package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftCertificate;

public interface GiftCertificateDao extends BaseDao<Long, GiftCertificate> {
    boolean addTagToCertificate(Long certificateId, Long tagId);
}
