package com.example.demo.dao.implement;

import com.example.demo.dao.entity.MyObject;
import com.example.demo.dao.interfaces.MyObjectsDAO;
import com.example.demo.dao.repository.MyObjectRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class MyObjectsDAOImpl implements MyObjectsDAO {

    private final MyObjectRepository myObjectRepository;

    public MyObjectsDAOImpl(MyObjectRepository myObjectRepository){
        this.myObjectRepository = myObjectRepository;
    }

    @Override
    public Mono<MyObject> findById(Long id) {
        return myObjectRepository.findById(id);
    }

    @Override
    public Flux<MyObject> findAll() {
        return myObjectRepository.findAll();
    }

    @Override
    public Mono<MyObject> update(Long id, MyObject myObject) {
        return myObjectRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(optionalMmyObject -> {
                    if (optionalMmyObject.isPresent()) {
                        MyObject myObject1 = optionalMmyObject.get();
                        myObject1.setGeoLat(myObject.getGeoLat());
                        myObject1.setGeoLon(myObject.getGeoLon());
                        myObject1.setGeometry(myObject.getGeometry());
                        myObject1.setRgbParameter(myObject.getRgbParameter());
                        return myObjectRepository.save(myObject1);
                    }

                    return Mono.empty();
                });
    }

    @Override
    public Mono<MyObject> save(MyObject myObject) {
        return myObjectRepository.save(myObject);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return myObjectRepository.deleteById(id);
    }

    @Override
    public Flux<MyObject> getMyObjectByIntervalGeoLatAndGeoLon(Double minGeoLat, Double minGeoLon, Double maxGeoLat, Double maxGeoLon){
        return myObjectRepository.getMyObjectByIntervalGeoLatAndGeoLon(minGeoLat, minGeoLon, maxGeoLat, maxGeoLon);
    }

    @Override
    public Flux<MyObject> getMyObjectLimitTen(){
        return myObjectRepository.getMyObjectLimitTen();
    }
}
