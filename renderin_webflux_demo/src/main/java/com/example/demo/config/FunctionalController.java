package com.example.demo.config;

import com.example.demo.model.RenderingRequest;
import com.example.demo.services.interfaces.RenderingService;
import com.example.demo.services.java2drendering.RenderingServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//@Configuration
public class FunctionalController {

    private final RenderingService renderingService;

    public FunctionalController(RenderingServiceImpl renderingService){
        this.renderingService = renderingService;
    }

    //@Bean
    public RouterFunction<ServerResponse> getRenderingMap(){
        return route(POST("/rendering"), request ->
            request.body(toMono(RenderingRequest.class))
                    .flatMap(renderingService::getPageByArea)
                    .flatMap(value ->
                            ServerResponse
                                    .ok()
                                    .contentType(MediaType.IMAGE_PNG)
                                    .body(value, byte[].class)
                    )
                );
    }
}
