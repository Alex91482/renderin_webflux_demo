package com.example.demo.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * Базовым элементом структуры данных OSM является точка (node) с географическими координатами — широтой (latitude) и долготой (longitude)
 */
@Entity
@Table(schema = "OPENSTREETMAPS", name = "NODE")
@Data
public class NodeEntity {

    /**
     * уникальный идентификатор в базе OSM (используется для way и relation)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    /**
     *
     */
    private Boolean visible;
    /**
     * версия изменения (используется для way и relation)
     */
    private Integer version;
    /**
     * номер изменения (что-то типа транзакции, которую можно применить или отменить) (используется для way и relation)
     */
    private Long changeset;
    /**
     * время изменения (используется для way и relation)
     */
    private String timestamp;
    /**
     *  имя оператора внёсшего изменения (используется для way и relation)
     */
    private String user;
    /**
     * идентификатор внёсшего изменения (используется для way и relation)
     */
    private Long uid;
    /**
     * широта
     */
    private Double lat;
    /**
     * долгота
     */
    private Double lon;
    /**
     * порядковый номер точки
     */
    private Integer serialNumber;
    /**
     * дополнительная информация
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TagEntity> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeEntity that = (NodeEntity) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(visible, that.visible)) return false;
        if (!Objects.equals(version, that.version)) return false;
        if (!Objects.equals(changeset, that.changeset)) return false;
        if (!Objects.equals(timestamp, that.timestamp)) return false;
        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(uid, that.uid)) return false;
        if (!Objects.equals(lat, that.lat)) return false;
        if (!Objects.equals(lon, that.lon)) return false;
        return Objects.equals(serialNumber, that.serialNumber);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (visible != null ? visible.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (changeset != null ? changeset.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (lon != null ? lon.hashCode() : 0);
        result = 31 * result + (serialNumber != null ? serialNumber.hashCode() : 0);
        return result;
    }
}
