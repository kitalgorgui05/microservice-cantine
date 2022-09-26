package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class CantineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cantine.class);
        Cantine cantine1 = new Cantine();
        cantine1.setId(null);
        Cantine cantine2 = new Cantine();
        cantine2.setId(cantine1.getId());
        assertThat(cantine1).isEqualTo(cantine2);
        cantine2.setId(null);
        assertThat(cantine1).isNotEqualTo(cantine2);
        cantine1.setId(null);
        assertThat(cantine1).isNotEqualTo(cantine2);
    }
}
