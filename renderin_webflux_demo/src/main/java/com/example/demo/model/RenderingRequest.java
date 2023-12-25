package com.example.demo.model;

import lombok.Data;

@Data
public class RenderingRequest {

    /**
     * ширина изображения
     */
    private Integer width;
    /**
     * высота изображения
     */
    private Integer height;
    /**
     * минимальная и максимальная координата прямоугольной области, в которой находятся объекты, которые нужно отобразить (в координатах 3857)
     */
    private Bbox bbox;
}
