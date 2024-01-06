package com.example.demo.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "line")
public class MyLine {

    @Id
    private Long id;
    /**
     * id из базы OpenStreetMap
     */
    private Long id_osm;
    /**
     * геометрия
     */
    private Geometry geometry;
    /**
     * цветоопределяющая характеристика
     */
    private String rgbParameter;

    @Override
    public String toString() {
        return "MyLine{" +
                "id=" + id +
                ", id_osm=" + id_osm +
                ", geometry=" + geometry.toString() +
                ", rgbParameter='" + rgbParameter + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyLine myLine = (MyLine) o;

        if (!Objects.equals(id, myLine.id)) return false;
        return Objects.equals(rgbParameter, myLine.rgbParameter);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rgbParameter != null ? rgbParameter.hashCode() : 0);
        return result;
    }
}
