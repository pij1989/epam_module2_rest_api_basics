package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public Optional<GiftCertificate> createGiftCertificate(GiftCertificate giftCertificate) {
        if (giftCertificate != null) {
            return Optional.of(giftCertificateDao.create(giftCertificate));
        }
        return Optional.empty();
    }

    @Override
    public Optional<GiftCertificate> findGiftCertificate(Long id) {
        return giftCertificateDao.findById(id);
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificate() {
        return giftCertificateDao.findAll();
    }

    @Override
    public Optional<GiftCertificate> updateGiftCertificate(GiftCertificate giftCertificate, Long id) {
        if (giftCertificate == null) {
            return Optional.empty();
        }
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        if (optionalGiftCertificate.isPresent()) {
            giftCertificate.setId(id);
            return giftCertificateDao.update(giftCertificate);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteGiftCertificate(Long id) {
        return giftCertificateDao.deleteById(id);
    }
}
