package com.epam.esm.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.BadRequestException;
import com.epam.esm.model.exception.NotFoundException;
import com.epam.esm.model.service.GiftCertificateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {
    private static final Logger logger = LogManager.getLogger(GiftCertificate.class);
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping
    public ResponseEntity<GiftCertificate> createGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws BadRequestException {
        logger.debug("Created gift certificate: " + giftCertificate);
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.createGiftCertificate(giftCertificate);
        return optionalGiftCertificate.map(certificate -> new ResponseEntity<>(certificate, HttpStatus.CREATED)).
                orElseThrow(() -> new BadRequestException("Bad request, gift certificate not be created"));
    }

    @GetMapping("/{certificateId}")
    public ResponseEntity<GiftCertificate> findGiftCertificate(@PathVariable String certificateId) throws BadRequestException, NotFoundException {
        logger.debug("Path variable: " + certificateId);
        long parseId;
        try {
            parseId = Long.parseLong(certificateId);
        } catch (NumberFormatException e) {
            logger.error("Bad request:" + e.getMessage());
            throw new BadRequestException("Bad request, id = " + certificateId, e);
        }
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.findGiftCertificate(parseId);
        return optionalGiftCertificate.map(giftCertificate -> new ResponseEntity<>(giftCertificate, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException("Gift certificate not found, id = " + certificateId));
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> findAllGiftCertificate() {
        List<GiftCertificate> giftCertificates = giftCertificateService.findAllGiftCertificate();
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @GetMapping(params = {"tagName"})
    public ResponseEntity<List<GiftCertificate>> findGiftCertificateByTagName(@RequestParam("tagName") String name) throws NotFoundException {
        logger.debug("Name: " + name);
        List<GiftCertificate> giftCertificates = giftCertificateService.findGiftCertificateByTagName(name);
        if (!giftCertificates.isEmpty()) {
            return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
        } else {
            throw new NotFoundException("Gift certificates not found, tag name = " + name);
        }
    }

    @GetMapping(params = {"filter"})
    public ResponseEntity<List<GiftCertificate>> searchGiftCertificate(@RequestParam("filter") String filter) {
        logger.debug("Filter: " + filter);
        List<GiftCertificate> giftCertificates = giftCertificateService.searchGiftCertificate(filter);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @GetMapping(params = {"sort"})
    public ResponseEntity<List<GiftCertificate>> sortGiftCertificate(@RequestParam("sort") String sort) {
        logger.debug("Sort: " + sort);
        List<GiftCertificate> giftCertificates = giftCertificateService.sortGiftCertificate(sort);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @PutMapping("/{certificateId}")
    public ResponseEntity<GiftCertificate> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate,
                                                                 @PathVariable String certificateId) throws BadRequestException, NotFoundException {
        logger.debug("Path variable: " + certificateId);
        long parseId;
        try {
            parseId = Long.parseLong(certificateId);
        } catch (NumberFormatException e) {
            logger.error("Bad request:" + e.getMessage());
            throw new BadRequestException("Bad request, id = " + certificateId, e);
        }
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.updateGiftCertificate(giftCertificate, parseId);
        return optionalGiftCertificate.map(certificate -> new ResponseEntity<>(certificate, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException("Gift certificate not found, id = " + certificateId));
    }

    @PatchMapping("/{certificateId}")
    public ResponseEntity<GiftCertificate> updatePartOfGiftCertificate(@RequestBody GiftCertificate giftCertificate,
                                                                       @PathVariable String certificateId) throws BadRequestException, NotFoundException {
        logger.debug("Path variable: " + certificateId);
        long parseId;
        try {
            parseId = Long.parseLong(certificateId);
        } catch (NumberFormatException e) {
            logger.error("Bad request:" + e.getMessage());
            throw new BadRequestException("Bad request, id = " + certificateId, e);
        }
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.updatePartGiftCertificate(giftCertificate, parseId);
        return optionalGiftCertificate.map(certificate -> new ResponseEntity<>(certificate, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException("Gift certificate not found, id = " + certificateId));
    }

    @PostMapping("{certificateId}/tags")
    public ResponseEntity<Tag> createTagInGiftCertificate(@PathVariable String certificateId,
                                                          @RequestBody Tag tag,
                                                          HttpServletRequest request) throws BadRequestException {
        logger.debug("Path variable: " + certificateId);
        long parseId;
        try {
            parseId = Long.parseLong(certificateId);
        } catch (NumberFormatException e) {
            logger.error("Bad request:" + e.getMessage());
            throw new BadRequestException("Bad request, id = " + certificateId, e);
        }
        Optional<Tag> optionalTag = giftCertificateService.createTagInGiftCertificate(parseId, tag);
        return optionalTag.map(createdTag -> {
            HttpHeaders responseHeaders = new HttpHeaders();
            String location = request.getRequestURL().append("/").append(createdTag.getId()).toString();
            responseHeaders.set(HttpHeaders.LOCATION, location);
            return new ResponseEntity<>(createdTag, responseHeaders, HttpStatus.CREATED);
        }).orElseThrow(() -> new BadRequestException("Bad request, tag in gift certificate not be created"));
    }

    @PutMapping("{certificateId}/tags/{tagId}")
    public ResponseEntity<Object> addTagToGiftCertificate(@PathVariable String certificateId, @PathVariable String tagId) throws BadRequestException, NotFoundException {
        logger.debug("Path variable: " + certificateId + "," + tagId);
        long parseCertificateId;
        long parseTagId;
        try {
            parseCertificateId = Long.parseLong(certificateId);
        } catch (NumberFormatException e) {
            logger.error("Bad request:" + e.getMessage());
            throw new BadRequestException("Bad request, id = " + certificateId, e);
        }
        try {
            parseTagId = Long.parseLong(tagId);
        } catch (NumberFormatException e) {
            logger.error("Bad request:" + e.getMessage());
            throw new BadRequestException("Bad request, id = " + tagId, e);
        }
        if (giftCertificateService.addTagToGiftCertificate(parseCertificateId, parseTagId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new NotFoundException("Gift certificate or tag not found, gift certificate id = " + certificateId +
                    "; " + "tag id = " + tagId);
        }
    }

    @DeleteMapping("/{certificateId}")
    public ResponseEntity<Object> deleteGiftCertificate(@PathVariable String certificateId) throws BadRequestException, NotFoundException {
        logger.debug("Path variable: " + certificateId);
        long parseId;
        try {
            parseId = Long.parseLong(certificateId);
        } catch (NumberFormatException e) {
            logger.error("Bad request:" + e.getMessage());
            throw new BadRequestException("Bad request, id = " + certificateId, e);
        }
        if (giftCertificateService.deleteGiftCertificate(parseId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new NotFoundException("Gift certificate not found, id = " + certificateId);
        }
    }
}
