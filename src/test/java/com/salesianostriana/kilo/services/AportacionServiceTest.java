package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*@ExtendWith(MockitoExtension.class)
class AportacionServiceTest {

    @Mock
    AportacionRepository aportacionRepository;

    @InjectMocks
    AportacionService aportacionService;

    @InjectMocks
    TipoAlimentoSaveService tipoAlimentoSaveService;

    @ParameterizedTest
    @CsvSource({
            "1, 12, 3",
            "0, 7, 2",
            "1, 12, 3",
            "3, 7, 2",
            "10, 2, 1"
    })
    void cambiarKilosDetalleMayorQue0YKilosMayorQueResult(double num, double num2, double num3) {
        double result = num;
        double kilosActuales = num2;
        double kilosNuevos = num3;

        DetalleAportacion detalleAportacion = new DetalleAportacion();

        Optional<Aportacion> aportacion = aportacionService.cambiarKilosDetalle(detalleAportacion, 20);


        assertTrue(!aportacion.isEmpty());
    }

    @Test
    void editAportacion() {
    }
}

 */