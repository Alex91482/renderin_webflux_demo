package com.example.demo.services.interfaces;

import com.example.demo.model.RenderingRequest;
import reactor.core.publisher.Mono;

public interface RenderingService {

    Mono<byte[]> getPageByArea(RenderingRequest renderingRequest);
}
