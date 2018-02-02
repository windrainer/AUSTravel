package com.nighteagle.web.rest;

import com.nighteagle.AusTravelApp;

import com.nighteagle.domain.Cnbanner;
import com.nighteagle.repository.CnbannerRepository;
import com.nighteagle.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CnbannerResource REST controller.
 *
 * @see CnbannerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AusTravelApp.class)
public class CnbannerResourceIntTest {

    private static final String DEFAULT_BACKGROUND = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_1 = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_2 = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_URL_1 = "AAAAAAAAAA";
    private static final String UPDATED_URL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_URL_2 = "AAAAAAAAAA";
    private static final String UPDATED_URL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CSS_1 = "AAAAAAAAAA";
    private static final String UPDATED_CSS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CSS_2 = "AAAAAAAAAA";
    private static final String UPDATED_CSS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    @Autowired
    private CnbannerRepository cnbannerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCnbannerMockMvc;

    private Cnbanner cnbanner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CnbannerResource cnbannerResource = new CnbannerResource(cnbannerRepository);
        this.restCnbannerMockMvc = MockMvcBuilders.standaloneSetup(cnbannerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cnbanner createEntity(EntityManager em) {
        Cnbanner cnbanner = new Cnbanner()
            .background(DEFAULT_BACKGROUND)
            .logo(DEFAULT_LOGO)
            .text1(DEFAULT_TEXT_1)
            .text2(DEFAULT_TEXT_2)
            .url1(DEFAULT_URL_1)
            .url2(DEFAULT_URL_2)
            .css1(DEFAULT_CSS_1)
            .css2(DEFAULT_CSS_2)
            .name(DEFAULT_NAME)
            .activated(DEFAULT_ACTIVATED);
        return cnbanner;
    }

    @Before
    public void initTest() {
        cnbanner = createEntity(em);
    }

    @Test
    @Transactional
    public void createCnbanner() throws Exception {
        int databaseSizeBeforeCreate = cnbannerRepository.findAll().size();

        // Create the Cnbanner
        restCnbannerMockMvc.perform(post("/api/cnbanners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnbanner)))
            .andExpect(status().isCreated());

        // Validate the Cnbanner in the database
        List<Cnbanner> cnbannerList = cnbannerRepository.findAll();
        assertThat(cnbannerList).hasSize(databaseSizeBeforeCreate + 1);
        Cnbanner testCnbanner = cnbannerList.get(cnbannerList.size() - 1);
        assertThat(testCnbanner.getBackground()).isEqualTo(DEFAULT_BACKGROUND);
        assertThat(testCnbanner.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCnbanner.getText1()).isEqualTo(DEFAULT_TEXT_1);
        assertThat(testCnbanner.getText2()).isEqualTo(DEFAULT_TEXT_2);
        assertThat(testCnbanner.getUrl1()).isEqualTo(DEFAULT_URL_1);
        assertThat(testCnbanner.getUrl2()).isEqualTo(DEFAULT_URL_2);
        assertThat(testCnbanner.getCss1()).isEqualTo(DEFAULT_CSS_1);
        assertThat(testCnbanner.getCss2()).isEqualTo(DEFAULT_CSS_2);
        assertThat(testCnbanner.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCnbanner.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createCnbannerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cnbannerRepository.findAll().size();

        // Create the Cnbanner with an existing ID
        cnbanner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCnbannerMockMvc.perform(post("/api/cnbanners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnbanner)))
            .andExpect(status().isBadRequest());

        // Validate the Cnbanner in the database
        List<Cnbanner> cnbannerList = cnbannerRepository.findAll();
        assertThat(cnbannerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cnbannerRepository.findAll().size();
        // set the field null
        cnbanner.setName(null);

        // Create the Cnbanner, which fails.

        restCnbannerMockMvc.perform(post("/api/cnbanners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnbanner)))
            .andExpect(status().isBadRequest());

        List<Cnbanner> cnbannerList = cnbannerRepository.findAll();
        assertThat(cnbannerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCnbanners() throws Exception {
        // Initialize the database
        cnbannerRepository.saveAndFlush(cnbanner);

        // Get all the cnbannerList
        restCnbannerMockMvc.perform(get("/api/cnbanners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cnbanner.getId().intValue())))
            .andExpect(jsonPath("$.[*].background").value(hasItem(DEFAULT_BACKGROUND.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
            .andExpect(jsonPath("$.[*].text1").value(hasItem(DEFAULT_TEXT_1.toString())))
            .andExpect(jsonPath("$.[*].text2").value(hasItem(DEFAULT_TEXT_2.toString())))
            .andExpect(jsonPath("$.[*].url1").value(hasItem(DEFAULT_URL_1.toString())))
            .andExpect(jsonPath("$.[*].url2").value(hasItem(DEFAULT_URL_2.toString())))
            .andExpect(jsonPath("$.[*].css1").value(hasItem(DEFAULT_CSS_1.toString())))
            .andExpect(jsonPath("$.[*].css2").value(hasItem(DEFAULT_CSS_2.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())));
    }

    @Test
    @Transactional
    public void getCnbanner() throws Exception {
        // Initialize the database
        cnbannerRepository.saveAndFlush(cnbanner);

        // Get the cnbanner
        restCnbannerMockMvc.perform(get("/api/cnbanners/{id}", cnbanner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cnbanner.getId().intValue()))
            .andExpect(jsonPath("$.background").value(DEFAULT_BACKGROUND.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()))
            .andExpect(jsonPath("$.text1").value(DEFAULT_TEXT_1.toString()))
            .andExpect(jsonPath("$.text2").value(DEFAULT_TEXT_2.toString()))
            .andExpect(jsonPath("$.url1").value(DEFAULT_URL_1.toString()))
            .andExpect(jsonPath("$.url2").value(DEFAULT_URL_2.toString()))
            .andExpect(jsonPath("$.css1").value(DEFAULT_CSS_1.toString()))
            .andExpect(jsonPath("$.css2").value(DEFAULT_CSS_2.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCnbanner() throws Exception {
        // Get the cnbanner
        restCnbannerMockMvc.perform(get("/api/cnbanners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCnbanner() throws Exception {
        // Initialize the database
        cnbannerRepository.saveAndFlush(cnbanner);
        int databaseSizeBeforeUpdate = cnbannerRepository.findAll().size();

        // Update the cnbanner
        Cnbanner updatedCnbanner = cnbannerRepository.findOne(cnbanner.getId());
        updatedCnbanner
            .background(UPDATED_BACKGROUND)
            .logo(UPDATED_LOGO)
            .text1(UPDATED_TEXT_1)
            .text2(UPDATED_TEXT_2)
            .url1(UPDATED_URL_1)
            .url2(UPDATED_URL_2)
            .css1(UPDATED_CSS_1)
            .css2(UPDATED_CSS_2)
            .name(UPDATED_NAME)
            .activated(UPDATED_ACTIVATED);

        restCnbannerMockMvc.perform(put("/api/cnbanners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCnbanner)))
            .andExpect(status().isOk());

        // Validate the Cnbanner in the database
        List<Cnbanner> cnbannerList = cnbannerRepository.findAll();
        assertThat(cnbannerList).hasSize(databaseSizeBeforeUpdate);
        Cnbanner testCnbanner = cnbannerList.get(cnbannerList.size() - 1);
        assertThat(testCnbanner.getBackground()).isEqualTo(UPDATED_BACKGROUND);
        assertThat(testCnbanner.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCnbanner.getText1()).isEqualTo(UPDATED_TEXT_1);
        assertThat(testCnbanner.getText2()).isEqualTo(UPDATED_TEXT_2);
        assertThat(testCnbanner.getUrl1()).isEqualTo(UPDATED_URL_1);
        assertThat(testCnbanner.getUrl2()).isEqualTo(UPDATED_URL_2);
        assertThat(testCnbanner.getCss1()).isEqualTo(UPDATED_CSS_1);
        assertThat(testCnbanner.getCss2()).isEqualTo(UPDATED_CSS_2);
        assertThat(testCnbanner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCnbanner.isActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void updateNonExistingCnbanner() throws Exception {
        int databaseSizeBeforeUpdate = cnbannerRepository.findAll().size();

        // Create the Cnbanner

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCnbannerMockMvc.perform(put("/api/cnbanners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cnbanner)))
            .andExpect(status().isCreated());

        // Validate the Cnbanner in the database
        List<Cnbanner> cnbannerList = cnbannerRepository.findAll();
        assertThat(cnbannerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCnbanner() throws Exception {
        // Initialize the database
        cnbannerRepository.saveAndFlush(cnbanner);
        int databaseSizeBeforeDelete = cnbannerRepository.findAll().size();

        // Get the cnbanner
        restCnbannerMockMvc.perform(delete("/api/cnbanners/{id}", cnbanner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cnbanner> cnbannerList = cnbannerRepository.findAll();
        assertThat(cnbannerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cnbanner.class);
        Cnbanner cnbanner1 = new Cnbanner();
        cnbanner1.setId(1L);
        Cnbanner cnbanner2 = new Cnbanner();
        cnbanner2.setId(cnbanner1.getId());
        assertThat(cnbanner1).isEqualTo(cnbanner2);
        cnbanner2.setId(2L);
        assertThat(cnbanner1).isNotEqualTo(cnbanner2);
        cnbanner1.setId(null);
        assertThat(cnbanner1).isNotEqualTo(cnbanner2);
    }
}
