package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public Optional<Tag> create(Tag tag) {
        return tagDao.create(tag);
    }

    @Override
    @Transactional
    public Optional<Tag> findTag(Long id) {
        return tagDao.findById(id);
    }

    @Override
    @Transactional
    public List<Tag> findAllTag() {
        return tagDao.findAll();
    }
}
