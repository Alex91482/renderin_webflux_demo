package com.example.demo.dao.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * Отношение — это логическое объединение точек, линий и других отношений в единый объект.
 */
@Entity
@Table(schema = "OPENSTREETMAPS", name = "RELATION")
@Data
public class RelationEntity {

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
    private String timestamp;
    /**
     * имя оператора внёсшего изменения
     */
    private String user;
    /**
     * идентификатор внёсшего изменения (
     */
    private Long uid;
    /**
     * список линий
     */
    @ManyToMany(fetch = FetchType.LAZY)
    /*@JoinTable(
            name = "REGISTRATION_AGENTCARDHOLDER", schema = "oaspn",
            joinColumns = @JoinColumn(name = "registration_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "registration_id_fkey")),
            inverseJoinColumns = @JoinColumn(name = "agent_card_holder", referencedColumnName = "persId", foreignKey = @ForeignKey(name = "agent_card_holder_fkey")))*/
    private List<WayEntity> ways;
    /**
     * дополнительная информация
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TagEntity> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationEntity that = (RelationEntity) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(visible, that.visible)) return false;
        if (!Objects.equals(version, that.version)) return false;
        if (!Objects.equals(changeset, that.changeset)) return false;
        if (!Objects.equals(timestamp, that.timestamp)) return false;
        if (!Objects.equals(user, that.user)) return false;
        return Objects.equals(uid, that.uid);
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
