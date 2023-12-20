package com.example.demo.dao.interfaces;

import com.example.demo.dao.entity.MyObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MyObjectsDAO {

    Mono<MyObject> findById(Long id);
    Flux<MyObject> findAll();
    Mono<MyObject> update(Long id, MyObject myObject);
    Mono<MyObject> save(MyObject myObject);
    Mono<Void> deleteById(Long id);
    Flux<MyObject> getMyObjectByIntervalGeoLatAndGeoLon(Double minGeoLat, Double minGeoLon, Double maxGeoLat, Double maxGeoLon);
    Flux<MyObject> getMyObjectLimitTen();
}
