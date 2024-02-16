package com.mezo.spring6reactivemongo.services;

import com.mezo.spring6reactivemongo.mappers.JuiceMapper;
import com.mezo.spring6reactivemongo.model.JuiceDTO;
import com.mezo.spring6reactivemongo.repository.JuiceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class JuiceServiceImpl implements JuiceService {

    private final JuiceRepo repo;

    private final JuiceMapper mapper;

    @Override
    public Flux<JuiceDTO> getAllObjects() {
        return repo.findAll().map(mapper::objToDto);
    }

    @Override
    public Mono<JuiceDTO> getObjectById(String objectId) {
        return repo.findById(objectId).map(mapper::objToDto);
    }

    @Override
    public Mono<JuiceDTO> saveObject(JuiceDTO object) {
        return repo.save(mapper.dtoToObj(object))
                        .map(mapper::objToDto);
    }

    @Override
    public Mono<JuiceDTO> updateObject(JuiceDTO object) {
        return repo.save(mapper.dtoToObj(object)).map(mapper::objToDto);
    }

    @Override
    public Mono<Void> deleteObjectById(String objectId) {
        return repo.deleteById(objectId);
    }

    @Override
    public Mono<JuiceDTO> getObjectByName(String juiceName) {
        return repo.findJuiceByName(juiceName).map(mapper::objToDto);
    }

    @Override
    public Flux<JuiceDTO> getAllObjectByStyle(String juiceStyle) {
        return repo.findAllByStyle(juiceStyle).map(mapper::objToDto);
    }
}
