package com.diploma.qoldan.service.product;

import com.diploma.qoldan.dto.product.TagDto;
import com.diploma.qoldan.exception.product.TagExistsException;
import com.diploma.qoldan.exception.product.TagNotFoundException;
import com.diploma.qoldan.mapper.product.TagMapper;
import com.diploma.qoldan.model.product.Tag;
import com.diploma.qoldan.repository.product.TagRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepo repo;
    private final TagMapper mapper;

    public List<TagDto> getTags() {
        List<Tag> tags = repo.findAll();
        return tags
                .stream()
                .map(mapper::mapTagToDto)
                .toList();
    }

    @Transactional
    public Long createTag(TagDto tagDto) throws TagExistsException {
        Tag tag = repo.findByTitle(tagDto.getTitle());
        if (tag != null) {
            throw new TagExistsException("");
        }

        tag = mapper.mapDtoToTag(tagDto);
        repo.save(tag);
        return tag.getId();
    }

    @Transactional
    public void updateTag(TagDto tagDto) throws TagNotFoundException {
        Tag tag = repo.findById(tagDto.getId());
        if (tag == null) {
            throw new TagNotFoundException("");
        }

        tag = mapper.mapDtoToTag(tagDto);
        repo.save(tag);
    }

    @Transactional
    public void deleteTagById(Long tagId) throws TagNotFoundException {
        Tag tag = repo.findById(tagId);
        if (tag == null) {
            throw new TagNotFoundException("");
        }

        repo.delete(tag);
    }
}
