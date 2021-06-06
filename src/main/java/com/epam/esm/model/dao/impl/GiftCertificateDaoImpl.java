package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String CREATE_GIFT_CERTIFICATE_SQL = "INSERT INTO gift_certificate(name,description,price,duration,create_date,last_update_date) VALUES (?,?,?,?,?,?)";
    private static final String FIND_GIFT_CERTIFICATE_BY_ID_SQL = "SELECT id,name,description,price,duration,create_date,last_update_date FROM gift_certificate WHERE id = ?";
    private static final String FIND_ALL_GIFT_CERTIFICATE_SQL = "SELECT id,name,description,price,duration,create_date,last_update_date FROM gift_certificate";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_GIFT_CERTIFICATE_SQL, new String[]{ColumnName.ID});
            preparedStatement.setString(1, giftCertificate.getName());
            preparedStatement.setString(2, giftCertificate.getDescription());
            preparedStatement.setBigDecimal(3, giftCertificate.getPrice());
            preparedStatement.setDate(4, Date.valueOf(giftCertificate.getDuration()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
            return preparedStatement;
        }, keyHolder);
        Long id = (Long) keyHolder.getKey();
        giftCertificate.setId(id);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return jdbcTemplate.query(FIND_GIFT_CERTIFICATE_BY_ID_SQL, rs -> {
            if (rs.next()) {
                GiftCertificate giftCertificate = createGiftCertificateFromResultSet(rs);
                return Optional.of(giftCertificate);
            }
            return Optional.empty();
        }, id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return jdbcTemplate.query(FIND_ALL_GIFT_CERTIFICATE_SQL, (rs, rowNum) -> createGiftCertificateFromResultSet(rs));
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate giftCertificate) {
        return jdbcTemplate.query(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(FIND_GIFT_CERTIFICATE_BY_ID_SQL,
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    preparedStatement.setLong(1, giftCertificate.getId());
                    return preparedStatement;
                },
                rs -> {
                    if (rs.next()) {
                        if (giftCertificate.getName() != null) {
                            rs.updateString(ColumnName.NAME, giftCertificate.getName());
                        }
                        if (giftCertificate.getDescription() != null) {
                            rs.updateString(ColumnName.DESCRIPTION, giftCertificate.getDescription());
                        }
                        if (giftCertificate.getPrice() != null) {
                            rs.updateBigDecimal(ColumnName.PRICE, giftCertificate.getPrice());
                        }
                        if (giftCertificate.getDuration() != null) {
                            rs.updateDate(ColumnName.DURATION, Date.valueOf(giftCertificate.getDuration()));
                        }
                        if (giftCertificate.getCreateDate() != null) {
                            rs.updateTimestamp(ColumnName.CREATE_DATE, Timestamp.valueOf(giftCertificate.getCreateDate()));
                        }
                        if (giftCertificate.getLastUpdateDate() != null) {
                            rs.updateTimestamp(ColumnName.LAST_UPDATE_DATE, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
                        }
                        rs.updateRow();
                        GiftCertificate updatedGiftCertificate = createGiftCertificateFromResultSet(rs);
                        return Optional.of(updatedGiftCertificate);
                    }
                    return Optional.empty();
                });
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    private GiftCertificate createGiftCertificateFromResultSet(ResultSet rs) throws SQLException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(rs.getLong(ColumnName.ID));
        giftCertificate.setName(rs.getString(ColumnName.NAME));
        giftCertificate.setDescription(rs.getString(ColumnName.DESCRIPTION));
        giftCertificate.setPrice(rs.getBigDecimal(ColumnName.PRICE));
        giftCertificate.setDuration(rs.getDate(ColumnName.DURATION).toLocalDate());
        giftCertificate.setCreateDate(rs.getTimestamp(ColumnName.CREATE_DATE).toLocalDateTime());
        giftCertificate.setLastUpdateDate(rs.getTimestamp(ColumnName.LAST_UPDATE_DATE).toLocalDateTime());
        return giftCertificate;
    }
}
