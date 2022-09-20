package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class CantineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CantineDTO.class);
        CantineDTO cantineDTO1 = new CantineDTO();
        cantineDTO1.setId(1L);
        CantineDTO cantineDTO2 = new CantineDTO();
        assertThat(cantineDTO1).isNotEqualTo(cantineDTO2);
        cantineDTO2.setId(cantineDTO1.getId());
        assertThat(cantineDTO1).isEqualTo(cantineDTO2);
        cantineDTO2.setId(2L);
        assertThat(cantineDTO1).isNotEqualTo(cantineDTO2);
        cantineDTO1.setId(null);
        assertThat(cantineDTO1).isNotEqualTo(cantineDTO2);
    }
}
