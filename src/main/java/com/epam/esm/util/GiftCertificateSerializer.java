package com.epam.esm.util;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GiftCertificateSerializer extends JsonSerializer<GiftCertificate> {
    @Override
    public void serialize(GiftCertificate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("name", value.getName());
        gen.writeStringField("description", value.getDescription());
        gen.writeNumberField("price", value.getPrice());
        gen.writeNumberField("duration", value.getDuration());
        gen.writeStringField("createDate", value.getCreateDate().toString());
        gen.writeStringField("lastUpdateDate", value.getCreateDate().toString());
        gen.writeFieldName("tags");
        gen.writeStartArray();
        for (Tag tag : value.getTags()) {
            gen.writeStartObject();
            gen.writeNumberField("id",tag.getId());
            gen.writeStringField("name",tag.getName());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
        gen.close();
    }
}
