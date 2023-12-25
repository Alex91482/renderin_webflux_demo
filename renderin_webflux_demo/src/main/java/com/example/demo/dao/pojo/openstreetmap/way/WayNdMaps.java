package com.example.demo.dao.pojo.openstreetmap.way;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class WayNdMaps {
    @XmlAttribute(name = "ref")
    private Long ref;
}
