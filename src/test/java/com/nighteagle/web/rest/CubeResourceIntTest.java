package com.nighteagle.web.rest;

import com.nighteagle.AusTravelApp;

import com.nighteagle.domain.Cube;
import com.nighteagle.repository.CubeRepository;
import com.nighteagle.service.CubeService;
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
 * Test class for the CubeResource REST controller.
 *
 * @see CubeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AusTravelApp.class)
public class CubeResourceIntTest {

    private static final String DEFAULT_IMG_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL = "BBBBBBBBBB";

    private static final String DEFAULT_COL_WIDTH = "AAAAAAAAAA";
    private static final String UPDATED_COL_WIDTH = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_HEADING = "AAAAAAAAAA";
    private static final String UPDATED_HEADING = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_STYLE = "BBBBBBBBBB";

    @Autowired
    private CubeRepository cubeRepository;

    @Autowired
    private CubeService cubeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCubeMockMvc;

    private Cube cube;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CubeResource cubeResource = new CubeResource(cubeService);
        this.restCubeMockMvc = MockMvcBuilders.standaloneSetup(cubeResource)
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
    public static Cube createEntity(EntityManager em) {
        Cube cube = new Cube()
            .imgUrl(DEFAULT_IMG_URL)
            .colWidth(DEFAULT_COL_WIDTH)
            .subTitle(DEFAULT_SUB_TITLE)
            .heading(DEFAULT_HEADING)
            .content(DEFAULT_CONTENT)
            .style(DEFAULT_STYLE);
        return cube;
    }

    @Before
    public void initTest() {
        cube = createEntity(em);
    }

    @Test
    @Transactional
    public void createCube() throws Exception {
        int databaseSizeBeforeCreate = cubeRepository.findAll().size();

        // Create the Cube
        restCubeMockMvc.perform(post("/api/cubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cube)))
            .andExpect(status().isCreated());

        // Validate the Cube in the database
        List<Cube> cubeList = cubeRepository.findAll();
        assertThat(cubeList).hasSize(databaseSizeBeforeCreate + 1);
        Cube testCube = cubeList.get(cubeList.size() - 1);
        assertThat(testCube.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
        assertThat(testCube.getColWidth()).isEqualTo(DEFAULT_COL_WIDTH);
        assertThat(testCube.getSubTitle()).isEqualTo(DEFAULT_SUB_TITLE);
        assertThat(testCube.getHeading()).isEqualTo(DEFAULT_HEADING);
        assertThat(testCube.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCube.getStyle()).isEqualTo(DEFAULT_STYLE);
    }

    @Test
    @Transactional
    public void createCubeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cubeRepository.findAll().size();

        // Create the Cube with an existing ID
        cube.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCubeMockMvc.perform(post("/api/cubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cube)))
            .andExpect(status().isBadRequest());

        // Validate the Cube in the database
        List<Cube> cubeList = cubeRepository.findAll();
        assertThat(cubeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCubes() throws Exception {
        // Initialize the database
        cubeRepository.saveAndFlush(cube);

        // Get all the cubeList
        restCubeMockMvc.perform(get("/api/cubes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cube.getId().intValue())))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL.toString())))
            .andExpect(jsonPath("$.[*].colWidth").value(hasItem(DEFAULT_COL_WIDTH.toString())))
            .andExpect(jsonPath("$.[*].subTitle").value(hasItem(DEFAULT_SUB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].heading").value(hasItem(DEFAULT_HEADING.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].style").value(hasItem(DEFAULT_STYLE.toString())));
    }

    @Test
    @Transactional
    public void getCube() throws Exception {
        // Initialize the database
        cubeRepository.saveAndFlush(cube);

        // Get the cube
        restCubeMockMvc.perform(get("/api/cubes/{id}", cube.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cube.getId().intValue()))
            .andExpect(jsonPath("$.imgUrl").value(DEFAULT_IMG_URL.toString()))
            .andExpect(jsonPath("$.colWidth").value(DEFAULT_COL_WIDTH.toString()))
            .andExpect(jsonPath("$.subTitle").value(DEFAULT_SUB_TITLE.toString()))
            .andExpect(jsonPath("$.heading").value(DEFAULT_HEADING.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.style").value(DEFAULT_STYLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCube() throws Exception {
        // Get the cube
        restCubeMockMvc.perform(get("/api/cubes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCube() throws Exception {
        // Initialize the database
        cubeService.save(cube);

        int databaseSizeBeforeUpdate = cubeRepository.findAll().size();

        // Update the cube
        Cube updatedCube = cubeRepository.findOne(cube.getId());
        updatedCube
            .imgUrl(UPDATED_IMG_URL)
            .colWidth(UPDATED_COL_WIDTH)
            .subTitle(UPDATED_SUB_TITLE)
            .heading(UPDATED_HEADING)
            .content(UPDATED_CONTENT)
            .style(UPDATED_STYLE);

        restCubeMockMvc.perform(put("/api/cubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCube)))
            .andExpect(status().isOk());

        // Validate the Cube in the database
        List<Cube> cubeList = cubeRepository.findAll();
        assertThat(cubeList).hasSize(databaseSizeBeforeUpdate);
        Cube testCube = cubeList.get(cubeList.size() - 1);
        assertThat(testCube.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
        assertThat(testCube.getColWidth()).isEqualTo(UPDATED_COL_WIDTH);
        assertThat(testCube.getSubTitle()).isEqualTo(UPDATED_SUB_TITLE);
        assertThat(testCube.getHeading()).isEqualTo(UPDATED_HEADING);
        assertThat(testCube.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCube.getStyle()).isEqualTo(UPDATED_STYLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCube() throws Exception {
        int databaseSizeBeforeUpdate = cubeRepository.findAll().size();

        // Create the Cube

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCubeMockMvc.perform(put("/api/cubes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cube)))
            .andExpect(status().isCreated());

        // Validate the Cube in the database
        List<Cube> cubeList = cubeRepository.findAll();
        assertThat(cubeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCube() throws Exception {
        // Initialize the database
        cubeService.save(cube);

        int databaseSizeBeforeDelete = cubeRepository.findAll().size();

        // Get the cube
        restCubeMockMvc.perform(delete("/api/cubes/{id}", cube.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cube> cubeList = cubeRepository.findAll();
        assertThat(cubeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cube.class);
        Cube cube1 = new Cube();
        cube1.setId(1L);
        Cube cube2 = new Cube();
        cube2.setId(cube1.getId());
        assertThat(cube1).isEqualTo(cube2);
        cube2.setId(2L);
        assertThat(cube1).isNotEqualTo(cube2);
        cube1.setId(null);
        assertThat(cube1).isNotEqualTo(cube2);
    }
}
