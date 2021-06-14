package com.epam.esm.model.service.impl;

import com.epam.esm.configuration.TestConfiguration;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.service.GiftCertificateService;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@ActiveProfiles("unit")
class GiftCertificateServiceImplTest {
    private GiftCertificate giftCertificate;

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private GiftCertificateService giftCertificateService;

    @BeforeEach
    void setUp() {
        giftCertificate = new GiftCertificate();
        giftCertificate.setName("New gift certificate name");
        giftCertificate.setDescription("New gift certificate description");
        giftCertificate.setPrice(new BigDecimal("55.77"));
        giftCertificate.setDuration(30);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
    }

    @AfterEach
    void tearDown() {
        giftCertificate = null;
    }

    @Test
    void createGiftCertificate() {
        when(giftCertificateDao.create(giftCertificate)).thenReturn(giftCertificate);
        Optional<GiftCertificate> actual = giftCertificateService.createGiftCertificate(giftCertificate);
        verify(giftCertificateDao, times(1)).create(giftCertificate);
        assertEquals(Optional.of(giftCertificate), actual);
    }

    @Test
    void findGiftCertificate() {
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(giftCertificate));
        Optional<GiftCertificate> actual = giftCertificateService.findGiftCertificate(1L);
        assertEquals(Optional.of(giftCertificate), actual);
    }

    @Test
    void findAllGiftCertificate() {
        when(giftCertificateDao.findAll()).thenReturn(List.of(new GiftCertificate(), new GiftCertificate()));
        List<GiftCertificate> actual = giftCertificateService.findAllGiftCertificate();
        verify(giftCertificateDao, times(1)).findAll();
        assertEquals(2, actual.size());
    }

    @Test
    void updateGiftCertificate() {
        giftCertificate.setName("Updated gift certificate name");
        giftCertificate.setDescription("Updated gift certificate description");
        when(giftCertificateDao.update(giftCertificate)).thenReturn(Optional.of(giftCertificate));
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(giftCertificate));
        Optional<GiftCertificate> actual = giftCertificateService.updateGiftCertificate(giftCertificate, 1L);
        verify(giftCertificateDao, times(1)).update(giftCertificate);
        assertEquals(Optional.of(giftCertificate), actual);
    }

    @Test
    void createTagInGiftCertificate() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Tag name");
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(new GiftCertificate()));
        when(giftCertificateDao.addTagToCertificate(1L, 1L)).thenReturn(true);
        when(tagDao.create(tag)).thenReturn(tag);
        Optional<Tag> actual = giftCertificateService.createTagInGiftCertificate(1L, tag);
        assertEquals(Optional.of(tag), actual);
    }

    @Test
    void addTagToGiftCertificate() {
        when(giftCertificateDao.addTagToCertificate(1L, 1L)).thenReturn(true);
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(new GiftCertificate()));
        when(tagDao.findById(1L)).thenReturn(Optional.of(new Tag()));
        boolean condition = giftCertificateService.addTagToGiftCertificate(1L, 1L);
        verify(giftCertificateDao, times(1)).addTagToCertificate(1L, 1L);
        assertTrue(condition);
    }

    @Test
    void deleteGiftCertificate() {
        when(giftCertificateDao.deleteById(1L)).thenReturn(true);
        boolean condition = giftCertificateService.deleteGiftCertificate(1L);
        verify(giftCertificateDao, times(1)).deleteById(1L);
        assertTrue(condition);
    }

    @Test
    void findGiftCertificateByTagName() {
        String name = "name";
        when(giftCertificateDao.findGiftCertificatesByTagName(name)).thenReturn(List.of(new GiftCertificate()));
        List<GiftCertificate> actual = giftCertificateService.findGiftCertificateByTagName(name);
        verify(giftCertificateDao, times(1)).findGiftCertificatesByTagName(name);
        assertEquals(1, actual.size());
    }

    @Test
    void searchGiftCertificate() {
        String name = "name";
        when(giftCertificateDao.findGiftCertificateLikeNameOrDescription(name)).thenReturn(List.of(new GiftCertificate()));
        List<GiftCertificate> actual = giftCertificateService.searchGiftCertificate(name);
        verify(giftCertificateDao, times(1)).findGiftCertificateLikeNameOrDescription(name);
        assertEquals(1, actual.size());
    }
}