package com.epam.esm.model.dao.impl;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles("integration")
class GiftCertificateDaoImplTest {
    private static final String ISO_DATE_TIME_PATTERN = "uuuu-MM-dd'T'hh:mm:ss.ss";
    private GiftCertificate giftCertificate;

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    void setUp() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName("New gift certificate name");
        giftCertificate.setDescription("New gift certificate description");
        giftCertificate.setPrice(new BigDecimal("55.77"));
        giftCertificate.setDuration(30);
        giftCertificate.setCreateDate(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern(ISO_DATE_TIME_PATTERN))));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern(ISO_DATE_TIME_PATTERN))));
    }

    @AfterEach
    void tearDown() {
        giftCertificate = null;
    }

    @Test
    void givenGiftCertificateInDb_WhenFindById_ThenReturnGiftCertificate() {
        GiftCertificate expected = giftCertificateDao.create(giftCertificate);
        Optional<GiftCertificate> actual = giftCertificateDao.findById(expected.getId());
        assertEquals(Optional.of(expected), actual);
    }

    @Test
    void givenGiftCertificatesInDb_WhenFindAll_ThenReturnListOfGiftCertificates() {
        giftCertificateDao.create(giftCertificate);
        giftCertificateDao.create(giftCertificate);
        giftCertificateDao.create(giftCertificate);
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll();
        assertEquals(3, giftCertificates.size());
    }
}