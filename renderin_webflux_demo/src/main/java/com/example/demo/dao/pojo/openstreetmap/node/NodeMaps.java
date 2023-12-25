package com.example.demo.dao.pojo.openstreetmap.node;

import com.example.demo.dao.pojo.openstreetmap.TagMaps;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeMaps {

    @XmlAttribute(name = "id")
    private Long id;
    @XmlAttribute(name = "visible")
    private Boolean visible;
    @XmlAttribute(name = "version")
    private Integer version;
    @XmlAttribute(name = "changeset")
    private Long changeset;
    @XmlAttribute(name = "timestamp")
    private String timestamp;
    @XmlAttribute(name = "user")
    private String user;
    @XmlAttribute(name = "uid")
    private Long uid;
    @XmlAttribute(name = "lat")
    private Double lat;
    @XmlAttribute(name = "lon")
    private Double lon;
    @XmlElement(name="tag")
    private List<TagMaps> tagMaps = new ArrayList<>();
}
