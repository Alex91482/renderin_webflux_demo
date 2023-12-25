package com.example.demo.model;

import lombok.Data;

@Data
public class Bbox {

    /**
     * минимальная широта
     */
    private Double geoLatMin;
    /**
     * минимальная долгота
     */
    private Double geoLonMin;
    /**
     * максимальная широта
     */
    private Double geoLatMax;
    /**
     * максемальная долгота
     */
    private Double geoLonMax;
}
