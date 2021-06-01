package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    @GetMapping("/tag")
    ResponseEntity<Tag> getTag() throws JsonProcessingException {
        Tag tag = new Tag(10, "Tag name");
        return ResponseEntity.ok(tag);
    }
}
