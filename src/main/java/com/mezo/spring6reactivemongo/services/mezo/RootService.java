package com.mezo.spring6reactivemongo.services.mezo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RootService<OBJ,ID> {

    Flux<OBJ> getAllObjects();

    Mono<OBJ> getObjectById(ID objectId);

    Mono<OBJ> saveObject(OBJ object);

    Mono<OBJ> updateObject(OBJ object);

    Mono<Void> deleteObjectById(ID objectId);

    default Mono<OBJ> patchObject(OBJ obj){
        throw new RuntimeException("use not implemented method");
    }

    default Mono<Void> deleteObject(OBJ object){
        throw new RuntimeException("use not implemented method");
    }


}
