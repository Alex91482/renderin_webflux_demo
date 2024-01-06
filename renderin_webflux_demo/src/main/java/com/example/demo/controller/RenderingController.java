package com.example.demo.controller;

import com.example.demo.model.RenderingRequest;
import com.example.demo.services.interfaces.RenderingService;
import com.example.demo.services.java2drendering.RenderingServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

/**
 * get контроллером со следующими параметрами:
 * width ширина изображения
 * height высота изображения
 * bbox: минимальная и максимальная координата прямоугольной области, в которой находятся объекты, которые нужно отобразить (обычно в координатах 3857)
 */
@Controller
public class RenderingController {

    private final RenderingService renderingService;

    public RenderingController(RenderingServiceImpl renderingService){
        this.renderingService = renderingService;
    }

    @RequestMapping(value = "/rendering", method = RequestMethod.POST)
    public Mono<ResponseEntity<Mono<byte[]>>> getRenderingMap(@RequestBody RenderingRequest renderingRequest){
        return Mono.just(
                ResponseEntity
                        .ok()
                        .body(renderingService.getPageByArea(renderingRequest))
        );
    }
}
