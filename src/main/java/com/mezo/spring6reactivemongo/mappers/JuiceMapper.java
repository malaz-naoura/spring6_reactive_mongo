package com.mezo.spring6reactivemongo.mappers;

import com.mezo.spring6reactivemongo.domain.Juice;
import com.mezo.spring6reactivemongo.model.JuiceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface JuiceMapper extends MapperMain<Juice, JuiceDTO> {


}
