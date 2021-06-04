package com.epam.esm.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.validator.GiftCertificateValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gift_certificates")
public class GiftCertificateController {
    private static final Logger logger = LogManager.getLogger(GiftCertificate.class);

    private final GiftCertificateValidator giftCertificateValidator;

    @Autowired
    public GiftCertificateController(GiftCertificateValidator giftCertificateValidator) {
        this.giftCertificateValidator = giftCertificateValidator;
    }

    @PostMapping
    public ResponseEntity<GiftCertificate> createGiftCertificate(@RequestBody GiftCertificate giftCertificate,
                                                                 BindingResult bindingResult) {
        logger.debug("Created gift certificate: " + giftCertificate);
        giftCertificateValidator.validate(giftCertificate, bindingResult);
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .forEach(logger::info);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
