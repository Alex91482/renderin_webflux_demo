package com.example.demo.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class MyObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * широта
     */
    private Double geoLat;
    /**
     * долгота
     */
    private Double geoLon;
    /**
     * геометрия
     */
    private String geometry;
    /**
     * цветоопределяющая характеристика
     */
    private String rgbParameter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyObject myObject = (MyObject) o;

        if (!id.equals(myObject.id)) return false;
        if (!Objects.equals(geoLat, myObject.geoLat)) return false;
        if (!Objects.equals(geoLon, myObject.geoLon)) return false;
        if (!Objects.equals(geometry, myObject.geometry)) return false;
        return Objects.equals(rgbParameter, myObject.rgbParameter);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (geoLat != null ? geoLat.hashCode() : 0);
        result = 31 * result + (geoLon != null ? geoLon.hashCode() : 0);
        result = 31 * result + (geometry != null ? geometry.hashCode() : 0);
        result = 31 * result + (rgbParameter != null ? rgbParameter.hashCode() : 0);
        return result;
    }
}
