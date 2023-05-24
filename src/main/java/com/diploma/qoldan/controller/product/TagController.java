package com.diploma.qoldan.controller.product;

import com.diploma.qoldan.dto.product.TagDto;
import com.diploma.qoldan.exception.product.TagExistsException;
import com.diploma.qoldan.exception.product.TagNotFoundException;
import com.diploma.qoldan.model.product.Tag;
import com.diploma.qoldan.service.product.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;


@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5001/")
public class TagController {

    private final TagService service;

    @GetMapping
    public ResponseEntity<List<TagDto>> getTags() {
        List<TagDto> tagDtoList = service.getTags();
        return ResponseEntity.ok(tagDtoList);
    }

    @PostMapping
    public ResponseEntity<Long> createTag(@RequestBody TagDto tagDto) throws TagExistsException {
        Long id = service.createTag(tagDto);
        return ResponseEntity.ok(id);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<String> updateTag(@PathVariable("id") Long tagId, @RequestBody TagDto tagDto) throws TagNotFoundException {
        tagDto.setId(tagId);
        service.updateTag(tagDto);
        return ResponseEntity.ok("Tag was successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable("id") Long tagId) throws TagNotFoundException {
        service.deleteTagById(tagId);
        return ResponseEntity.ok("Tag was successfully deleted");
    }
}
