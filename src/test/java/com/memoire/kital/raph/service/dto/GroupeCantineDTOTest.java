package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class GroupeCantineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupeCantineDTO.class);
        GroupeCantineDTO groupeCantineDTO1 = new GroupeCantineDTO();
        groupeCantineDTO1.setId(1L);
        GroupeCantineDTO groupeCantineDTO2 = new GroupeCantineDTO();
        assertThat(groupeCantineDTO1).isNotEqualTo(groupeCantineDTO2);
        groupeCantineDTO2.setId(groupeCantineDTO1.getId());
        assertThat(groupeCantineDTO1).isEqualTo(groupeCantineDTO2);
        groupeCantineDTO2.setId(2L);
        assertThat(groupeCantineDTO1).isNotEqualTo(groupeCantineDTO2);
        groupeCantineDTO1.setId(null);
        assertThat(groupeCantineDTO1).isNotEqualTo(groupeCantineDTO2);
    }
}
