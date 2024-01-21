package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.dtos.ranking.RankQueryResponseDTO;
import com.salesianostriana.kilo.entities.Clase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@Testcontainers
@Sql(value = "classpath:insert-aportaciones.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:delete-aportaciones.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ClaseRepositoryTest {

    @Autowired
    ClaseRepository claseRepository;

    @Autowired
    AportacionRepository aportacionRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withUsername("testUser")
            .withPassword("testSecret")
            .withDatabaseName("testDatabase");

    @Test
    void findKilos() {
        Double kilosClase1 = claseRepository.findKilos(1L);
        assertEquals(27.7, kilosClase1);
    }


    @Test
    void findClasesOrderedByRank() {
        List<RankQueryResponseDTO> lista1 = claseRepository.findClasesOrderedByRank();

        assertTrue(lista1.get(0).getSumaKilos() > lista1.get(1).getSumaKilos());
    }
}