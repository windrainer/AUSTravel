package com.nighteagle.web.rest;

import com.nighteagle.AusTravelApp;

import com.nighteagle.domain.Banner;
import com.nighteagle.repository.BannerRepository;
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
 * Test class for the BannerResource REST controller.
 *
 * @see BannerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AusTravelApp.class)
public class BannerResourceIntTest {

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
    private BannerRepository bannerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBannerMockMvc;

    private Banner banner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BannerResource bannerResource = new BannerResource(bannerRepository);
        this.restBannerMockMvc = MockMvcBuilders.standaloneSetup(bannerResource)
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
    public static Banner createEntity(EntityManager em) {
        Banner banner = new Banner()
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
        return banner;
    }

    @Before
    public void initTest() {
        banner = createEntity(em);
    }

    @Test
    @Transactional
    public void createBanner() throws Exception {
        int databaseSizeBeforeCreate = bannerRepository.findAll().size();

        // Create the Banner
        restBannerMockMvc.perform(post("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isCreated());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate + 1);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getBackground()).isEqualTo(DEFAULT_BACKGROUND);
        assertThat(testBanner.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testBanner.getText1()).isEqualTo(DEFAULT_TEXT_1);
        assertThat(testBanner.getText2()).isEqualTo(DEFAULT_TEXT_2);
        assertThat(testBanner.getUrl1()).isEqualTo(DEFAULT_URL_1);
        assertThat(testBanner.getUrl2()).isEqualTo(DEFAULT_URL_2);
        assertThat(testBanner.getCss1()).isEqualTo(DEFAULT_CSS_1);
        assertThat(testBanner.getCss2()).isEqualTo(DEFAULT_CSS_2);
        assertThat(testBanner.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBanner.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
    }

    @Test
    @Transactional
    public void createBannerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bannerRepository.findAll().size();

        // Create the Banner with an existing ID
        banner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannerMockMvc.perform(post("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = bannerRepository.findAll().size();
        // set the field null
        banner.setActivated(null);

        // Create the Banner, which fails.

        restBannerMockMvc.perform(post("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isBadRequest());

        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBanners() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get all the bannerList
        restBannerMockMvc.perform(get("/api/banners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banner.getId().intValue())))
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
    public void getBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);

        // Get the banner
        restBannerMockMvc.perform(get("/api/banners/{id}", banner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(banner.getId().intValue()))
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
    public void getNonExistingBanner() throws Exception {
        // Get the banner
        restBannerMockMvc.perform(get("/api/banners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Update the banner
        Banner updatedBanner = bannerRepository.findOne(banner.getId());
        updatedBanner
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

        restBannerMockMvc.perform(put("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBanner)))
            .andExpect(status().isOk());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate);
        Banner testBanner = bannerList.get(bannerList.size() - 1);
        assertThat(testBanner.getBackground()).isEqualTo(UPDATED_BACKGROUND);
        assertThat(testBanner.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testBanner.getText1()).isEqualTo(UPDATED_TEXT_1);
        assertThat(testBanner.getText2()).isEqualTo(UPDATED_TEXT_2);
        assertThat(testBanner.getUrl1()).isEqualTo(UPDATED_URL_1);
        assertThat(testBanner.getUrl2()).isEqualTo(UPDATED_URL_2);
        assertThat(testBanner.getCss1()).isEqualTo(UPDATED_CSS_1);
        assertThat(testBanner.getCss2()).isEqualTo(UPDATED_CSS_2);
        assertThat(testBanner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBanner.isActivated()).isEqualTo(UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBanner() throws Exception {
        int databaseSizeBeforeUpdate = bannerRepository.findAll().size();

        // Create the Banner

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBannerMockMvc.perform(put("/api/banners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(banner)))
            .andExpect(status().isCreated());

        // Validate the Banner in the database
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBanner() throws Exception {
        // Initialize the database
        bannerRepository.saveAndFlush(banner);
        int databaseSizeBeforeDelete = bannerRepository.findAll().size();

        // Get the banner
        restBannerMockMvc.perform(delete("/api/banners/{id}", banner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Banner> bannerList = bannerRepository.findAll();
        assertThat(bannerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banner.class);
        Banner banner1 = new Banner();
        banner1.setId(1L);
        Banner banner2 = new Banner();
        banner2.setId(banner1.getId());
        assertThat(banner1).isEqualTo(banner2);
        banner2.setId(2L);
        assertThat(banner1).isNotEqualTo(banner2);
        banner1.setId(null);
        assertThat(banner1).isNotEqualTo(banner2);
    }
}
