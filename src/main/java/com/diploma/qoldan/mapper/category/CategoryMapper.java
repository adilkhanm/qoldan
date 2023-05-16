package com.diploma.qoldan.mapper.category;

import com.diploma.qoldan.dto.category.CategoryDto;
import com.diploma.qoldan.model.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto mapCategoryToDto(Category category);

    Category mapDtoToCategory(CategoryDto categoryDto);

}
