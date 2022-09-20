package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CantineMapperTest {

    private CantineMapper cantineMapper;

    @BeforeEach
    public void setUp() {
        cantineMapper = new CantineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(cantineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(cantineMapper.fromId(null)).isNull();
    }
}
