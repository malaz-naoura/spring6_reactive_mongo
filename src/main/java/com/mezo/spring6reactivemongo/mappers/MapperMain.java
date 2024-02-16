package com.mezo.spring6reactivemongo.mappers;

public interface MapperMain<T,D> {

    T dtoToObj(D dto);

    D objToDto(T obj);
}
