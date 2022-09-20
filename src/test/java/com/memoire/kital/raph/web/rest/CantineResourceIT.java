package com.memoire.kital.raph.web.rest;

import com.memoire.kital.raph.CantineApp;
import com.memoire.kital.raph.config.TestSecurityConfiguration;
import com.memoire.kital.raph.domain.Cantine;
import com.memoire.kital.raph.domain.GroupeCantine;
import com.memoire.kital.raph.repository.CantineRepository;
import com.memoire.kital.raph.service.CantineService;
import com.memoire.kital.raph.service.dto.CantineDTO;
import com.memoire.kital.raph.service.mapper.CantineMapper;
import com.memoire.kital.raph.service.dto.CantineCriteria;
import com.memoire.kital.raph.service.CantineQueryService;

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
 * Integration tests for the {@link CantineResource} REST controller.
 */
@SpringBootTest(classes = { CantineApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CantineResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOMBRE_GROUPE = 1;
    private static final Integer UPDATED_NOMBRE_GROUPE = 2;
    private static final Integer SMALLER_NOMBRE_GROUPE = 1 - 1;

    @Autowired
    private CantineRepository cantineRepository;

    @Autowired
    private CantineMapper cantineMapper;

    @Autowired
    private CantineService cantineService;

    @Autowired
    private CantineQueryService cantineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCantineMockMvc;

    private Cantine cantine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cantine createEntity(EntityManager em) {
        Cantine cantine = new Cantine()
            .libelle(DEFAULT_LIBELLE)
            .nombreGroupe(DEFAULT_NOMBRE_GROUPE);
        return cantine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cantine createUpdatedEntity(EntityManager em) {
        Cantine cantine = new Cantine()
            .libelle(UPDATED_LIBELLE)
            .nombreGroupe(UPDATED_NOMBRE_GROUPE);
        return cantine;
    }

    @BeforeEach
    public void initTest() {
        cantine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCantine() throws Exception {
        int databaseSizeBeforeCreate = cantineRepository.findAll().size();
        // Create the Cantine
        CantineDTO cantineDTO = cantineMapper.toDto(cantine);
        restCantineMockMvc.perform(post("/api/cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cantineDTO)))
            .andExpect(status().isCreated());

        // Validate the Cantine in the database
        List<Cantine> cantineList = cantineRepository.findAll();
        assertThat(cantineList).hasSize(databaseSizeBeforeCreate + 1);
        Cantine testCantine = cantineList.get(cantineList.size() - 1);
        assertThat(testCantine.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testCantine.getNombreGroupe()).isEqualTo(DEFAULT_NOMBRE_GROUPE);
    }

    @Test
    @Transactional
    public void createCantineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cantineRepository.findAll().size();

        // Create the Cantine with an existing ID
        cantine.setId(1L);
        CantineDTO cantineDTO = cantineMapper.toDto(cantine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCantineMockMvc.perform(post("/api/cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cantineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cantine in the database
        List<Cantine> cantineList = cantineRepository.findAll();
        assertThat(cantineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = cantineRepository.findAll().size();
        // set the field null
        cantine.setLibelle(null);

        // Create the Cantine, which fails.
        CantineDTO cantineDTO = cantineMapper.toDto(cantine);


        restCantineMockMvc.perform(post("/api/cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cantineDTO)))
            .andExpect(status().isBadRequest());

        List<Cantine> cantineList = cantineRepository.findAll();
        assertThat(cantineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreGroupeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cantineRepository.findAll().size();
        // set the field null
        cantine.setNombreGroupe(null);

        // Create the Cantine, which fails.
        CantineDTO cantineDTO = cantineMapper.toDto(cantine);


        restCantineMockMvc.perform(post("/api/cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cantineDTO)))
            .andExpect(status().isBadRequest());

        List<Cantine> cantineList = cantineRepository.findAll();
        assertThat(cantineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCantines() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList
        restCantineMockMvc.perform(get("/api/cantines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cantine.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].nombreGroupe").value(hasItem(DEFAULT_NOMBRE_GROUPE)));
    }
    
    @Test
    @Transactional
    public void getCantine() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get the cantine
        restCantineMockMvc.perform(get("/api/cantines/{id}", cantine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cantine.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.nombreGroupe").value(DEFAULT_NOMBRE_GROUPE));
    }


    @Test
    @Transactional
    public void getCantinesByIdFiltering() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        Long id = cantine.getId();

        defaultCantineShouldBeFound("id.equals=" + id);
        defaultCantineShouldNotBeFound("id.notEquals=" + id);

        defaultCantineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCantineShouldNotBeFound("id.greaterThan=" + id);

        defaultCantineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCantineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCantinesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where libelle equals to DEFAULT_LIBELLE
        defaultCantineShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the cantineList where libelle equals to UPDATED_LIBELLE
        defaultCantineShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllCantinesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where libelle not equals to DEFAULT_LIBELLE
        defaultCantineShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the cantineList where libelle not equals to UPDATED_LIBELLE
        defaultCantineShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllCantinesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultCantineShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the cantineList where libelle equals to UPDATED_LIBELLE
        defaultCantineShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllCantinesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where libelle is not null
        defaultCantineShouldBeFound("libelle.specified=true");

        // Get all the cantineList where libelle is null
        defaultCantineShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllCantinesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where libelle contains DEFAULT_LIBELLE
        defaultCantineShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the cantineList where libelle contains UPDATED_LIBELLE
        defaultCantineShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllCantinesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where libelle does not contain DEFAULT_LIBELLE
        defaultCantineShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the cantineList where libelle does not contain UPDATED_LIBELLE
        defaultCantineShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }


    @Test
    @Transactional
    public void getAllCantinesByNombreGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where nombreGroupe equals to DEFAULT_NOMBRE_GROUPE
        defaultCantineShouldBeFound("nombreGroupe.equals=" + DEFAULT_NOMBRE_GROUPE);

        // Get all the cantineList where nombreGroupe equals to UPDATED_NOMBRE_GROUPE
        defaultCantineShouldNotBeFound("nombreGroupe.equals=" + UPDATED_NOMBRE_GROUPE);
    }

    @Test
    @Transactional
    public void getAllCantinesByNombreGroupeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where nombreGroupe not equals to DEFAULT_NOMBRE_GROUPE
        defaultCantineShouldNotBeFound("nombreGroupe.notEquals=" + DEFAULT_NOMBRE_GROUPE);

        // Get all the cantineList where nombreGroupe not equals to UPDATED_NOMBRE_GROUPE
        defaultCantineShouldBeFound("nombreGroupe.notEquals=" + UPDATED_NOMBRE_GROUPE);
    }

    @Test
    @Transactional
    public void getAllCantinesByNombreGroupeIsInShouldWork() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where nombreGroupe in DEFAULT_NOMBRE_GROUPE or UPDATED_NOMBRE_GROUPE
        defaultCantineShouldBeFound("nombreGroupe.in=" + DEFAULT_NOMBRE_GROUPE + "," + UPDATED_NOMBRE_GROUPE);

        // Get all the cantineList where nombreGroupe equals to UPDATED_NOMBRE_GROUPE
        defaultCantineShouldNotBeFound("nombreGroupe.in=" + UPDATED_NOMBRE_GROUPE);
    }

    @Test
    @Transactional
    public void getAllCantinesByNombreGroupeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where nombreGroupe is not null
        defaultCantineShouldBeFound("nombreGroupe.specified=true");

        // Get all the cantineList where nombreGroupe is null
        defaultCantineShouldNotBeFound("nombreGroupe.specified=false");
    }

    @Test
    @Transactional
    public void getAllCantinesByNombreGroupeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where nombreGroupe is greater than or equal to DEFAULT_NOMBRE_GROUPE
        defaultCantineShouldBeFound("nombreGroupe.greaterThanOrEqual=" + DEFAULT_NOMBRE_GROUPE);

        // Get all the cantineList where nombreGroupe is greater than or equal to UPDATED_NOMBRE_GROUPE
        defaultCantineShouldNotBeFound("nombreGroupe.greaterThanOrEqual=" + UPDATED_NOMBRE_GROUPE);
    }

    @Test
    @Transactional
    public void getAllCantinesByNombreGroupeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where nombreGroupe is less than or equal to DEFAULT_NOMBRE_GROUPE
        defaultCantineShouldBeFound("nombreGroupe.lessThanOrEqual=" + DEFAULT_NOMBRE_GROUPE);

        // Get all the cantineList where nombreGroupe is less than or equal to SMALLER_NOMBRE_GROUPE
        defaultCantineShouldNotBeFound("nombreGroupe.lessThanOrEqual=" + SMALLER_NOMBRE_GROUPE);
    }

    @Test
    @Transactional
    public void getAllCantinesByNombreGroupeIsLessThanSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where nombreGroupe is less than DEFAULT_NOMBRE_GROUPE
        defaultCantineShouldNotBeFound("nombreGroupe.lessThan=" + DEFAULT_NOMBRE_GROUPE);

        // Get all the cantineList where nombreGroupe is less than UPDATED_NOMBRE_GROUPE
        defaultCantineShouldBeFound("nombreGroupe.lessThan=" + UPDATED_NOMBRE_GROUPE);
    }

    @Test
    @Transactional
    public void getAllCantinesByNombreGroupeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        // Get all the cantineList where nombreGroupe is greater than DEFAULT_NOMBRE_GROUPE
        defaultCantineShouldNotBeFound("nombreGroupe.greaterThan=" + DEFAULT_NOMBRE_GROUPE);

        // Get all the cantineList where nombreGroupe is greater than SMALLER_NOMBRE_GROUPE
        defaultCantineShouldBeFound("nombreGroupe.greaterThan=" + SMALLER_NOMBRE_GROUPE);
    }


    @Test
    @Transactional
    public void getAllCantinesByGroupecantinesIsEqualToSomething() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);
        GroupeCantine groupecantines = GroupeCantineResourceIT.createEntity(em);
        em.persist(groupecantines);
        em.flush();
        cantine.addGroupecantines(groupecantines);
        cantineRepository.saveAndFlush(cantine);
        Long groupecantinesId = groupecantines.getId();

        // Get all the cantineList where groupecantines equals to groupecantinesId
        defaultCantineShouldBeFound("groupecantinesId.equals=" + groupecantinesId);

        // Get all the cantineList where groupecantines equals to groupecantinesId + 1
        defaultCantineShouldNotBeFound("groupecantinesId.equals=" + (groupecantinesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCantineShouldBeFound(String filter) throws Exception {
        restCantineMockMvc.perform(get("/api/cantines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cantine.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].nombreGroupe").value(hasItem(DEFAULT_NOMBRE_GROUPE)));

        // Check, that the count call also returns 1
        restCantineMockMvc.perform(get("/api/cantines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCantineShouldNotBeFound(String filter) throws Exception {
        restCantineMockMvc.perform(get("/api/cantines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCantineMockMvc.perform(get("/api/cantines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCantine() throws Exception {
        // Get the cantine
        restCantineMockMvc.perform(get("/api/cantines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCantine() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        int databaseSizeBeforeUpdate = cantineRepository.findAll().size();

        // Update the cantine
        Cantine updatedCantine = cantineRepository.findById(cantine.getId()).get();
        // Disconnect from session so that the updates on updatedCantine are not directly saved in db
        em.detach(updatedCantine);
        updatedCantine
            .libelle(UPDATED_LIBELLE)
            .nombreGroupe(UPDATED_NOMBRE_GROUPE);
        CantineDTO cantineDTO = cantineMapper.toDto(updatedCantine);

        restCantineMockMvc.perform(put("/api/cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cantineDTO)))
            .andExpect(status().isOk());

        // Validate the Cantine in the database
        List<Cantine> cantineList = cantineRepository.findAll();
        assertThat(cantineList).hasSize(databaseSizeBeforeUpdate);
        Cantine testCantine = cantineList.get(cantineList.size() - 1);
        assertThat(testCantine.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testCantine.getNombreGroupe()).isEqualTo(UPDATED_NOMBRE_GROUPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCantine() throws Exception {
        int databaseSizeBeforeUpdate = cantineRepository.findAll().size();

        // Create the Cantine
        CantineDTO cantineDTO = cantineMapper.toDto(cantine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCantineMockMvc.perform(put("/api/cantines").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cantineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cantine in the database
        List<Cantine> cantineList = cantineRepository.findAll();
        assertThat(cantineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCantine() throws Exception {
        // Initialize the database
        cantineRepository.saveAndFlush(cantine);

        int databaseSizeBeforeDelete = cantineRepository.findAll().size();

        // Delete the cantine
        restCantineMockMvc.perform(delete("/api/cantines/{id}", cantine.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cantine> cantineList = cantineRepository.findAll();
        assertThat(cantineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
