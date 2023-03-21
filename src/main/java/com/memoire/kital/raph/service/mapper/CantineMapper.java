package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.CantineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cantine} and its DTO {@link CantineDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CantineMapper extends EntityMapper<CantineDTO, Cantine> {


    @Mapping(target = "groupecantines", ignore = true)
    //@Mapping(target = "removeGroupecantines", ignore = true)
    Cantine toEntity(CantineDTO cantineDTO);

    default Cantine fromId(String id) {
        if (id == null) {
            return null;
        }
        Cantine cantine = new Cantine();
        cantine.setId(id);
        return cantine;
    }
}
