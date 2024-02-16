package com.mezo.spring6reactivemongo.repository;

import com.mezo.spring6reactivemongo.domain.Juice;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface JuiceRepo extends ReactiveMongoRepository<Juice,String> {

    Mono<Juice> findJuiceByName(String name);

    Flux<Juice> findAllByStyle(String style);
}
