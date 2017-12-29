package com.nighteagle.web.rest;

import com.nighteagle.AusTravelApp;

import com.nighteagle.domain.PageTourEntry;
import com.nighteagle.repository.PageTourEntryRepository;
import com.nighteagle.service.PageTourEntryService;
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
 * Test class for the PageTourEntryResource REST controller.
 *
 * @see PageTourEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AusTravelApp.class)
public class PageTourEntryResourceIntTest {

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
    private PageTourEntryRepository pageTourEntryRepository;

    @Autowired
    private PageTourEntryService pageTourEntryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPageTourEntryMockMvc;

    private PageTourEntry pageTourEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PageTourEntryResource pageTourEntryResource = new PageTourEntryResource(pageTourEntryService);
        this.restPageTourEntryMockMvc = MockMvcBuilders.standaloneSetup(pageTourEntryResource)
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
    public static PageTourEntry createEntity(EntityManager em) {
        PageTourEntry pageTourEntry = new PageTourEntry()
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
        return pageTourEntry;
    }

    @Before
    public void initTest() {
        pageTourEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createPageTourEntry() throws Exception {
        int databaseSizeBeforeCreate = pageTourEntryRepository.findAll().size();

        // Create the PageTourEntry
        restPageTourEntryMockMvc.perform(post("/api/page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageTourEntry)))
            .andExpect(status().isCreated());

        // Validate the PageTourEntry in the database
        List<PageTourEntry> pageTourEntryList = pageTourEntryRepository.findAll();
        assertThat(pageTourEntryList).hasSize(databaseSizeBeforeCreate + 1);
        PageTourEntry testPageTourEntry = pageTourEntryList.get(pageTourEntryList.size() - 1);
        assertThat(testPageTourEntry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPageTourEntry.getHeading()).isEqualTo(DEFAULT_HEADING);
        assertThat(testPageTourEntry.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testPageTourEntry.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPageTourEntry.getImgUrl1()).isEqualTo(DEFAULT_IMG_URL_1);
        assertThat(testPageTourEntry.getImgUrl2()).isEqualTo(DEFAULT_IMG_URL_2);
        assertThat(testPageTourEntry.getCssStyle()).isEqualTo(DEFAULT_CSS_STYLE);
        assertThat(testPageTourEntry.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testPageTourEntry.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testPageTourEntry.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
        assertThat(testPageTourEntry.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testPageTourEntry.getColWidth()).isEqualTo(DEFAULT_COL_WIDTH);
    }

    @Test
    @Transactional
    public void createPageTourEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pageTourEntryRepository.findAll().size();

        // Create the PageTourEntry with an existing ID
        pageTourEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPageTourEntryMockMvc.perform(post("/api/page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageTourEntry)))
            .andExpect(status().isBadRequest());

        // Validate the PageTourEntry in the database
        List<PageTourEntry> pageTourEntryList = pageTourEntryRepository.findAll();
        assertThat(pageTourEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pageTourEntryRepository.findAll().size();
        // set the field null
        pageTourEntry.setName(null);

        // Create the PageTourEntry, which fails.

        restPageTourEntryMockMvc.perform(post("/api/page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageTourEntry)))
            .andExpect(status().isBadRequest());

        List<PageTourEntry> pageTourEntryList = pageTourEntryRepository.findAll();
        assertThat(pageTourEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = pageTourEntryRepository.findAll().size();
        // set the field null
        pageTourEntry.setContent(null);

        // Create the PageTourEntry, which fails.

        restPageTourEntryMockMvc.perform(post("/api/page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageTourEntry)))
            .andExpect(status().isBadRequest());

        List<PageTourEntry> pageTourEntryList = pageTourEntryRepository.findAll();
        assertThat(pageTourEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPageTourEntries() throws Exception {
        // Initialize the database
        pageTourEntryRepository.saveAndFlush(pageTourEntry);

        // Get all the pageTourEntryList
        restPageTourEntryMockMvc.perform(get("/api/page-tour-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pageTourEntry.getId().intValue())))
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
    public void getPageTourEntry() throws Exception {
        // Initialize the database
        pageTourEntryRepository.saveAndFlush(pageTourEntry);

        // Get the pageTourEntry
        restPageTourEntryMockMvc.perform(get("/api/page-tour-entries/{id}", pageTourEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pageTourEntry.getId().intValue()))
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
    public void getNonExistingPageTourEntry() throws Exception {
        // Get the pageTourEntry
        restPageTourEntryMockMvc.perform(get("/api/page-tour-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePageTourEntry() throws Exception {
        // Initialize the database
        pageTourEntryService.save(pageTourEntry);

        int databaseSizeBeforeUpdate = pageTourEntryRepository.findAll().size();

        // Update the pageTourEntry
        PageTourEntry updatedPageTourEntry = pageTourEntryRepository.findOne(pageTourEntry.getId());
        updatedPageTourEntry
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

        restPageTourEntryMockMvc.perform(put("/api/page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPageTourEntry)))
            .andExpect(status().isOk());

        // Validate the PageTourEntry in the database
        List<PageTourEntry> pageTourEntryList = pageTourEntryRepository.findAll();
        assertThat(pageTourEntryList).hasSize(databaseSizeBeforeUpdate);
        PageTourEntry testPageTourEntry = pageTourEntryList.get(pageTourEntryList.size() - 1);
        assertThat(testPageTourEntry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPageTourEntry.getHeading()).isEqualTo(UPDATED_HEADING);
        assertThat(testPageTourEntry.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testPageTourEntry.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPageTourEntry.getImgUrl1()).isEqualTo(UPDATED_IMG_URL_1);
        assertThat(testPageTourEntry.getImgUrl2()).isEqualTo(UPDATED_IMG_URL_2);
        assertThat(testPageTourEntry.getCssStyle()).isEqualTo(UPDATED_CSS_STYLE);
        assertThat(testPageTourEntry.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testPageTourEntry.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testPageTourEntry.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
        assertThat(testPageTourEntry.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testPageTourEntry.getColWidth()).isEqualTo(UPDATED_COL_WIDTH);
    }

    @Test
    @Transactional
    public void updateNonExistingPageTourEntry() throws Exception {
        int databaseSizeBeforeUpdate = pageTourEntryRepository.findAll().size();

        // Create the PageTourEntry

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPageTourEntryMockMvc.perform(put("/api/page-tour-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pageTourEntry)))
            .andExpect(status().isCreated());

        // Validate the PageTourEntry in the database
        List<PageTourEntry> pageTourEntryList = pageTourEntryRepository.findAll();
        assertThat(pageTourEntryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePageTourEntry() throws Exception {
        // Initialize the database
        pageTourEntryService.save(pageTourEntry);

        int databaseSizeBeforeDelete = pageTourEntryRepository.findAll().size();

        // Get the pageTourEntry
        restPageTourEntryMockMvc.perform(delete("/api/page-tour-entries/{id}", pageTourEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PageTourEntry> pageTourEntryList = pageTourEntryRepository.findAll();
        assertThat(pageTourEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PageTourEntry.class);
        PageTourEntry pageTourEntry1 = new PageTourEntry();
        pageTourEntry1.setId(1L);
        PageTourEntry pageTourEntry2 = new PageTourEntry();
        pageTourEntry2.setId(pageTourEntry1.getId());
        assertThat(pageTourEntry1).isEqualTo(pageTourEntry2);
        pageTourEntry2.setId(2L);
        assertThat(pageTourEntry1).isNotEqualTo(pageTourEntry2);
        pageTourEntry1.setId(null);
        assertThat(pageTourEntry1).isNotEqualTo(pageTourEntry2);
    }
}
