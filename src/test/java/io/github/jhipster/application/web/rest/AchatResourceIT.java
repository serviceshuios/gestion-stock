package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.GestionStockApp;
import io.github.jhipster.application.domain.Achat;
import io.github.jhipster.application.repository.AchatRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AchatResource} REST controller.
 */
@SpringBootTest(classes = GestionStockApp.class)
public class AchatResourceIT {

    private static final LocalDate DEFAULT_ACHAT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ACHAT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ACHAT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACHAT_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACHAT_QUANTITE = 1;
    private static final Integer UPDATED_ACHAT_QUANTITE = 2;

    @Autowired
    private AchatRepository achatRepository;

    @Mock
    private AchatRepository achatRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAchatMockMvc;

    private Achat achat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AchatResource achatResource = new AchatResource(achatRepository);
        this.restAchatMockMvc = MockMvcBuilders.standaloneSetup(achatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achat createEntity(EntityManager em) {
        Achat achat = new Achat()
            .achatDate(DEFAULT_ACHAT_DATE)
            .achatDescription(DEFAULT_ACHAT_DESCRIPTION)
            .achatQuantite(DEFAULT_ACHAT_QUANTITE);
        return achat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achat createUpdatedEntity(EntityManager em) {
        Achat achat = new Achat()
            .achatDate(UPDATED_ACHAT_DATE)
            .achatDescription(UPDATED_ACHAT_DESCRIPTION)
            .achatQuantite(UPDATED_ACHAT_QUANTITE);
        return achat;
    }

    @BeforeEach
    public void initTest() {
        achat = createEntity(em);
    }

    @Test
    @Transactional
    public void createAchat() throws Exception {
        int databaseSizeBeforeCreate = achatRepository.findAll().size();

        // Create the Achat
        restAchatMockMvc.perform(post("/api/achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achat)))
            .andExpect(status().isCreated());

        // Validate the Achat in the database
        List<Achat> achatList = achatRepository.findAll();
        assertThat(achatList).hasSize(databaseSizeBeforeCreate + 1);
        Achat testAchat = achatList.get(achatList.size() - 1);
        assertThat(testAchat.getAchatDate()).isEqualTo(DEFAULT_ACHAT_DATE);
        assertThat(testAchat.getAchatDescription()).isEqualTo(DEFAULT_ACHAT_DESCRIPTION);
        assertThat(testAchat.getAchatQuantite()).isEqualTo(DEFAULT_ACHAT_QUANTITE);
    }

    @Test
    @Transactional
    public void createAchatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = achatRepository.findAll().size();

        // Create the Achat with an existing ID
        achat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAchatMockMvc.perform(post("/api/achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achat)))
            .andExpect(status().isBadRequest());

        // Validate the Achat in the database
        List<Achat> achatList = achatRepository.findAll();
        assertThat(achatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAchatDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = achatRepository.findAll().size();
        // set the field null
        achat.setAchatDate(null);

        // Create the Achat, which fails.

        restAchatMockMvc.perform(post("/api/achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achat)))
            .andExpect(status().isBadRequest());

        List<Achat> achatList = achatRepository.findAll();
        assertThat(achatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAchatDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = achatRepository.findAll().size();
        // set the field null
        achat.setAchatDescription(null);

        // Create the Achat, which fails.

        restAchatMockMvc.perform(post("/api/achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achat)))
            .andExpect(status().isBadRequest());

        List<Achat> achatList = achatRepository.findAll();
        assertThat(achatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAchatQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = achatRepository.findAll().size();
        // set the field null
        achat.setAchatQuantite(null);

        // Create the Achat, which fails.

        restAchatMockMvc.perform(post("/api/achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achat)))
            .andExpect(status().isBadRequest());

        List<Achat> achatList = achatRepository.findAll();
        assertThat(achatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAchats() throws Exception {
        // Initialize the database
        achatRepository.saveAndFlush(achat);

        // Get all the achatList
        restAchatMockMvc.perform(get("/api/achats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achat.getId().intValue())))
            .andExpect(jsonPath("$.[*].achatDate").value(hasItem(DEFAULT_ACHAT_DATE.toString())))
            .andExpect(jsonPath("$.[*].achatDescription").value(hasItem(DEFAULT_ACHAT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].achatQuantite").value(hasItem(DEFAULT_ACHAT_QUANTITE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAchatsWithEagerRelationshipsIsEnabled() throws Exception {
        AchatResource achatResource = new AchatResource(achatRepositoryMock);
        when(achatRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAchatMockMvc = MockMvcBuilders.standaloneSetup(achatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAchatMockMvc.perform(get("/api/achats?eagerload=true"))
        .andExpect(status().isOk());

        verify(achatRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAchatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        AchatResource achatResource = new AchatResource(achatRepositoryMock);
            when(achatRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAchatMockMvc = MockMvcBuilders.standaloneSetup(achatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAchatMockMvc.perform(get("/api/achats?eagerload=true"))
        .andExpect(status().isOk());

            verify(achatRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAchat() throws Exception {
        // Initialize the database
        achatRepository.saveAndFlush(achat);

        // Get the achat
        restAchatMockMvc.perform(get("/api/achats/{id}", achat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(achat.getId().intValue()))
            .andExpect(jsonPath("$.achatDate").value(DEFAULT_ACHAT_DATE.toString()))
            .andExpect(jsonPath("$.achatDescription").value(DEFAULT_ACHAT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.achatQuantite").value(DEFAULT_ACHAT_QUANTITE));
    }

    @Test
    @Transactional
    public void getNonExistingAchat() throws Exception {
        // Get the achat
        restAchatMockMvc.perform(get("/api/achats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAchat() throws Exception {
        // Initialize the database
        achatRepository.saveAndFlush(achat);

        int databaseSizeBeforeUpdate = achatRepository.findAll().size();

        // Update the achat
        Achat updatedAchat = achatRepository.findById(achat.getId()).get();
        // Disconnect from session so that the updates on updatedAchat are not directly saved in db
        em.detach(updatedAchat);
        updatedAchat
            .achatDate(UPDATED_ACHAT_DATE)
            .achatDescription(UPDATED_ACHAT_DESCRIPTION)
            .achatQuantite(UPDATED_ACHAT_QUANTITE);

        restAchatMockMvc.perform(put("/api/achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAchat)))
            .andExpect(status().isOk());

        // Validate the Achat in the database
        List<Achat> achatList = achatRepository.findAll();
        assertThat(achatList).hasSize(databaseSizeBeforeUpdate);
        Achat testAchat = achatList.get(achatList.size() - 1);
        assertThat(testAchat.getAchatDate()).isEqualTo(UPDATED_ACHAT_DATE);
        assertThat(testAchat.getAchatDescription()).isEqualTo(UPDATED_ACHAT_DESCRIPTION);
        assertThat(testAchat.getAchatQuantite()).isEqualTo(UPDATED_ACHAT_QUANTITE);
    }

    @Test
    @Transactional
    public void updateNonExistingAchat() throws Exception {
        int databaseSizeBeforeUpdate = achatRepository.findAll().size();

        // Create the Achat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAchatMockMvc.perform(put("/api/achats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achat)))
            .andExpect(status().isBadRequest());

        // Validate the Achat in the database
        List<Achat> achatList = achatRepository.findAll();
        assertThat(achatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAchat() throws Exception {
        // Initialize the database
        achatRepository.saveAndFlush(achat);

        int databaseSizeBeforeDelete = achatRepository.findAll().size();

        // Delete the achat
        restAchatMockMvc.perform(delete("/api/achats/{id}", achat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Achat> achatList = achatRepository.findAll();
        assertThat(achatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Achat.class);
        Achat achat1 = new Achat();
        achat1.setId(1L);
        Achat achat2 = new Achat();
        achat2.setId(achat1.getId());
        assertThat(achat1).isEqualTo(achat2);
        achat2.setId(2L);
        assertThat(achat1).isNotEqualTo(achat2);
        achat1.setId(null);
        assertThat(achat1).isNotEqualTo(achat2);
    }
}
