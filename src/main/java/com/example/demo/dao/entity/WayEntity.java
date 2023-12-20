package com.example.demo.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Линия — это последовательности точек. Менять последовательность нельзя. Несколько линий, логически, могут представлять один объект.
 * Например, длинная дорога состоит из нескольких линий.
 * Линии одной дороги связаны в единое целое соблюдением условия — точка окончания одной линии строго соответствует точке начала другой линии
 * или нескольких линий (в случае ветвления дороги или съезда на другую дорогу).
 * Такая целостность по точкам для линий в OSM хорошо соблюдается.
 */
@Entity
@Table(schema = "OPENSTREETMAPS", name = "WAY")
@Data
public class WayEntity {

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
     * номер изменения (что-то типа транзакции, которую можно применить или отменить)
     */
    private Long changeset;
    /**
     * время изменения (используется для way и relation)
     */
    private LocalDateTime timestamp;
    /**
     * имя оператора внёсшего изменения
     */
    private String user;
    /**
     * идентификатор внёсшего изменения (
     */
    private Long uid;
    /**
     * список точек
     */
    @ManyToMany(fetch = FetchType.LAZY)
    private List<NodeEntity> nodes;
    /**
     * дополнительная информация
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TagEntity> tags;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WayEntity wayEntity = (WayEntity) o;

        if (!Objects.equals(id, wayEntity.id)) return false;
        if (!Objects.equals(visible, wayEntity.visible)) return false;
        if (!Objects.equals(version, wayEntity.version)) return false;
        if (!Objects.equals(changeset, wayEntity.changeset)) return false;
        if (!Objects.equals(timestamp, wayEntity.timestamp)) return false;
        if (!Objects.equals(user, wayEntity.user)) return false;
        return Objects.equals(uid, wayEntity.uid);
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
        return result;
    }
}
