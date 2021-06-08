package com.epam.esm.model.service;

import com.epam.esm.model.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    Optional<GiftCertificate> createGiftCertificate(GiftCertificate giftCertificate);

    Optional<GiftCertificate> findGiftCertificate(Long id);

    List<GiftCertificate> findAllGiftCertificate();

    Optional<GiftCertificate> updateGiftCertificate(GiftCertificate giftCertificate,Long id);

    boolean addTagToGiftCertificate(Long certificateId, Long tagId);

    boolean deleteGiftCertificate(Long id);
}
