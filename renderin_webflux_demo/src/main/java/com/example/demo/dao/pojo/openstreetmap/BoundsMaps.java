package com.example.demo.dao.pojo.openstreetmap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class BoundsMaps {

    @XmlAttribute(name = "minlat")
    private Double minlat;
    @XmlAttribute(name = "minlon")
    private Double minlon;
    @XmlAttribute(name = "maxlat")
    private Double maxlat;
    @XmlAttribute(name = "maxlon")
    private Double maxlon;
}
