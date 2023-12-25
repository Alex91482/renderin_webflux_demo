package com.example.demo.dao.implement;

import com.example.demo.dao.entity.MyLine;
import com.example.demo.dao.interfaces.MyLineDAO;
import com.example.demo.dao.repository.MyLineRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class MyLineDAOImpl implements MyLineDAO {

    private final MyLineRepository myLineRepository;

    public MyLineDAOImpl(MyLineRepository myLineRepository){
        this.myLineRepository = myLineRepository;
    }

    @Override
    public Mono<MyLine> findById(Long id) {
        return myLineRepository.findById(id);
    }

    @Override
    public Flux<MyLine> findAll() {
        return myLineRepository.findAll();
    }

    @Override
    public Mono<MyLine> update(Long id, MyLine myLine) {
        return myLineRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
                .flatMap(optionalMmyObject -> {
                    if (optionalMmyObject.isPresent()) {
                        MyLine myLine1 = optionalMmyObject.get();
                        myLine1.setGeometry(myLine.getGeometry());
                        myLine1.setRgbParameter(myLine.getRgbParameter());
                        return myLineRepository.save(myLine1);
                    }else{
                        return save(myLine);
                    }
                });
    }

    @Override
    public Mono<MyLine> save(MyLine myLine) {
        return myLineRepository.save(myLine);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return myLineRepository.deleteById(id);
    }

    @Override
    public Flux<MyLine> getMyObjectLimitTen(){
        return myLineRepository.getMyObjectLimitTen();
    }
}
