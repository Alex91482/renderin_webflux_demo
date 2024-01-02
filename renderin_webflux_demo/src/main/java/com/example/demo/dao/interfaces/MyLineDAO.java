package com.example.demo.dao.interfaces;

import com.example.demo.dao.entity.MyLine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MyLineDAO {

    Mono<MyLine> findById(Long id);
    Flux<MyLine> findAll();
    Mono<MyLine> update(Long id, MyLine myLine);
    Mono<MyLine> save(MyLine myLine);
    Mono<Void> deleteById(Long id);
    Flux<MyLine> getMyObjectLimitTen();
    Flux<MyLine> getMyLineByCoordinatePlane(Double maxLat, Double maxLon, Double minLat, Double minLon);
}
