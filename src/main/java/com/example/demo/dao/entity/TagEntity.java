package com.example.demo.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

/**
 * Объект — это элемент (точка, линия, отношение) с набором тегов (атрибутов). Тег (tag) определён как k=«ключ» v=«значение».
 * Если элемент не имеет тегов, то он не является объектом, а входит в состав других объектов (с тегами тоже может входить).
 * Обязательных тегов у объекта нет.
 */
@Entity
@Data
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String key;
    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEntity tagEntity = (TagEntity) o;

        if (!Objects.equals(id, tagEntity.id)) return false;
        if (!Objects.equals(key, tagEntity.key)) return false;
        return Objects.equals(value, tagEntity.value);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
