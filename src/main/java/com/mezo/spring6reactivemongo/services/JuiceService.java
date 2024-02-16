package com.mezo.spring6reactivemongo.services;

import com.mezo.spring6reactivemongo.model.JuiceDTO;
import com.mezo.spring6reactivemongo.services.mezo.RootService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JuiceService extends RootService<JuiceDTO,String> {

    Mono<JuiceDTO> getObjectByName(String juiceName);

    Flux<JuiceDTO> getAllObjectByStyle(String juiceStyle);
}
