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
}
