package com.epam.esm.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Tag extends Entity {
    private Long id;
    private String name;
    private List<GiftCertificate> giftCertificates = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GiftCertificate> getGiftCertificates() {
        return new ArrayList<>(giftCertificates);
    }

    public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
        this.giftCertificates = new ArrayList<>(giftCertificates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
        return giftCertificates != null ? giftCertificates.equals(tag.giftCertificates) : tag.giftCertificates == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (giftCertificates != null ? giftCertificates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", giftCertificates=").append(giftCertificates);
        sb.append('}');
        return sb.toString();
    }
}
