package com.example.demo.dao.pojo.openstreetmap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class TagMaps {

    @XmlAttribute(name = "k")
    private String k;
    @XmlAttribute(name = "v")
    private String v;
}
