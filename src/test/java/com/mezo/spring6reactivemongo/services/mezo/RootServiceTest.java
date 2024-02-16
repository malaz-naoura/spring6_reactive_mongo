package com.mezo.spring6reactivemongo.services.mezo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RootServiceTest<T> {

    T getSavedObject();

    void listObjects();

    void getObjectById();

    void saveObject();

    void updateObject();

    void deleteObjectById();

    default void patchObject(){}

    default void deleteObject(){}
}
