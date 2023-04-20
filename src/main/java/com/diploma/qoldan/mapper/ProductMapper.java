package com.diploma.qoldan.mapper;

import com.diploma.qoldan.dto.ProductDto;
import com.diploma.qoldan.model.Category;
import com.diploma.qoldan.model.Product;
import com.diploma.qoldan.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "desc", source = "product.summary")
    @Mapping(target = "ownerEmail", source = "product.user.email")
    @Mapping(target = "ownerName", source = "product.user.firstname")
    @Mapping(target = "categoryId", source = "product.category.id")
    @Mapping(target = "categoryTitle", source = "product.category.title")
    ProductDto mapProductToDto(Product product);

    @Mapping(target = "id", source = "productDto.id")
    @Mapping(target = "title", source = "productDto.title")
    @Mapping(target = "summary", source = "productDto.desc")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "category", source = "category")
    Product mapDtoToProduct(ProductDto productDto, Category category, User user);

}
