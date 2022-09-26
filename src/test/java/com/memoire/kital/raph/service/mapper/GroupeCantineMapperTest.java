package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupeCantineMapperTest {

    private GroupeCantineMapper groupeCantineMapper;

    @BeforeEach
    public void setUp() {
        groupeCantineMapper = new GroupeCantineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = null;
        assertThat(groupeCantineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(groupeCantineMapper.fromId(null)).isNull();
    }
}
