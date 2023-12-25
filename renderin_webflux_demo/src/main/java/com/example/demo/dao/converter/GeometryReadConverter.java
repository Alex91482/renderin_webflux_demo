package com.example.demo.dao.converter;

import lombok.NonNull;
import org.locationtech.jts.geom.Geometry;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class GeometryReadConverter implements Converter<Geometry, Geometry> {
    @Override
    public Geometry convert(@NonNull Geometry source) {
        return source;
    }
}
