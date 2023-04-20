package com.diploma.qoldan.mapper;

import com.diploma.qoldan.dto.CategoryDto;
import com.diploma.qoldan.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto mapCategoryToDto(Category category);

    Category mapDtoToCategory(CategoryDto categoryDto);

}
