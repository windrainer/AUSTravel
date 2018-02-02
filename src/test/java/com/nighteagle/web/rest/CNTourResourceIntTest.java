package com.nighteagle.web.rest;

import com.nighteagle.AusTravelApp;

import com.nighteagle.domain.CNTour;
import com.nighteagle.repository.CNTourRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CNTourResource REST controller.
 *
 * @see CNTourResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AusTravelApp.class)
public class CNTourResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);

    private static final String DEFAULT_IMG_URL_1 = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_URL_2 = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private CNTourRepository cNTourRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCNTourMockMvc;

    private CNTour cNTour;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CNTourResource cNTourResource = new CNTourResource(cNTourRepository);
        this.restCNTourMockMvc = MockMvcBuilders.standaloneSetup(cNTourResource)
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
    public static CNTour createEntity(EntityManager em) {
        CNTour cNTour = new CNTour()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .discount(DEFAULT_DISCOUNT)
            .imgUrl1(DEFAULT_IMG_URL_1)
            .imgUrl2(DEFAULT_IMG_URL_2)
            .createBy(DEFAULT_CREATE_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updateBy(DEFAULT_UPDATE_BY)
            .updateTime(DEFAULT_UPDATE_TIME)
            .content(DEFAULT_CONTENT);
        return cNTour;
    }

    @Before
    public void initTest() {
        cNTour = createEntity(em);
    }

    @Test
    @Transactional
    public void createCNTour() throws Exception {
        int databaseSizeBeforeCreate = cNTourRepository.findAll().size();

        // Create the CNTour
        restCNTourMockMvc.perform(post("/api/c-n-tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNTour)))
            .andExpect(status().isCreated());

        // Validate the CNTour in the database
        List<CNTour> cNTourList = cNTourRepository.findAll();
        assertThat(cNTourList).hasSize(databaseSizeBeforeCreate + 1);
        CNTour testCNTour = cNTourList.get(cNTourList.size() - 1);
        assertThat(testCNTour.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCNTour.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCNTour.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testCNTour.getImgUrl1()).isEqualTo(DEFAULT_IMG_URL_1);
        assertThat(testCNTour.getImgUrl2()).isEqualTo(DEFAULT_IMG_URL_2);
        assertThat(testCNTour.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testCNTour.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testCNTour.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testCNTour.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testCNTour.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createCNTourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cNTourRepository.findAll().size();

        // Create the CNTour with an existing ID
        cNTour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCNTourMockMvc.perform(post("/api/c-n-tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNTour)))
            .andExpect(status().isBadRequest());

        // Validate the CNTour in the database
        List<CNTour> cNTourList = cNTourRepository.findAll();
        assertThat(cNTourList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cNTourRepository.findAll().size();
        // set the field null
        cNTour.setName(null);

        // Create the CNTour, which fails.

        restCNTourMockMvc.perform(post("/api/c-n-tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNTour)))
            .andExpect(status().isBadRequest());

        List<CNTour> cNTourList = cNTourRepository.findAll();
        assertThat(cNTourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = cNTourRepository.findAll().size();
        // set the field null
        cNTour.setContent(null);

        // Create the CNTour, which fails.

        restCNTourMockMvc.perform(post("/api/c-n-tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNTour)))
            .andExpect(status().isBadRequest());

        List<CNTour> cNTourList = cNTourRepository.findAll();
        assertThat(cNTourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCNTours() throws Exception {
        // Initialize the database
        cNTourRepository.saveAndFlush(cNTour);

        // Get all the cNTourList
        restCNTourMockMvc.perform(get("/api/c-n-tours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cNTour.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].imgUrl1").value(hasItem(DEFAULT_IMG_URL_1.toString())))
            .andExpect(jsonPath("$.[*].imgUrl2").value(hasItem(DEFAULT_IMG_URL_2.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getCNTour() throws Exception {
        // Initialize the database
        cNTourRepository.saveAndFlush(cNTour);

        // Get the cNTour
        restCNTourMockMvc.perform(get("/api/c-n-tours/{id}", cNTour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cNTour.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.imgUrl1").value(DEFAULT_IMG_URL_1.toString()))
            .andExpect(jsonPath("$.imgUrl2").value(DEFAULT_IMG_URL_2.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCNTour() throws Exception {
        // Get the cNTour
        restCNTourMockMvc.perform(get("/api/c-n-tours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCNTour() throws Exception {
        // Initialize the database
        cNTourRepository.saveAndFlush(cNTour);
        int databaseSizeBeforeUpdate = cNTourRepository.findAll().size();

        // Update the cNTour
        CNTour updatedCNTour = cNTourRepository.findOne(cNTour.getId());
        updatedCNTour
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .discount(UPDATED_DISCOUNT)
            .imgUrl1(UPDATED_IMG_URL_1)
            .imgUrl2(UPDATED_IMG_URL_2)
            .createBy(UPDATED_CREATE_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updateBy(UPDATED_UPDATE_BY)
            .updateTime(UPDATED_UPDATE_TIME)
            .content(UPDATED_CONTENT);

        restCNTourMockMvc.perform(put("/api/c-n-tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCNTour)))
            .andExpect(status().isOk());

        // Validate the CNTour in the database
        List<CNTour> cNTourList = cNTourRepository.findAll();
        assertThat(cNTourList).hasSize(databaseSizeBeforeUpdate);
        CNTour testCNTour = cNTourList.get(cNTourList.size() - 1);
        assertThat(testCNTour.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCNTour.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCNTour.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testCNTour.getImgUrl1()).isEqualTo(UPDATED_IMG_URL_1);
        assertThat(testCNTour.getImgUrl2()).isEqualTo(UPDATED_IMG_URL_2);
        assertThat(testCNTour.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testCNTour.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testCNTour.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testCNTour.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testCNTour.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingCNTour() throws Exception {
        int databaseSizeBeforeUpdate = cNTourRepository.findAll().size();

        // Create the CNTour

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCNTourMockMvc.perform(put("/api/c-n-tours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNTour)))
            .andExpect(status().isCreated());

        // Validate the CNTour in the database
        List<CNTour> cNTourList = cNTourRepository.findAll();
        assertThat(cNTourList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCNTour() throws Exception {
        // Initialize the database
        cNTourRepository.saveAndFlush(cNTour);
        int databaseSizeBeforeDelete = cNTourRepository.findAll().size();

        // Get the cNTour
        restCNTourMockMvc.perform(delete("/api/c-n-tours/{id}", cNTour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CNTour> cNTourList = cNTourRepository.findAll();
        assertThat(cNTourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CNTour.class);
        CNTour cNTour1 = new CNTour();
        cNTour1.setId(1L);
        CNTour cNTour2 = new CNTour();
        cNTour2.setId(cNTour1.getId());
        assertThat(cNTour1).isEqualTo(cNTour2);
        cNTour2.setId(2L);
        assertThat(cNTour1).isNotEqualTo(cNTour2);
        cNTour1.setId(null);
        assertThat(cNTour1).isNotEqualTo(cNTour2);
    }
}
