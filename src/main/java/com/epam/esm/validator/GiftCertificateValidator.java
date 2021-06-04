package com.epam.esm.validator;

import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GiftCertificateValidator implements Validator {
    private static final String DATE_TIME_ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    @Override
    public boolean supports(Class<?> clazz) {
        return GiftCertificate.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        GiftCertificate giftCertificate = (GiftCertificate) target;

    }
}
