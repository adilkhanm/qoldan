package com.diploma.qoldan.service.product;

import com.diploma.qoldan.dto.product.ProductRequestDto;
import com.diploma.qoldan.dto.product.ProductResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.enums.ProductStatusEnum;
import com.diploma.qoldan.exception.UsernameExistsException;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.product.ProductAccessDeniedException;
import com.diploma.qoldan.exception.product.ProductIsNotActiveException;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.exception.product.ProductTypeNotFoundException;
import com.diploma.qoldan.model.category.Category;
import com.diploma.qoldan.model.image.Image;
import com.diploma.qoldan.model.item.Item;
import com.diploma.qoldan.model.item.ItemImage;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.product.ProductType;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.category.CategoryRepo;
import com.diploma.qoldan.repository.ProductRepo;
import com.diploma.qoldan.repository.UserRepo;
import com.diploma.qoldan.repository.image.ImageRepo;
import com.diploma.qoldan.repository.item.ItemImageRepo;
import com.diploma.qoldan.repository.product.ProductTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo repo;
    private final ProductTypeRepo productTypeRepo;
    private final ImageRepo imageRepo;
    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;
    private final ItemImageRepo itemImageRepo;

    public List<ProductShortResponseDto> getProducts(String username,
                                                     String type,
                                                     String category,
                                                     Integer price_low,
                                                     Integer price_high,
                                                     Integer limit,
                                                     Integer offset) {
        List<Product> products = repo.findAllByStatus(ProductStatusEnum.ACTIVE.toString());
        Stream<Product> productsStream = products.stream().filter(product -> {
            boolean okay = true;
            if (username != null)
                okay &= product.getItem().getUser().getEmail().equals(username);
            if (type != null)
                okay &= product.getProductType().getTitle().equals(type);
            if (category != null)
                okay &= product.getItem().getCategory().getTitle().equals(category);
            if (price_low != null)
                okay &= product.getPrice() >= price_low;
            if (price_high != null)
                okay &= product.getPrice() <= price_high;
            return okay;
        });

        if (offset != null)
            productsStream = productsStream.skip(offset);
        if (limit != null)
            productsStream = productsStream.limit(limit);

        return productsStream
                .map(product -> ProductShortResponseDto.builder()
                        .id(product.getId())
                        .title(product.getItem().getTitle())
                        .price(product.getPrice())
                        .img(product.getItem().getMainImage().getUrl())
                        .date(product.getDatePosted())
                        .build())
                .toList();
    }

    public ProductResponseDto getProductById(Long productId) throws ProductNotFoundException {
        Product product = findProductById(productId);

        Item item = product.getItem();
        List<String> tags = product
                .getProductTagList()
                .stream()
                .map(productTag -> productTag.getTag().getTitle())
                .toList();
        List<String> images = item
                .getItemImageList()
                .stream()
                .map(itemImage -> itemImage.getImage().getUrl())
                .toList();

        return ProductResponseDto.builder()
                .id(product.getId())
                .title(item.getTitle())
                .summary(item.getSummary())
                .img(item.getMainImage().getUrl())
                .category(item.getCategory().getTitle())
                .username(item.getUser().getEmail())
                .status(product.getStatus())
                .price(product.getPrice())
                .date(product.getDatePosted())
                .type(product.getProductType().getTitle())
                .tags(tags)
                .images(images)
                .build();
    }

    @Transactional
    public Long createProduct(ProductRequestDto productRequestDto, String username)
            throws ProductTypeNotFoundException, CategoryNotFoundException, UsernameExistsException {

        Image image = getImage(productRequestDto.getImg());
        Category category = findCategoryByTitle(productRequestDto.getCategory());
        User user = findUserByUsername(username);
        ProductType productType = findTypeByTitle(productRequestDto.getType());

        Item item = Item.builder()
                .title(productRequestDto.getTitle())
                .summary(productRequestDto.getSummary())
                .mainImage(image)
                .category(category)
                .user(user)
                .build();

        Product product = Product.builder()
                .price(productRequestDto.getPrice())
                .datePosted(new Date())
                .status(ProductStatusEnum.ACTIVE.toString())
                .item(item)
                .productType(productType)
                .build();

        repo.save(product);

        addImagesToItem(productRequestDto.getImages(), item);

        return product.getId();
    }

    public void updateProduct(ProductRequestDto productRequestDto, String username, Collection<? extends GrantedAuthority> authorities)
            throws ProductNotFoundException, ProductIsNotActiveException, ProductTypeNotFoundException, CategoryNotFoundException, ProductAccessDeniedException {
        Product product = findProductById(productRequestDto.getId());
        if (!product.getStatus().equals(ProductStatusEnum.ACTIVE.toString()))
            throw new ProductIsNotActiveException("");

        if (!product.getItem().getUser().getEmail().equals(username)
                && authorities.stream().anyMatch(a -> !a.getAuthority().equals("ROLE_ADMIN")))
            throw new ProductAccessDeniedException("");


        ProductType productType = findTypeByTitle(productRequestDto.getType());
        product.setPrice(productRequestDto.getPrice());
        product.setProductType(productType);

        Item item = product.getItem();
        Category category = findCategoryByTitle(productRequestDto.getCategory());
        item.setTitle(productRequestDto.getTitle());
        item.setSummary(productRequestDto.getSummary());
        item.setCategory(category);

        Image image = getImage(productRequestDto.getImg());
        item.setMainImage(image);

        product.setItem(item);

        repo.save(product);
        addImagesToItem(productRequestDto.getImages(), item);
    }

    private void addImagesToItem(List<String> images, Item item) {
        if (images == null)
            return;

        for (String imageUrl : images) {
            Image image = getImage(imageUrl);
            ItemImage itemImage = ItemImage.builder()
                    .image(image)
                    .item(item)
                    .build();
            itemImageRepo.save(itemImage);
        }
    }

    @Transactional
    public void deleteProductById(Long productId) throws ProductNotFoundException, ProductIsNotActiveException {
        Product product = findProductById(productId);
        if (!product.getStatus().equals(ProductStatusEnum.ACTIVE.toString()))
            throw new ProductIsNotActiveException("");
        repo.delete(product);
    }

    private Image getImage(String url) {
        Image image = imageRepo.findByUrl(url);
        if (image != null)
            return image;
        return Image.builder()
                .url(url)
                .build();
    }

    private Product findProductById(Long id) throws ProductNotFoundException {
        Product product = repo.findById(id);
        if (product == null)
            throw new ProductNotFoundException("");
        return product;
    }

    private Category findCategoryByTitle(String title) throws CategoryNotFoundException {
        Category category = categoryRepo.findByTitle(title);
        if (category == null)
            throw new CategoryNotFoundException("");
        return category;
    }

    private User findUserByUsername(String username) throws UsernameExistsException {
        User user = userRepo.findByEmail(username);
        if (user == null)
            throw new UsernameExistsException("");
        return user;
    }

    private ProductType findTypeByTitle(String title) throws ProductTypeNotFoundException {
        ProductType productType = productTypeRepo.findByTitle(title);
        if (productType == null)
            throw new ProductTypeNotFoundException("");
        return productType;
    }
}
