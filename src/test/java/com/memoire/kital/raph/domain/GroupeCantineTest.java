package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class GroupeCantineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupeCantine.class);
        GroupeCantine groupeCantine1 = new GroupeCantine();
        groupeCantine1.setId(null);
        GroupeCantine groupeCantine2 = new GroupeCantine();
        groupeCantine2.setId(groupeCantine1.getId());
        assertThat(groupeCantine1).isEqualTo(groupeCantine2);
        groupeCantine2.setId(null);
        assertThat(groupeCantine1).isNotEqualTo(groupeCantine2);
        groupeCantine1.setId(null);
        assertThat(groupeCantine1).isNotEqualTo(groupeCantine2);
    }
}
