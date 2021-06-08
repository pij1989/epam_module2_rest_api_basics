package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
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
    @Transactional
    public Optional<Tag> createTagInGiftCertificate(Long certificateId, Tag tag) {
        if (tag != null && giftCertificateDao.findById(certificateId).isPresent()) {
            Tag createdTag = tagDao.create(tag);
            Long tagId = createdTag.getId();
            return giftCertificateDao.addTagToCertificate(certificateId, tagId) ? Optional.of(createdTag) : Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public boolean addTagToGiftCertificate(Long certificateId, Long tagId) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(certificateId);
        if (optionalGiftCertificate.isPresent()) {
            if (tagDao.findById(tagId).isPresent()) {
                return giftCertificateDao.addTagToCertificate(certificateId, tagId);
            }
        }
        return false;
    }

    @Override
    public boolean deleteGiftCertificate(Long id) {
        return giftCertificateDao.deleteById(id);
    }
}
