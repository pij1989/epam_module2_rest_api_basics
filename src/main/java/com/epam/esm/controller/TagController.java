package com.epam.esm.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TagController {
    private static final Logger logger = LogManager.getLogger(TagController.class);
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/tags")
    ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        logger.debug("Request body: " + tag);
        Optional<Tag> optionalTag = tagService.create(tag);
        if (optionalTag.isPresent()) {
            Tag createdTag = optionalTag.get();
            return new ResponseEntity<>(createdTag, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tags/{id}")
    ResponseEntity<Tag> findTag(@PathVariable Long id){
        logger.debug("Path variable: " + id);
       Optional<Tag>optionalTag = tagService.findTag(id);
       if(optionalTag.isPresent()){
           Tag tag = optionalTag.get();
           return new ResponseEntity<>(tag,HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/tags")
    ResponseEntity<List<Tag>> findAllTags() {
        List<Tag> tags = tagService.findAllTag();
        return new ResponseEntity<>(tags,HttpStatus.OK);
    }
}
