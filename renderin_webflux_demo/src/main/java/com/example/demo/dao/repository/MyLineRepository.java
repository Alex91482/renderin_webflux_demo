package com.example.demo.dao.repository;

import com.example.demo.dao.entity.MyLine;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MyLineRepository extends R2dbcRepository<MyLine, Long> {

    @Query(value = "select * from public.line limit 10")
    Flux<MyLine> getMyObjectLimitTen();

    @Query(value = "select * from line_test lt where ST_Intersects(lt.geom , ST_MakeEnvelope(?4, ?3, ?2, ?1, 4326))")
    Flux<MyLine> getMyLineByCoordinatePlane(Double maxLat, Double maxLon, Double minLat, Double minLon);
}
