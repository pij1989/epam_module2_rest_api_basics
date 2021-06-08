package com.epam.esm.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.exception.BadRequestException;
import com.epam.esm.model.exception.NotFoundException;
import com.epam.esm.model.service.GiftCertificateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<GiftCertificate> createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        logger.debug("Created gift certificate: " + giftCertificate);
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.createGiftCertificate(giftCertificate);
        if (optionalGiftCertificate.isPresent()) {
            GiftCertificate createdGiftCertificate = optionalGiftCertificate.get();
            return new ResponseEntity<>(createdGiftCertificate, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{certificateId}")
    ResponseEntity<GiftCertificate> findGiftCertificate(@PathVariable String certificateId) throws BadRequestException, NotFoundException {
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
    ResponseEntity<List<GiftCertificate>> findAllGiftCertificate() {
        List<GiftCertificate> giftCertificates = giftCertificateService.findAllGiftCertificate();
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @PutMapping("/{certificateId}")
    ResponseEntity<GiftCertificate> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate,
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

    @PutMapping("{certificateId}/tags/{tagId}")
    ResponseEntity<Object> addTagToGiftCertificate(@PathVariable String certificateId, @PathVariable String tagId) throws BadRequestException, NotFoundException {
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
    ResponseEntity<Object> deleteGiftCertificate(@PathVariable String certificateId) throws BadRequestException, NotFoundException {
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
