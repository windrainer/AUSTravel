package com.nighteagle.web.rest;

import com.nighteagle.AusTravelApp;

import com.nighteagle.domain.CNPageTourEntry;
import com.nighteagle.repository.CNPageTourEntryRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CNPageTourEntryResource REST controller.
 *
 * @see CNPageTourEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AusTravelApp.class)
public class CNPageTourEntryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HEADING = "AAAAAAAAAA";
    private static final String UPDATED_HEADING = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_URL_1 = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_URL_2 = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CSS_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_CSS_STYLE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATE_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COL_WIDTH = "AAAAAAAAAA";
    private static final String UPDATED_COL_WIDTH = "BBBBBBBBBB";

    @Autowired
    private CNPageTourEntryRepository cNPageTourEntryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCNPageTourEntryMockMvc;

    private CNPageTourEntry cNPageTourEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CNPageTourEntryResource cNPageTourEntryResource = new CNPageTourEntryResource(cNPageTourEntryRepository);
        this.restCNPageTourEntryMockMvc = MockMvcBuilders.standaloneSetup(cNPageTourEntryResource)
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
    public static CNPageTourEntry createEntity(EntityManager em) {
        CNPageTourEntry cNPageTourEntry = new CNPageTourEntry()
            .name(DEFAULT_NAME)
            .heading(DEFAULT_HEADING)
            .subTitle(DEFAULT_SUB_TITLE)
            .content(DEFAULT_CONTENT)
            .imgUrl1(DEFAULT_IMG_URL_1)
            .imgUrl2(DEFAULT_IMG_URL_2)
            .cssStyle(DEFAULT_CSS_STYLE)
            .createBy(DEFAULT_CREATE_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updateBy(DEFAULT_UPDATE_BY)
            .updateTime(DEFAULT_UPDATE_TIME)
            .colWidth(DEFAULT_COL_WIDTH);
        return cNPageTourEntry;
    }

    @Before
    public void initTest() {
        cNPageTourEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createCNPageTourEntry() throws Exception {
        int databaseSizeBeforeCreate = cNPageTourEntryRepository.findAll().size();

        // Create the CNPageTourEntry
        restCNPageTourEntryMockMvc.perform(post("/api/c-n-page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNPageTourEntry)))
            .andExpect(status().isCreated());

        // Validate the CNPageTourEntry in the database
        List<CNPageTourEntry> cNPageTourEntryList = cNPageTourEntryRepository.findAll();
        assertThat(cNPageTourEntryList).hasSize(databaseSizeBeforeCreate + 1);
        CNPageTourEntry testCNPageTourEntry = cNPageTourEntryList.get(cNPageTourEntryList.size() - 1);
        assertThat(testCNPageTourEntry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCNPageTourEntry.getHeading()).isEqualTo(DEFAULT_HEADING);
        assertThat(testCNPageTourEntry.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testCNPageTourEntry.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCNPageTourEntry.getImgUrl1()).isEqualTo(DEFAULT_IMG_URL_1);
        assertThat(testCNPageTourEntry.getImgUrl2()).isEqualTo(DEFAULT_IMG_URL_2);
        assertThat(testCNPageTourEntry.getCssStyle()).isEqualTo(DEFAULT_CSS_STYLE);
        assertThat(testCNPageTourEntry.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testCNPageTourEntry.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testCNPageTourEntry.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testCNPageTourEntry.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testCNPageTourEntry.getColWidth()).isEqualTo(DEFAULT_COL_WIDTH);
    }

    @Test
    @Transactional
    public void createCNPageTourEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cNPageTourEntryRepository.findAll().size();

        // Create the CNPageTourEntry with an existing ID
        cNPageTourEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCNPageTourEntryMockMvc.perform(post("/api/c-n-page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNPageTourEntry)))
            .andExpect(status().isBadRequest());

        // Validate the CNPageTourEntry in the database
        List<CNPageTourEntry> cNPageTourEntryList = cNPageTourEntryRepository.findAll();
        assertThat(cNPageTourEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cNPageTourEntryRepository.findAll().size();
        // set the field null
        cNPageTourEntry.setName(null);

        // Create the CNPageTourEntry, which fails.

        restCNPageTourEntryMockMvc.perform(post("/api/c-n-page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNPageTourEntry)))
            .andExpect(status().isBadRequest());

        List<CNPageTourEntry> cNPageTourEntryList = cNPageTourEntryRepository.findAll();
        assertThat(cNPageTourEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = cNPageTourEntryRepository.findAll().size();
        // set the field null
        cNPageTourEntry.setSubTitle(null);

        // Create the CNPageTourEntry, which fails.

        restCNPageTourEntryMockMvc.perform(post("/api/c-n-page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNPageTourEntry)))
            .andExpect(status().isBadRequest());

        List<CNPageTourEntry> cNPageTourEntryList = cNPageTourEntryRepository.findAll();
        assertThat(cNPageTourEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = cNPageTourEntryRepository.findAll().size();
        // set the field null
        cNPageTourEntry.setContent(null);

        // Create the CNPageTourEntry, which fails.

        restCNPageTourEntryMockMvc.perform(post("/api/c-n-page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNPageTourEntry)))
            .andExpect(status().isBadRequest());

        List<CNPageTourEntry> cNPageTourEntryList = cNPageTourEntryRepository.findAll();
        assertThat(cNPageTourEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCNPageTourEntries() throws Exception {
        // Initialize the database
        cNPageTourEntryRepository.saveAndFlush(cNPageTourEntry);

        // Get all the cNPageTourEntryList
        restCNPageTourEntryMockMvc.perform(get("/api/c-n-page-tour-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cNPageTourEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].heading").value(hasItem(DEFAULT_HEADING.toString())))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].imgUrl1").value(hasItem(DEFAULT_IMG_URL_1.toString())))
            .andExpect(jsonPath("$.[*].imgUrl2").value(hasItem(DEFAULT_IMG_URL_2.toString())))
            .andExpect(jsonPath("$.[*].cssStyle").value(hasItem(DEFAULT_CSS_STYLE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].colWidth").value(hasItem(DEFAULT_COL_WIDTH.toString())));
    }

    @Test
    @Transactional
    public void getCNPageTourEntry() throws Exception {
        // Initialize the database
        cNPageTourEntryRepository.saveAndFlush(cNPageTourEntry);

        // Get the cNPageTourEntry
        restCNPageTourEntryMockMvc.perform(get("/api/c-n-page-tour-entries/{id}", cNPageTourEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cNPageTourEntry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.heading").value(DEFAULT_HEADING.toString()))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.imgUrl1").value(DEFAULT_IMG_URL_1.toString()))
            .andExpect(jsonPath("$.imgUrl2").value(DEFAULT_IMG_URL_2.toString()))
            .andExpect(jsonPath("$.cssStyle").value(DEFAULT_CSS_STYLE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.colWidth").value(DEFAULT_COL_WIDTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCNPageTourEntry() throws Exception {
        // Get the cNPageTourEntry
        restCNPageTourEntryMockMvc.perform(get("/api/c-n-page-tour-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCNPageTourEntry() throws Exception {
        // Initialize the database
        cNPageTourEntryRepository.saveAndFlush(cNPageTourEntry);
        int databaseSizeBeforeUpdate = cNPageTourEntryRepository.findAll().size();

        // Update the cNPageTourEntry
        CNPageTourEntry updatedCNPageTourEntry = cNPageTourEntryRepository.findOne(cNPageTourEntry.getId());
        updatedCNPageTourEntry
            .name(UPDATED_NAME)
            .heading(UPDATED_HEADING)
            .subTitle(UPDATED_SUB_TITLE)
            .content(UPDATED_CONTENT)
            .imgUrl1(UPDATED_IMG_URL_1)
            .imgUrl2(UPDATED_IMG_URL_2)
            .cssStyle(UPDATED_CSS_STYLE)
            .createBy(UPDATED_CREATE_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updateBy(UPDATED_UPDATE_BY)
            .updateTime(UPDATED_UPDATE_TIME)
            .colWidth(UPDATED_COL_WIDTH);

        restCNPageTourEntryMockMvc.perform(put("/api/c-n-page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCNPageTourEntry)))
            .andExpect(status().isOk());

        // Validate the CNPageTourEntry in the database
        List<CNPageTourEntry> cNPageTourEntryList = cNPageTourEntryRepository.findAll();
        assertThat(cNPageTourEntryList).hasSize(databaseSizeBeforeUpdate);
        CNPageTourEntry testCNPageTourEntry = cNPageTourEntryList.get(cNPageTourEntryList.size() - 1);
        assertThat(testCNPageTourEntry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCNPageTourEntry.getHeading()).isEqualTo(UPDATED_HEADING);
        assertThat(testCNPageTourEntry.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testCNPageTourEntry.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCNPageTourEntry.getImgUrl1()).isEqualTo(UPDATED_IMG_URL_1);
        assertThat(testCNPageTourEntry.getImgUrl2()).isEqualTo(UPDATED_IMG_URL_2);
        assertThat(testCNPageTourEntry.getCssStyle()).isEqualTo(UPDATED_CSS_STYLE);
        assertThat(testCNPageTourEntry.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testCNPageTourEntry.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testCNPageTourEntry.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testCNPageTourEntry.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testCNPageTourEntry.getColWidth()).isEqualTo(UPDATED_COL_WIDTH);
    }

    @Test
    @Transactional
    public void updateNonExistingCNPageTourEntry() throws Exception {
        int databaseSizeBeforeUpdate = cNPageTourEntryRepository.findAll().size();

        // Create the CNPageTourEntry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCNPageTourEntryMockMvc.perform(put("/api/c-n-page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cNPageTourEntry)))
            .andExpect(status().isCreated());

        // Validate the CNPageTourEntry in the database
        List<CNPageTourEntry> cNPageTourEntryList = cNPageTourEntryRepository.findAll();
        assertThat(cNPageTourEntryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCNPageTourEntry() throws Exception {
        // Initialize the database
        cNPageTourEntryRepository.saveAndFlush(cNPageTourEntry);
        int databaseSizeBeforeDelete = cNPageTourEntryRepository.findAll().size();

        // Get the cNPageTourEntry
        restCNPageTourEntryMockMvc.perform(delete("/api/c-n-page-tour-entries/{id}", cNPageTourEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CNPageTourEntry> cNPageTourEntryList = cNPageTourEntryRepository.findAll();
        assertThat(cNPageTourEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CNPageTourEntry.class);
        CNPageTourEntry cNPageTourEntry1 = new CNPageTourEntry();
        cNPageTourEntry1.setId(1L);
        CNPageTourEntry cNPageTourEntry2 = new CNPageTourEntry();
        cNPageTourEntry2.setId(cNPageTourEntry1.getId());
        assertThat(cNPageTourEntry1).isEqualTo(cNPageTourEntry2);
        cNPageTourEntry2.setId(2L);
        assertThat(cNPageTourEntry1).isNotEqualTo(cNPageTourEntry2);
        cNPageTourEntry1.setId(null);
        assertThat(cNPageTourEntry1).isNotEqualTo(cNPageTourEntry2);
    }
}
