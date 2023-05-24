package com.diploma.qoldan.service.product;

import com.diploma.qoldan.dto.product.ProductRequestDto;
import com.diploma.qoldan.dto.product.ProductResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.enums.ProductStatusEnum;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.product.ProductAccessDeniedException;
import com.diploma.qoldan.exception.product.ProductIsNotActiveException;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.exception.product.ProductTypeNotFoundException;
import com.diploma.qoldan.mapper.product.ProductMapper;
import com.diploma.qoldan.model.category.Category;
import com.diploma.qoldan.model.image.Image;
import com.diploma.qoldan.model.item.Item;
import com.diploma.qoldan.model.item.ItemImage;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.product.ProductType;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.product.ProductRepo;
import com.diploma.qoldan.repository.image.ImageRepo;
import com.diploma.qoldan.repository.item.ItemImageRepo;
import com.diploma.qoldan.service.cart.CartSimpleService;
import com.diploma.qoldan.service.user.UserService;
import com.diploma.qoldan.service.category.CategorySimpleService;
import com.diploma.qoldan.service.user.UserSimpleService;
import com.diploma.qoldan.service.wishlist.WishlistSimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final ImageRepo imageRepo;
    private final ItemImageRepo itemImageRepo;

    private final ProductMapper mapper;

    private final ProductSimpleService service;
    private final CategorySimpleService categoryService;
    private final WishlistSimpleService wishlistService;
    private final ProductTypeService productTypeService;
    private final UserSimpleService userService;
    private final CartSimpleService cartService;

    public List<ProductShortResponseDto> getProducts(String username,
                                                     String ownerUsername,
                                                     String type,
                                                     String category,
                                                     Integer priceLow,
                                                     Integer priceHigh,
                                                     Integer limit,
                                                     Integer offset) {
        List<Product> products = repo.findAllByStatus(ProductStatusEnum.ACTIVE.toString());
        Stream<Product> productsStream = products.stream().filter(product -> {
            boolean okay = true;
            if (ownerUsername != null)
                okay &= product.getItem().getUser().getEmail().equals(ownerUsername);
            if (type != null)
                okay &= product.getProductType().getTitle().equals(type);
            if (category != null)
                okay &= product.getItem().getCategory().getTitle().equals(category);
            if (priceLow != null)
                okay &= product.getPrice() >= priceLow;
            if (priceHigh != null)
                okay &= product.getPrice() <= priceHigh;
            return okay;
        });

        if (offset != null)
            productsStream = productsStream.skip(offset);
        if (limit != null)
            productsStream = productsStream.limit(limit);

        final User user = username == null ? null : userService.findUserByUsername(username);

        return productsStream
                .map(product -> {
                    boolean inWishlist = wishlistService.checkProductInWishlist(user, product);
                    boolean inCart = cartService.checkProductInCart(user, product);
                    return mapper.mapProductToShortResponse(product, inWishlist, inCart);
                })
                .toList();
    }

    public Integer getProductPages(Integer productsPerPage,
                                   String ownerUsername,
                                   String type,
                                   String category,
                                   Integer priceLow,
                                   Integer priceHigh) {
        List<ProductShortResponseDto> products = getProducts(
                null,
                ownerUsername,
                type,
                category,
                priceLow,
                priceHigh,
                null,
                null);
        Integer productsCount = products.size();
        return (productsCount + productsPerPage - 1) / productsPerPage;
    }

    public ProductResponseDto getProductById(String username, Long productId) throws ProductNotFoundException {
        Product product = service.findProductById(productId);

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

        final User user = username == null ? null : userService.findUserByUsername(username);
        boolean inWishlist = wishlistService.checkProductInWishlist(user, product);
        boolean inCart = cartService.checkProductInCart(user, product);

        return mapper.mapProductToResponse(product, item, tags, images, inWishlist, inCart);
    }

    public List<ProductShortResponseDto> getUsersProducts(String username, Integer limit, Integer offset) {
        User user = userService.findUserByUsername(username);
        List<Product> products = repo.findAllByStatusAndUser(ProductStatusEnum.ACTIVE.toString(), user.getId());
        Stream<Product> productsStream = products.stream();
        if (offset != null)
            productsStream = productsStream.skip(offset);
        if (limit != null)
            productsStream = productsStream.limit(limit);

        return productsStream
                .map(product -> {
                    boolean inWishlist = wishlistService.checkProductInWishlist(user, product);
                    boolean inCart = cartService.checkProductInCart(user, product);
                    return mapper.mapProductToShortResponse(product, inWishlist, inCart);
                })
                .toList();
    }

    @Transactional
    public Long createProduct(ProductRequestDto productRequestDto, Long imageId, String username)
            throws ProductTypeNotFoundException, CategoryNotFoundException, UsernameNotFoundException {

        Image image = getImage(productRequestDto.getImg());
        Category category = categoryService.findCategoryByTitle(productRequestDto.getCategory());
        User user = userService.findUserByUsername(username);
        ProductType productType = productTypeService.findTypeByTitle(productRequestDto.getType());

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

    @Transactional
    public void updateProduct(ProductRequestDto productRequestDto, String username, Collection<? extends GrantedAuthority> authorities)
            throws ProductNotFoundException, ProductIsNotActiveException, ProductTypeNotFoundException, CategoryNotFoundException, ProductAccessDeniedException {
        Product product = service.findProductById(productRequestDto.getId());
        if (!product.getStatus().equals(ProductStatusEnum.ACTIVE.toString()))
            throw new ProductIsNotActiveException("");

        if (!product.getItem().getUser().getEmail().equals(username)
                && authorities.stream().anyMatch(a -> !a.getAuthority().equals("ROLE_ADMIN")))
            throw new ProductAccessDeniedException("");

        ProductType productType = productTypeService.findTypeByTitle(productRequestDto.getType());
        product.setPrice(productRequestDto.getPrice());
        product.setProductType(productType);

        Item item = product.getItem();
        Category category = categoryService.findCategoryByTitle(productRequestDto.getCategory());
        item.setTitle(productRequestDto.getTitle());
        item.setSummary(productRequestDto.getSummary());
        item.setCategory(category);

        Image image = getImage(productRequestDto.getImg());
        item.setMainImage(image);

        product.setItem(item);

        repo.save(product);
        addImagesToItem(productRequestDto.getImages(), item);
    }

    @Transactional
    public void deleteProductById(Long productId) throws ProductNotFoundException, ProductIsNotActiveException {
        Product product = service.findProductById(productId);
        if (!product.getStatus().equals(ProductStatusEnum.ACTIVE.toString()))
            throw new ProductIsNotActiveException("");
        repo.delete(product);
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

    private Image getImage(String url) {
        Image image = imageRepo.findByUrl(url);
        if (image != null)
            return image;
        return Image.builder()
                .url(url)
                .build();
    }

}
