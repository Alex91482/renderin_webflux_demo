package com.example.demo.init;

import com.example.demo.dao.entity.MyLine;
import com.example.demo.dao.implement.MyLineDAOImpl;
import com.example.demo.dao.interfaces.MyLineDAO;
import com.example.demo.dao.pojo.openstreetmap.OsmMaps;
import com.example.demo.dao.pojo.openstreetmap.node.NodeMaps;
import com.example.demo.dao.pojo.openstreetmap.way.WayMaps;
import com.example.demo.dao.pojo.openstreetmap.way.WayNdMaps;
import jakarta.xml.bind.JAXBContext;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DataInit implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInit.class);

    private static final String PATH_TO_RESOURCE  = "./src/main/resources/test_data/map_pp_burg.osm";
    private static final String PATH_TO_RESOURCE1 = "./src/main/resources/test_data/map_v2.osm";
    private static final String PATH_TO_RESOURCE2 = "./src/main/resources/test_data/cathedral.osm";

    private final MyLineDAO myObjectsDAO;

    public DataInit(MyLineDAOImpl myObjectsDAO){
        this.myObjectsDAO = myObjectsDAO;
    }

    @Override
    public void run(ApplicationArguments args) {
        //start1();
    }

    private void start1(){
        dbFill().subscribe(
                next -> {
                    if(next){
                        logger.info("Data initialization complete");
                    }else{
                        logger.info("No data initialization required");
                    }
                },
                error -> {
                    logger.error("An exception occurred during data initialization: {}", error.getMessage());
                }
        );
    }

    public Mono<Boolean> dbFill(){
        return myObjectsDAO.getMyObjectLimitTen()
                .count()
                .flatMap(result -> {
                    if(result == 10){
                        return Mono.just(Optional.empty());
                    }else{
                        return getOsmMaps();
                    }
                })
                .flatMap(osmResult -> {
                    if(osmResult.isEmpty()){
                        return Mono.just(false);
                    }else{
                        return Mono.create(emitter -> {
                            var osmMaps = osmResult.get();
                            readOsrm((OsmMaps) osmMaps)
                                    .map(myObjectsDAO::save)
                                    .subscribe(
                                            Mono::subscribe,
                                            error -> logger.error("Error in section init data: {}", error.getMessage()),
                                            () -> emitter.success(true)
                                    );
                        });
                    }
                });
    }

    /**
     * Метод читает xml файл для создания объекта OsmMaps
     * @return если существуют файлы для чтения вернет Optional<OsmMaps> если нет то Optional.empty()
     */
    private Mono<Optional<OsmMaps>> getOsmMaps(){
        return Mono.fromSupplier(() -> {
            String patchToResource = "";
            if(new File(PATH_TO_RESOURCE).exists()){
                patchToResource = PATH_TO_RESOURCE;
            }else if(new File(PATH_TO_RESOURCE1).exists()){
                patchToResource = PATH_TO_RESOURCE1;
            }else if(new File(PATH_TO_RESOURCE2).exists()){
                patchToResource = PATH_TO_RESOURCE2;
            }
            logger.info("Patch to resource: {}", patchToResource);
            try(var br = new BufferedReader(new FileReader(patchToResource))) {
                var body = br.lines().collect(Collectors.joining());
                var reader = new StringReader(body);
                var context = JAXBContext.newInstance(OsmMaps.class);
                var unmarshaller = context.createUnmarshaller();
                OsmMaps osmMaps = (OsmMaps) unmarshaller.unmarshal(reader);
                return Optional.ofNullable(osmMaps);
            }catch (Exception e){
                logger.error("Exception on creation OsmMaps: {}", e.getMessage());
                e.printStackTrace();
            }
            return Optional.empty();
        });
    }

    /**
     * Метод по преобразованию линий их xml в сущности для бд
     * @param osmMaps прочитанный xml документ
     * @return возвращает поток сущностей
     */
    private Flux<MyLine> readOsrm(final OsmMaps osmMaps){
        return Flux.create(emitter -> {
            try {
                var nodeList = new ArrayList<NodeMaps>(osmMaps.getNodes());
                for (WayMaps wayMaps : osmMaps.getWays()) {
                    final List<Long> nodeId = wayMaps.getNds().stream().map(WayNdMaps::getRef).toList();
                    var nodes = nodeId.stream().map(nodeId1 -> {
                                NodeMaps nodeMaps1 = null;
                                for (NodeMaps nodeMaps2 : nodeList) {
                                    if (Objects.equals(nodeMaps2.getId(), nodeId1)) {
                                        nodeMaps1 = nodeMaps2;
                                        break;
                                    }
                                }
                                return nodeMaps1;
                            })
                            .filter(Objects::nonNull)
                            .toList();

                    MyLine myLine = myLineConverter(wayMaps, nodes);
                    emitter.next(myLine);
                }
                emitter.complete();
            }catch (Exception e){
                emitter.error(new RuntimeException(e));
            }
        });
    }

    /**
     * Метод преобразует данные полученные из xml в сущность
     * @param wayMaps линия полученная их xml
     * @param nodeMaps список связаных с линией точек из xml, порядок точек сохранен
     * @return возвращает сущность заполненую данными
     */
    private MyLine myLineConverter(final WayMaps wayMaps, final List<NodeMaps> nodeMaps){
        var geometryFactory = new GeometryFactory();
        var coordinateArrayList = new ArrayList<Coordinate>();
        for (NodeMaps nodeMap : nodeMaps){
            coordinateArrayList.add(new Coordinate(nodeMap.getLon(), nodeMap.getLat()));
        }
        var lineString = geometryFactory.createLineString(coordinateArrayList.toArray(Coordinate[]::new));

        var myLine = new MyLine();
        myLine.setRgbParameter("0,0,225");
        myLine.setId_osm(wayMaps.getId());
        myLine.setGeometry(lineString);
        return myLine;
    }
}
