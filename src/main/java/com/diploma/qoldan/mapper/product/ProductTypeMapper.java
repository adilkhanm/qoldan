package com.diploma.qoldan.mapper.product;

import com.diploma.qoldan.dto.product.ProductTypeDto;
import com.diploma.qoldan.model.product.ProductType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {
    ProductTypeDto mapTypeToDto(ProductType type);
    ProductType mapDtoToType(ProductTypeDto typeDto);
}
