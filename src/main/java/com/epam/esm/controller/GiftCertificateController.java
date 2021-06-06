package com.epam.esm.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.exception.BadRequestException;
import com.epam.esm.model.exception.GiftCertificateNotFoundException;
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

    @GetMapping("/{id}")
    ResponseEntity<GiftCertificate> findGiftCertificate(@PathVariable String id) throws GiftCertificateNotFoundException,
            BadRequestException {
        logger.debug("Path variable: " + id);
        long parseId;
        try {
            parseId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            logger.error("Bad request:" + e.getMessage());
            throw new BadRequestException("Bad request, id = " + id, e);
        }
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.findGiftCertificate(parseId);
        return optionalGiftCertificate.map(giftCertificate -> new ResponseEntity<>(giftCertificate, HttpStatus.OK))
                .orElseThrow(() -> new GiftCertificateNotFoundException("Gift certificate not found, id = " + id));
    }

    @GetMapping
    ResponseEntity<List<GiftCertificate>> findAllGiftCertificate() {
        List<GiftCertificate> giftCertificates = giftCertificateService.findAllGiftCertificate();
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<GiftCertificate> updateGiftCertificate(@RequestBody GiftCertificate giftCertificate,
                                                          @PathVariable String id) throws BadRequestException, GiftCertificateNotFoundException {
        logger.debug("Path variable: " + id);
        long parseId;
        try {
            parseId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            logger.error("Bad request:" + e.getMessage());
            throw new BadRequestException("Bad request, id = " + id, e);
        }
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateService.updateGiftCertificate(giftCertificate, parseId);
        return optionalGiftCertificate.map(certificate -> new ResponseEntity<>(certificate, HttpStatus.OK))
                .orElseThrow(() -> new GiftCertificateNotFoundException("Gift certificate not found, id = " + id));
    }
}
