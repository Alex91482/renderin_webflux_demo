package com.example.demo.init;

import com.example.demo.dao.pojo.openstreetmap.OsmMaps;
import com.example.demo.dao.pojo.openstreetmap.node.NodeMaps;
import com.example.demo.dao.pojo.openstreetmap.relation.RelationMaps;
import com.example.demo.dao.pojo.openstreetmap.way.WayMaps;
import com.example.demo.dao.pojo.rendering.LinePojo;
import jakarta.xml.bind.JAXBContext;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Core0 {

    private static final String PATH_TO_RESOURCE = "./src/main/resources/test_data/map_pp_burg.osm";
    private static final String PATH_TO_RESOURCE1 = "./src/main/resources/test_data/map_v2.osm";
    private static final String PATH_TO_RESOURCE2 = "./src/main/resources/test_data/cathedral.osm";
    private static final Integer HEIGHT = 1000;
    private static final Integer WIDTH = 1000;

    public static void main(String[]args){

        var osm =  getOsmFromXml(PATH_TO_RESOURCE);
        var linePojoList = getAllWay(osm);

        JFrame frame = new JFrame("Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.add(new StrokeTest0(linePojoList, WIDTH, HEIGHT));
    }

    private static List<LinePojo> getByRelation(OsmMaps osm){
        var relationList = osm.getRelations();
        var wayList = new ArrayList<WayMaps>(osm.getWays());
        var wayBuReverence = relationList.stream()
                .map(RelationMaps::getMembers)
                .flatMap(Collection::stream)
                .filter(memberMaps -> memberMaps.getType().equals("way"))
                .map(memberMaps -> {
                    WayMaps wayMaps0 = null;
                    for(WayMaps wayMaps : wayList){
                        if(Long.parseLong(memberMaps.getRef()) == wayMaps.getId()){
                            wayMaps0 = wayMaps;
                            break;
                        }
                    }
                    return wayMaps0;
                }).filter(Objects::nonNull).toList();

        double maxLat = osm.getBounds().getMaxlat();
        double maxLon = osm.getBounds().getMaxlon();
        double minLat = osm.getBounds().getMinlat();
        double minLon = osm.getBounds().getMinlon();

        return wayBuReverence.stream()
                .parallel()
                .map(wayMaps -> new LinePojo(wayMaps, osm.getNodes(), maxLat, maxLon, minLat, minLon, HEIGHT, WIDTH))
                .toList();
    }

    private static List<LinePojo> getAllWay(OsmMaps osm){
        double maxLat = osm.getBounds().getMaxlat();
        double maxLon = osm.getBounds().getMaxlon();
        double minLat = osm.getBounds().getMinlat();
        double minLon = osm.getBounds().getMinlon();
        return osm.getWays().stream()
                .parallel()
                .map(wayMaps -> new LinePojo(wayMaps, osm.getNodes(), maxLat, maxLon, minLat, minLon, HEIGHT, WIDTH))
                .toList();
    }

    private static OsmMaps getOsmFromXml(String patchToResource){
        OsmMaps osmMaps = null;
        try(var br = new BufferedReader(new FileReader(patchToResource))) {
            var body = br.lines().collect(Collectors.joining());
            var reader = new StringReader(body);
            var context = JAXBContext.newInstance(OsmMaps.class);
            var unmarshaller = context.createUnmarshaller();
            osmMaps = (OsmMaps) unmarshaller.unmarshal(reader);
        }catch (Exception e){
            e.printStackTrace();
        }
        return osmMaps;
    }

    static class StrokeTest0 extends JPanel{

        private final List<LinePojo> linePojoList;

        public StrokeTest0(List<LinePojo> linePojoList, Integer width, Integer height){
            this.linePojoList = linePojoList;
            this.setSize(width, height);
            this.setVisible(true);
        }

        public void paint(Graphics gr){
            for(LinePojo linePojo : linePojoList) {
                printLine(linePojo, gr);
            }
        }

        private void printLine(LinePojo linePojo, Graphics gr1){
            Graphics2D gr = (Graphics2D) gr1;
            gr.setColor(Color.BLUE);

            Integer xStart = -1;
            Integer yStart = -1;
            Integer xEnd = 0;
            Integer yEnd = 0;
            for(LinePojo.CoordinateXY coordinateXY : linePojo.getCoordinateXYList()){
                if(xStart == -1 && yStart == -1){

                    xStart = coordinateXY.getCoordinateX();
                    yStart = coordinateXY.getCoordinateY();

                }else{

                    xEnd = coordinateXY.getCoordinateX();
                    yEnd = coordinateXY.getCoordinateY();

                    gr.drawLine(xStart, yStart, xEnd, yEnd);

                    xStart = xEnd;
                    yStart = yEnd;
                }
            }
        }
    }
}
