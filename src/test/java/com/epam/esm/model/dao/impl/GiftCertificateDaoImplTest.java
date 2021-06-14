package com.epam.esm.model.dao.impl;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles("integration")
class GiftCertificateDaoImplTest {
    private static final String ISO_DATE_TIME_PATTERN = "yyyy-MM-dd'T'hh:mm:ss.ss";
    private GiftCertificate giftCertificate;

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName("New gift certificate name");
        giftCertificate.setDescription("New gift certificate description");
        giftCertificate.setPrice(new BigDecimal("55.77"));
        giftCertificate.setDuration(30);
        giftCertificate.setCreateDate(LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(ISO_DATE_TIME_PATTERN))));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(ISO_DATE_TIME_PATTERN))));
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
        assertFalse(giftCertificates.isEmpty());
    }

    @Test
    void givenGiftCertificateInDb_WhenUpdate_ThenMatchingModify() {
        GiftCertificate createdGiftCertificate = giftCertificateDao.create(giftCertificate);
        String updateGiftCertificateName = "Updated gift certificate name";
        createdGiftCertificate.setName(updateGiftCertificateName);
        Optional<GiftCertificate> actual = giftCertificateDao.update(createdGiftCertificate);
        assertEquals(Optional.of(createdGiftCertificate), actual);
    }

    @Test
    void givenGiftCertificateInDb_WhenDelete_ThenReturnTrue() {
        GiftCertificate createdGiftCertificate = giftCertificateDao.create(giftCertificate);
        boolean condition = giftCertificateDao.deleteById(createdGiftCertificate.getId());
        assertTrue(condition);
    }

    @Test
    void givenGiftCertificateAndTagInDb_WhenAddTagToCertificate_ThenReturnTrue() {
        GiftCertificate createdGiftCertificate = giftCertificateDao.create(giftCertificate);
        Tag tag = new Tag();
        tag.setName("Tag name");
        Tag createdTag = tagDao.create(tag);
        boolean condition = giftCertificateDao.addTagToCertificate(createdGiftCertificate.getId(), createdTag.getId());
        assertTrue(condition);
    }

    @Test
    void givenGiftCertificateAndTagInDb_WhenFindGiftCertificatesByTagName_ThenReturnListGiftCertificates() {
        GiftCertificate createdGiftCertificate = giftCertificateDao.create(giftCertificate);
        Tag tag = new Tag();
        tag.setName("New tag name");
        Tag createdTag = tagDao.create(tag);
        giftCertificateDao.addTagToCertificate(createdGiftCertificate.getId(), createdTag.getId());
        List<GiftCertificate> actual = giftCertificateDao.findGiftCertificatesByTagName(tag.getName());
        assertEquals(1, actual.size());
    }
}