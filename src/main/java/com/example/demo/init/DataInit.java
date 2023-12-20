package com.example.demo.init;

import com.example.demo.dao.entity.MyObject;
import com.example.demo.dao.implement.MyObjectsDAOImpl;
import com.example.demo.dao.pojo.openstreetmap.node.NodeMaps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class DataInit {

    private static final Logger logger = LoggerFactory.getLogger(DataInit.class);

    private static final String PATH_TO_RESOURCE = "./src/main/resource/test_data/map_pp_burg.osm";
    private static final String PATH_TO_RESOURCE1 = "./src/main/resources/test_data/map_v2.osm";
    private static final String PATH_TO_RESOURCE2 = "./src/main/resources/test_data/cathedral.osm";

    private final MyObjectsDAOImpl myObjectsDAO;

    public DataInit(MyObjectsDAOImpl myObjectsDAO){
        this.myObjectsDAO = myObjectsDAO;
    }

    /*public Mono<Void> dbFill(){
        return myObjectsDAO.getMyObjectLimitTen()
                .count()
                .flatMap(result -> {
                    if(result == 10){
                        return Flux.empty();
                    }else{
                        return Flux.create(emitter -> {
                            var dbf = DocumentBuilderFactory.newInstance();
                            var db  = dbf.newDocumentBuilder();
                            var doc = db.parse(new File(PATH_TO_RESOURCE));
                        });
                    }
                });
    }*/

    private Flux<MyObject> readOsrmAndConvert(){
        return Flux.create(emitter -> {

        });
    }

    private MyObject myObjectConverter(NodeMaps nodeMaps){
        MyObject myObject = new MyObject();
        myObject.setRgbParameter("0,0,0");
        myObject.setGeoLat(nodeMaps.getLat());
        myObject.setGeoLon(nodeMaps.getLon());
        //myObject.setGeometry(); // ???
        return myObject;
    }
}
