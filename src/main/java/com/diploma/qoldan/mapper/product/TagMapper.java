package com.diploma.qoldan.mapper.product;

import com.diploma.qoldan.dto.product.TagDto;
import com.diploma.qoldan.model.product.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDto mapTagToDto(Tag tag);
    Tag mapDtoToTag(TagDto tagDto);
}
