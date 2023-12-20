package com.example.demo.dao.repository;

import com.example.demo.dao.entity.MyObject;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MyObjectRepository extends R2dbcRepository<MyObject, Long> {

    @Query(value="select * from testdb.my_object mo where mo.geoLat >= :minGeoLat and mo.geoLat <= :maxGeoLat and mo.geoLon >= :minGeoLon ang mo.geoLat <= :maxGeoLon")
    Flux<MyObject> getMyObjectByIntervalGeoLatAndGeoLon(Double minGeoLat, Double minGeoLon, Double maxGeoLat, Double maxGeoLon);

    @Query(value = "select * from testdb.my_object limit 10")
    Flux<MyObject> getMyObjectLimitTen();
}
