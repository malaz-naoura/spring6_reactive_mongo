package com.mezo.spring6reactivemongo.services;

import com.mezo.spring6reactivemongo.model.JuiceDTO;
import com.mezo.spring6reactivemongo.services.mezo.RootServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
class JuiceServiceImplTest implements RootServiceTest<JuiceDTO> {

    JuiceDTO dumpObj = JuiceDTO.builder()
                               .name("jucietest1")
                               .price(BigDecimal.ONE)
                               .upc("upcccs")
                               .style("style1")
                               .build();


    @Autowired
    JuiceService service;

    @BeforeEach
    void setUp() {
        AtomicBoolean finished = new AtomicBoolean(false);

        service.getAllObjects()
               .flatMap(juiceDTO -> service.deleteObjectById(juiceDTO.getId())
                                           .thenReturn(juiceDTO))
               .doOnComplete(() -> finished.set(true))
               .subscribe();

        await().untilTrue(finished);

        AtomicBoolean finished1 = new AtomicBoolean(false);
        AtomicBoolean finished2 = new AtomicBoolean(false);
        AtomicBoolean finished3 = new AtomicBoolean(false);

        service.saveObject(dumpObj.toBuilder()
                                  .name("jucietest1")
                                  .style("style")
                                  .build())
               .subscribe((juiceDTO -> finished1.set(true)));

        service.saveObject(dumpObj.toBuilder()
                                  .name("jucietest2")
                                  .style("style")
                                  .build())
               .subscribe((juiceDTO -> finished2.set(true)));

        service.saveObject(dumpObj.toBuilder()
                                  .name("jucietest3")
                                  .style("style")
                                  .build())
               .subscribe((juiceDTO -> finished3.set(true)));


        await().untilTrue(finished1);
        await().untilTrue(finished2);
        await().untilTrue(finished3);

    }


    @Override
    public JuiceDTO getSavedObject() {
        AtomicBoolean finished = new AtomicBoolean();
        AtomicReference<JuiceDTO> juiceDTOAtomicReference = new AtomicReference<>();

        service.getAllObjects()
               .collectList()
               .subscribe(list -> {
                   juiceDTOAtomicReference.set(list.get(0));
                   finished.set(true);
               });

        await().untilTrue(finished);

        return juiceDTOAtomicReference.get();
    }

    @Test
    @Order(10)
    @Override
    public void listObjects() {
        AtomicBoolean finished = new AtomicBoolean(false);
        AtomicInteger listSize = new AtomicInteger();

        service.getAllObjects()
               .collectList()
               .subscribe(list -> {
                   listSize.set(list.size());
                   finished.set(true);
               });

        await().untilTrue(finished);

        assertThat(listSize.get()).isNotEqualTo(0);
    }

    @Test
    @Order(10)
    @Override
    public void getObjectById() {
        AtomicBoolean finished = new AtomicBoolean();
        AtomicReference<JuiceDTO> randomJuiceDTOAtomicReference = new AtomicReference<>();
        service.getAllObjects()
               .collectList()
               .subscribe(list -> {
                   randomJuiceDTOAtomicReference.set(list.get(0));
                   finished.set(true);
               });


        await().untilTrue(finished);
        finished.set(true);

        AtomicReference<JuiceDTO> juiceDTOAtomicReference = new AtomicReference<>();

        service.getObjectById(randomJuiceDTOAtomicReference.get()
                                                           .getId())
               .subscribe(juiceDTO -> {
                   juiceDTOAtomicReference.set(juiceDTO);
                   finished.set(true);
               });

        await().untilTrue(finished);

        assertThat(juiceDTOAtomicReference.get()).isEqualTo(randomJuiceDTOAtomicReference.get());
    }

    @Test
    @Order(30)
    @Override
    public void saveObject() {
        AtomicBoolean finished = new AtomicBoolean(false);
        AtomicReference<JuiceDTO> juiceDTOAtomicReference = new AtomicReference<>();

        Mono<JuiceDTO> juiceDTOMono = service.saveObject(dumpObj);

        juiceDTOMono.subscribe(savedJuiceDto -> {
            juiceDTOAtomicReference.set(savedJuiceDto);
            finished.set(true);
        });

        await().untilTrue(finished);
        assertThat(juiceDTOAtomicReference.get()
                                          .getId()).isNotBlank();
    }

    @Test
    @Order(30)
    @Override
    public void updateObject() {
        AtomicBoolean finished = new AtomicBoolean();

        JuiceDTO savedObject = getSavedObject();

        String newJuiceName = "updatedByJuiceTester";
        savedObject.setName(newJuiceName);

        service.saveObject(savedObject)
               .subscribe(juiceDTO -> finished.set(true));

        await().untilTrue(finished);
        finished.set(false);

        AtomicReference<String> updatedName = new AtomicReference<>();

        service.getObjectById(savedObject.getId())
               .subscribe(juiceDTO -> {
                   updatedName.set(juiceDTO.getName());
                   finished.set(true);
               });

        await().untilTrue(finished);

        assertThat(newJuiceName).isEqualTo(updatedName.get());
    }

    @Test
    @Order(40)
    @Override
    public void deleteObjectById() {
        AtomicBoolean finished = new AtomicBoolean();

        JuiceDTO savedObject = getSavedObject();

        service.deleteObjectById(savedObject.getId())
               .doOnSuccess(unused -> finished.set(true))
               .subscribe();

        await().untilTrue(finished);
        finished.set(false);

        AtomicReference<JuiceDTO> juiceDTOAtomicReference = new AtomicReference<>();
        service.getObjectById(savedObject.getId())
               .doOnSuccess(juiceDTO -> {
                   juiceDTOAtomicReference.set(juiceDTO);
                   finished.set(true);
               })
               .subscribe();

        await().untilTrue(finished);

        assertThat(juiceDTOAtomicReference.get()).isNull();
    }

    @Test
    @Order(10)
    public void getObjectByName() {
        String requiredJuiceName = "jucietest1";
        AtomicReference<String> juiceName = new AtomicReference<>();
        AtomicBoolean finished = new AtomicBoolean();

        service.getObjectByName(requiredJuiceName)
               .subscribe(juiceDTO -> {
                   juiceName.set(juiceDTO.getName());
                   finished.set(true);
               });

        await().untilTrue(finished);
        assertThat(requiredJuiceName).isEqualTo(juiceName.get());
    }

    @Test
    @Order(10)
    public void getAllObjectByStyle() {

        String requiredStyle = "style";

        AtomicBoolean finished = new AtomicBoolean(false);
        AtomicReference<List<JuiceDTO>> listJuiceDto = new AtomicReference<>();

        service.getAllObjects()
               .collectList()
               .subscribe(list -> {
                   listJuiceDto.set(list);
                   finished.set(true);
               });

        await().untilTrue(finished);
        finished.set(false);

        Integer expectedSizeOfList = listJuiceDto.get()
                                                 .stream()
                                                 .filter(juiceDTO -> juiceDTO.getStyle()
                                                                             .equals(requiredStyle))
                                                 .toList()
                                                 .size();

        AtomicInteger acutalSizeOfList = new AtomicInteger();

        service.getAllObjectByStyle(requiredStyle)
               .collectList()
               .subscribe(list -> {
                   acutalSizeOfList.set(list.size());
                   finished.set(true);
               });

        await().untilTrue(finished);
        assertThat(acutalSizeOfList.get()).isEqualTo(expectedSizeOfList);
    }
}