package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.GroupeCantineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupeCantine} and its DTO {@link GroupeCantineDTO}.
 */
@Mapper(componentModel = "spring", uses = {CantineMapper.class})
public interface GroupeCantineMapper extends EntityMapper<GroupeCantineDTO, GroupeCantine> {

    @Mapping(source = "cantine.id", target = "cantineId")
    GroupeCantineDTO toDto(GroupeCantine groupeCantine);

    @Mapping(source = "cantineId", target = "cantine")
    GroupeCantine toEntity(GroupeCantineDTO groupeCantineDTO);

    default GroupeCantine fromId(String id) {
        if (id == null) {
            return null;
        }
        GroupeCantine groupeCantine = new GroupeCantine();
        groupeCantine.setId(id);
        return groupeCantine;
    }
}
