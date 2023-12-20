package com.example.demo.init;

import com.example.demo.dao.pojo.openstreetmap.BoundsMaps;
import com.example.demo.dao.pojo.openstreetmap.OsmMaps;
import com.example.demo.dao.pojo.openstreetmap.node.NodeMaps;
import com.example.demo.dao.pojo.openstreetmap.way.WayMaps;
import com.example.demo.dao.pojo.openstreetmap.way.WayNdMaps;
import jakarta.xml.bind.JAXBContext;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Core {

    private static final String PATH_TO_RESOURCE = "./src/main/resources/test_data/cathedral.osm";

    public static void main(String[]args){
        try(var br = new BufferedReader(new FileReader(PATH_TO_RESOURCE))) {

            var body = br.lines().collect(Collectors.joining());
            var reader = new StringReader(body);
            var context = JAXBContext.newInstance(OsmMaps.class);
            var unmarshaller = context.createUnmarshaller();
            var osm = (OsmMaps) unmarshaller.unmarshal(reader);

            for(WayNdMaps wayNdMaps : osm.getWays().get(1).getNds()){
                System.out.println(wayNdMaps.getRef());
            }
            List<NodeMaps> nodeMaps = getNodeByWay(osm.getWays().get(1), osm.getNodes());

            JFrame frame = new JFrame("Window");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 700);
            frame.setUndecorated(true);
            frame.setVisible(true);
            frame.add(new StrokeTest(nodeMaps, osm.getBounds(), 700, 700));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<NodeMaps> getNodeByWay(WayMaps wayMaps, final List<NodeMaps> nodeMaps){
        List<NodeMaps> nodeMapsList = new ArrayList<>();
        final List<Long> nodeId = wayMaps.getNds().stream().map(WayNdMaps::getRef).peek(System.out::println).toList();
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

    static class StrokeTest extends JPanel{

        private final List<NodeMaps> nodeMapsList;
        private final Integer height;
        private final Integer width;
        private Double maxLat;
        private Double maxLon;
        private Double minLat;
        private Double minLon;
        private Double stepLat;
        private Double stepLon;
        private Double stepX;
        private Double stepY;

        public StrokeTest(List<NodeMaps> nodeMapsList, BoundsMaps bounds, Integer height, Integer width) {
            this.height = height;
            this.width = width;
            this.nodeMapsList = nodeMapsList;
            this.setSize(width, height);
            this.setVisible(true);
            init(nodeMapsList, bounds);
        }

        private void init(final List<NodeMaps> list, final BoundsMaps bounds){
            if(bounds != null){
                maxLat = bounds.getMaxlat();
                maxLon = bounds.getMaxlon();
                minLat = bounds.getMinlat();
                minLon = bounds.getMinlon();
            }else {
                List<NodeMaps> myListNodeMaps = new ArrayList<>(list);
                OptionalDouble optionalMaxLat = myListNodeMaps.stream().mapToDouble(NodeMaps::getLat).max();
                if(optionalMaxLat.isPresent()){
                    maxLat = optionalMaxLat.getAsDouble();
                    System.out.println("maxLat = " + maxLat);
                }
                OptionalDouble optionalMaxLon =  myListNodeMaps.stream().mapToDouble(NodeMaps::getLon).max();
                if(optionalMaxLon.isPresent()){
                    maxLon = optionalMaxLon.getAsDouble();
                    System.out.println("maxLon = " + maxLon);
                }
                OptionalDouble optionalMinLat = myListNodeMaps.stream().mapToDouble(NodeMaps::getLat).min();
                if(optionalMinLat.isPresent()){
                    minLat = optionalMinLat.getAsDouble();
                    System.out.println("minLat = " + minLat);
                }
                OptionalDouble optionalMinLon =  myListNodeMaps.stream().mapToDouble(NodeMaps::getLon).min();
                if(optionalMinLon.isPresent()){
                    minLon = optionalMinLon.getAsDouble();
                    System.out.println("minLon = " + minLon);
                }
            }
            stepLat = (maxLat - minLat) / 100  ; // 0.003920800000003055
            stepLon = (maxLon - minLon) / 100  ; // 0.00901619999999781
            stepX = Double.valueOf(width) / 100;
            stepY = Double.valueOf(height) / 100;
            System.out.println("stepLat = " + stepLat + " stepLon = " + stepLon + " stepX = " + stepX + " stepY = " + stepY);
        }

        public void paint(Graphics gr1){
            Graphics2D gr = (Graphics2D) gr1;
            gr.setColor(Color.BLUE);

            for(NodeMaps nodeMaps : nodeMapsList){
                System.out.println("id = " + nodeMaps.getId() + " lat = " + nodeMaps.getLat() + " lon = " + nodeMaps.getLon());
            }

            for(int i = 1; i < nodeMapsList.size(); i++){
                double x1 = nodeMapsList.get(i - 1).getLon();
                double y1 = nodeMapsList.get(i - 1).getLat();
                double x2 = nodeMapsList.get(i).getLon();
                double y2 = nodeMapsList.get(i).getLat();
                int xStart = convertToPixelWeightPoint(x1);
                int yStart = convertToPixelHeightPoint(y1);
                int xEnd = convertToPixelWeightPoint(x2);
                int yEnd = convertToPixelHeightPoint(y2);
                gr.drawLine(xStart, yStart, xEnd, yEnd);
                System.out.println("xStart = " + xStart + " xEnd = " + xEnd + " yStart = " + yStart + " yEnd = " + yEnd);
            }
        }

        private int convertToPixelHeightPoint(Double lat){
            return (int) Math.round(
                    (lat - minLat) / stepLat * stepY
            );
        }

        private int convertToPixelWeightPoint(Double lon){
            return (int) Math.round(
                    (lon - minLon) / stepLon * stepX
            );
        }
    }


}
