package com.example.demo.dao.pojo.rendering;

import com.example.demo.dao.pojo.openstreetmap.TagMaps;
import com.example.demo.dao.pojo.openstreetmap.node.NodeMaps;
import com.example.demo.dao.pojo.openstreetmap.way.WayMaps;
import com.example.demo.dao.pojo.openstreetmap.way.WayNdMaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinePojo {

    private final List<CoordinateXY> coordinateXYList;

    public LinePojo(WayMaps wayMaps, List<NodeMaps> nodeMapsList,
                    Double maxLat0, Double maxLon0, Double minLat0, Double minLon0,
                    Integer height0, Integer width0
    ){
        WayMaps myWayMaps = createClone(wayMaps);
        List<NodeMaps> myNodeMapsList = new ArrayList<>(nodeMapsList);
        Double maxLat = maxLat0;
        Double maxLon = maxLon0;
        Double minLat = minLat0;
        Double minLon = minLon0;
        Integer height = height0;
        Integer width = width0;
        Double stepLat = (maxLat - minLat) / 100;
        Double stepLon = (maxLon - minLon) / 100;
        Double stepX = Double.valueOf(width) / 100;
        Double stepY = Double.valueOf(height) / 100;
        List<NodeMaps> nodeMapsList1 = getNodeByWay(myWayMaps, myNodeMapsList);
        coordinateXYList = nodeMapsList1.stream()
                .filter(NodeMaps::getVisible)
                .map(nodeMaps -> new CoordinateXY(
                        convertToPixelWeightPoint(nodeMaps.getLon(), minLon, stepLon, stepX),
                        convertToPixelHeightPoint(nodeMaps.getLat(), minLat, stepLat, stepY)
                ))
                .toList();
    }

    private int convertToPixelHeightPoint(Double lat, Double minLat, Double stepLat, Double stepY){
        return (int) Math.round((lat - minLat) / stepLat * stepY);
    }

    private int convertToPixelWeightPoint(Double lon, Double minLon, Double stepLon, Double stepX){
        return (int) Math.round((lon - minLon) / stepLon * stepX);
    }

    private static List<NodeMaps> getNodeByWay(WayMaps wayMaps, final List<NodeMaps> nodeMaps){
        List<NodeMaps> nodeMapsList = new ArrayList<>();
        final List<Long> nodeId = wayMaps.getNds().stream().map(WayNdMaps::getRef).peek(System.out::println).toList();
        System.out.println("nodeId size = " + nodeId.size());
        nodeMapsList = nodeId.stream()
                .map(nodeId1 -> {
                    NodeMaps nodeMaps1 = null;
                    for(NodeMaps nodeMaps2 : nodeMaps){
                        if(Objects.equals(nodeMaps2.getId(), nodeId1)){
                            nodeMaps1 = nodeMaps2;
                            break;
                        }
                    }
                    return nodeMaps1;
                })
                .filter(Objects::nonNull)
                .toList();
        return nodeMapsList;
    }

    private WayMaps createClone(WayMaps wayMaps){
        WayMaps wayMaps1 = new WayMaps();
        wayMaps1.setId(wayMaps.getId());
        wayMaps1.setVisible(wayMaps.getVisible());
        wayMaps1.setVersion(wayMaps.getVersion());
        wayMaps1.setChangeset(wayMaps.getChangeset());
        wayMaps1.setTimestamp(wayMaps.getTimestamp());
        wayMaps1.setUser(wayMaps.getUser());
        wayMaps1.setUid(wayMaps.getUid());
        List<WayNdMaps> nds = new ArrayList<>(wayMaps.getNds());
        wayMaps1.setNds(nds);
        List<TagMaps> tagMaps = new ArrayList<>(wayMaps.getTagMaps());
        wayMaps1.setTagMaps(tagMaps);
        return wayMaps1;
    }

    public List<CoordinateXY> getCoordinateXYList(){
        return coordinateXYList;
    }

    public static class CoordinateXY{
        private final Integer coordinateX;
        private final Integer coordinateY;

        public CoordinateXY(Integer coordinateX, Integer coordinateY){
            this.coordinateX = coordinateX;
            this.coordinateY = coordinateY;
        }

        public Integer getCoordinateX(){
            return coordinateX;
        }

        public Integer getCoordinateY(){
            return coordinateY;
        }
    }
}
