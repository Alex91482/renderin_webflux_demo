package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
