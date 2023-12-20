package com.example.demo.dao.pojo.openstreetmap;

import com.example.demo.dao.pojo.openstreetmap.node.NodeMaps;
import com.example.demo.dao.pojo.openstreetmap.relation.RelationMaps;
import com.example.demo.dao.pojo.openstreetmap.way.WayMaps;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * version="0.6"
 * generator="CGImap 0.8.10 (3476766 spike-07.openstreetmap.org)"
 * copyright="OpenStreetMap and contributors"
 * license="http://opendatacommons.org/licenses/odbl/1-0/"
 */

@Data
@XmlRootElement(name = "osm")
@XmlAccessorType(XmlAccessType.FIELD)
public class OsmMaps {

    @XmlAttribute(name = "version")
    private String version;
    @XmlAttribute(name = "generator")
    private String generator;
    @XmlAttribute(name = "copyright")
    private String copyright;
    @XmlAttribute(name = "attribution")
    private String attribution;
    @XmlAttribute(name = "license")
    private String license;
    @XmlElement(name="node")
    private List<NodeMaps> nodes = new ArrayList<>();
    @XmlElement(name="way")
    private List<WayMaps> ways = new ArrayList<>();
    @XmlElement(name="relation")
    private List<RelationMaps> relations = new ArrayList<>();
    @XmlElement(name="bounds")
    private BoundsMaps bounds;
}
