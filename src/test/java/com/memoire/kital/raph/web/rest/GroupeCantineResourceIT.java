package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.CantineApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.GroupeCantine;
import com.memoire.kital.raph.domain.Cantine;
import com.memoire.kital.raph.repository.GroupeCantineRepository;
import com.memoire.kital.raph.service.GroupeCantineService;
import com.memoire.kital.raph.service.dto.GroupeCantineDTO;
import com.memoire.kital.raph.service.mapper.GroupeCantineMapper;
import com.memoire.kital.raph.service.dto.GroupeCantineCriteria;
import com.memoire.kital.raph.service.GroupeCantineQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GroupeCantineResource} REST controller.
 */
@SpringBootTest(classes = { CantineApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class GroupeCantineResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_ELEVES = 1;
    private static final Integer UPDATED_NOMBRE_ELEVES = 2;
    private static final Integer SMALLER_NOMBRE_ELEVES = 1 - 1;

    @Autowired
    private GroupeCantineRepository groupeCantineRepository;

    @Autowired
    private GroupeCantineMapper groupeCantineMapper;

    @Autowired
    private GroupeCantineService groupeCantineService;

    @Autowired
    private GroupeCantineQueryService groupeCantineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupeCantineMockMvc;

    private GroupeCantine groupeCantine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupeCantine createEntity(EntityManager em) {
        GroupeCantine groupeCantine = new GroupeCantine()
            .nom(DEFAULT_NOM)
            .nombreEleves(DEFAULT_NOMBRE_ELEVES);
        return groupeCantine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupeCantine createUpdatedEntity(EntityManager em) {
        GroupeCantine groupeCantine = new GroupeCantine()
            .nom(UPDATED_NOM)
            .nombreEleves(UPDATED_NOMBRE_ELEVES);
        return groupeCantine;
    }

    @BeforeEach
    public void initTest() {
        groupeCantine = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupeCantine() throws Exception {
        int databaseSizeBeforeCreate = groupeCantineRepository.findAll().size();
        // Create the GroupeCantine
        GroupeCantineDTO groupeCantineDTO = groupeCantineMapper.toDto(groupeCantine);
        restGroupeCantineMockMvc.perform(post("/api/groupe-cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeCantineDTO)))
            .andExpect(status().isCreated());

        // Validate the GroupeCantine in the database
        List<GroupeCantine> groupeCantineList = groupeCantineRepository.findAll();
        assertThat(groupeCantineList).hasSize(databaseSizeBeforeCreate + 1);
        GroupeCantine testGroupeCantine = groupeCantineList.get(groupeCantineList.size() - 1);
        assertThat(testGroupeCantine.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testGroupeCantine.getNombreEleves()).isEqualTo(DEFAULT_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void createGroupeCantineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupeCantineRepository.findAll().size();

        // Create the GroupeCantine with an existing ID
        groupeCantine.setId(1L);
        GroupeCantineDTO groupeCantineDTO = groupeCantineMapper.toDto(groupeCantine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupeCantineMockMvc.perform(post("/api/groupe-cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeCantineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupeCantine in the database
        List<GroupeCantine> groupeCantineList = groupeCantineRepository.findAll();
        assertThat(groupeCantineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupeCantineRepository.findAll().size();
        // set the field null
        groupeCantine.setNom(null);

        // Create the GroupeCantine, which fails.
        GroupeCantineDTO groupeCantineDTO = groupeCantineMapper.toDto(groupeCantine);


        restGroupeCantineMockMvc.perform(post("/api/groupe-cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeCantineDTO)))
            .andExpect(status().isBadRequest());

        List<GroupeCantine> groupeCantineList = groupeCantineRepository.findAll();
        assertThat(groupeCantineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreElevesIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupeCantineRepository.findAll().size();
        // set the field null
        groupeCantine.setNombreEleves(null);

        // Create the GroupeCantine, which fails.
        GroupeCantineDTO groupeCantineDTO = groupeCantineMapper.toDto(groupeCantine);


        restGroupeCantineMockMvc.perform(post("/api/groupe-cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeCantineDTO)))
            .andExpect(status().isBadRequest());

        List<GroupeCantine> groupeCantineList = groupeCantineRepository.findAll();
        assertThat(groupeCantineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupeCantines() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList
        restGroupeCantineMockMvc.perform(get("/api/groupe-cantines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupeCantine.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nombreEleves").value(hasItem(DEFAULT_NOMBRE_ELEVES)));
    }
    
    @Test
    @Transactional
    public void getGroupeCantine() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get the groupeCantine
        restGroupeCantineMockMvc.perform(get("/api/groupe-cantines/{id}", groupeCantine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupeCantine.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nombreEleves").value(DEFAULT_NOMBRE_ELEVES));
    }


    @Test
    @Transactional
    public void getGroupeCantinesByIdFiltering() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        Long id = groupeCantine.getId();

        defaultGroupeCantineShouldBeFound("id.equals=" + id);
        defaultGroupeCantineShouldNotBeFound("id.notEquals=" + id);

        defaultGroupeCantineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGroupeCantineShouldNotBeFound("id.greaterThan=" + id);

        defaultGroupeCantineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGroupeCantineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGroupeCantinesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nom equals to DEFAULT_NOM
        defaultGroupeCantineShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the groupeCantineList where nom equals to UPDATED_NOM
        defaultGroupeCantineShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nom not equals to DEFAULT_NOM
        defaultGroupeCantineShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the groupeCantineList where nom not equals to UPDATED_NOM
        defaultGroupeCantineShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultGroupeCantineShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the groupeCantineList where nom equals to UPDATED_NOM
        defaultGroupeCantineShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nom is not null
        defaultGroupeCantineShouldBeFound("nom.specified=true");

        // Get all the groupeCantineList where nom is null
        defaultGroupeCantineShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllGroupeCantinesByNomContainsSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nom contains DEFAULT_NOM
        defaultGroupeCantineShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the groupeCantineList where nom contains UPDATED_NOM
        defaultGroupeCantineShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nom does not contain DEFAULT_NOM
        defaultGroupeCantineShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the groupeCantineList where nom does not contain UPDATED_NOM
        defaultGroupeCantineShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllGroupeCantinesByNombreElevesIsEqualToSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nombreEleves equals to DEFAULT_NOMBRE_ELEVES
        defaultGroupeCantineShouldBeFound("nombreEleves.equals=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeCantineList where nombreEleves equals to UPDATED_NOMBRE_ELEVES
        defaultGroupeCantineShouldNotBeFound("nombreEleves.equals=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNombreElevesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nombreEleves not equals to DEFAULT_NOMBRE_ELEVES
        defaultGroupeCantineShouldNotBeFound("nombreEleves.notEquals=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeCantineList where nombreEleves not equals to UPDATED_NOMBRE_ELEVES
        defaultGroupeCantineShouldBeFound("nombreEleves.notEquals=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNombreElevesIsInShouldWork() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nombreEleves in DEFAULT_NOMBRE_ELEVES or UPDATED_NOMBRE_ELEVES
        defaultGroupeCantineShouldBeFound("nombreEleves.in=" + DEFAULT_NOMBRE_ELEVES + "," + UPDATED_NOMBRE_ELEVES);

        // Get all the groupeCantineList where nombreEleves equals to UPDATED_NOMBRE_ELEVES
        defaultGroupeCantineShouldNotBeFound("nombreEleves.in=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNombreElevesIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nombreEleves is not null
        defaultGroupeCantineShouldBeFound("nombreEleves.specified=true");

        // Get all the groupeCantineList where nombreEleves is null
        defaultGroupeCantineShouldNotBeFound("nombreEleves.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNombreElevesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nombreEleves is greater than or equal to DEFAULT_NOMBRE_ELEVES
        defaultGroupeCantineShouldBeFound("nombreEleves.greaterThanOrEqual=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeCantineList where nombreEleves is greater than or equal to UPDATED_NOMBRE_ELEVES
        defaultGroupeCantineShouldNotBeFound("nombreEleves.greaterThanOrEqual=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNombreElevesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nombreEleves is less than or equal to DEFAULT_NOMBRE_ELEVES
        defaultGroupeCantineShouldBeFound("nombreEleves.lessThanOrEqual=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeCantineList where nombreEleves is less than or equal to SMALLER_NOMBRE_ELEVES
        defaultGroupeCantineShouldNotBeFound("nombreEleves.lessThanOrEqual=" + SMALLER_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNombreElevesIsLessThanSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nombreEleves is less than DEFAULT_NOMBRE_ELEVES
        defaultGroupeCantineShouldNotBeFound("nombreEleves.lessThan=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeCantineList where nombreEleves is less than UPDATED_NOMBRE_ELEVES
        defaultGroupeCantineShouldBeFound("nombreEleves.lessThan=" + UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void getAllGroupeCantinesByNombreElevesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        // Get all the groupeCantineList where nombreEleves is greater than DEFAULT_NOMBRE_ELEVES
        defaultGroupeCantineShouldNotBeFound("nombreEleves.greaterThan=" + DEFAULT_NOMBRE_ELEVES);

        // Get all the groupeCantineList where nombreEleves is greater than SMALLER_NOMBRE_ELEVES
        defaultGroupeCantineShouldBeFound("nombreEleves.greaterThan=" + SMALLER_NOMBRE_ELEVES);
    }


    @Test
    @Transactional
    public void getAllGroupeCantinesByCantineIsEqualToSomething() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);
        Cantine cantine = CantineResourceIT.createEntity(em);
        em.persist(cantine);
        em.flush();
        groupeCantine.setCantine(cantine);
        groupeCantineRepository.saveAndFlush(groupeCantine);
        Long cantineId = cantine.getId();

        // Get all the groupeCantineList where cantine equals to cantineId
        defaultGroupeCantineShouldBeFound("cantineId.equals=" + cantineId);

        // Get all the groupeCantineList where cantine equals to cantineId + 1
        defaultGroupeCantineShouldNotBeFound("cantineId.equals=" + (cantineId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGroupeCantineShouldBeFound(String filter) throws Exception {
        restGroupeCantineMockMvc.perform(get("/api/groupe-cantines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupeCantine.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nombreEleves").value(hasItem(DEFAULT_NOMBRE_ELEVES)));

        // Check, that the count call also returns 1
        restGroupeCantineMockMvc.perform(get("/api/groupe-cantines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGroupeCantineShouldNotBeFound(String filter) throws Exception {
        restGroupeCantineMockMvc.perform(get("/api/groupe-cantines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGroupeCantineMockMvc.perform(get("/api/groupe-cantines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGroupeCantine() throws Exception {
        // Get the groupeCantine
        restGroupeCantineMockMvc.perform(get("/api/groupe-cantines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupeCantine() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        int databaseSizeBeforeUpdate = groupeCantineRepository.findAll().size();

        // Update the groupeCantine
        GroupeCantine updatedGroupeCantine = groupeCantineRepository.findById(groupeCantine.getId()).get();
        // Disconnect from session so that the updates on updatedGroupeCantine are not directly saved in db
        em.detach(updatedGroupeCantine);
        updatedGroupeCantine
            .nom(UPDATED_NOM)
            .nombreEleves(UPDATED_NOMBRE_ELEVES);
        GroupeCantineDTO groupeCantineDTO = groupeCantineMapper.toDto(updatedGroupeCantine);

        restGroupeCantineMockMvc.perform(put("/api/groupe-cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeCantineDTO)))
            .andExpect(status().isOk());

        // Validate the GroupeCantine in the database
        List<GroupeCantine> groupeCantineList = groupeCantineRepository.findAll();
        assertThat(groupeCantineList).hasSize(databaseSizeBeforeUpdate);
        GroupeCantine testGroupeCantine = groupeCantineList.get(groupeCantineList.size() - 1);
        assertThat(testGroupeCantine.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testGroupeCantine.getNombreEleves()).isEqualTo(UPDATED_NOMBRE_ELEVES);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupeCantine() throws Exception {
        int databaseSizeBeforeUpdate = groupeCantineRepository.findAll().size();

        // Create the GroupeCantine
        GroupeCantineDTO groupeCantineDTO = groupeCantineMapper.toDto(groupeCantine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupeCantineMockMvc.perform(put("/api/groupe-cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeCantineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GroupeCantine in the database
        List<GroupeCantine> groupeCantineList = groupeCantineRepository.findAll();
        assertThat(groupeCantineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupeCantine() throws Exception {
        // Initialize the database
        groupeCantineRepository.saveAndFlush(groupeCantine);

        int databaseSizeBeforeDelete = groupeCantineRepository.findAll().size();

        // Delete the groupeCantine
        restGroupeCantineMockMvc.perform(delete("/api/groupe-cantines/{id}", groupeCantine.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupeCantine> groupeCantineList = groupeCantineRepository.findAll();
        assertThat(groupeCantineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
