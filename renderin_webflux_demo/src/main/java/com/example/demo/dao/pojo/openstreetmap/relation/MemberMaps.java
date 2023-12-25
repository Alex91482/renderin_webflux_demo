package com.example.demo.dao.pojo.openstreetmap.relation;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MemberMaps {

    @XmlAttribute(name = "type")
    private String type;
    @XmlAttribute(name = "ref")
    private String ref;
    @XmlAttribute(name = "role")
    private String role;
}
