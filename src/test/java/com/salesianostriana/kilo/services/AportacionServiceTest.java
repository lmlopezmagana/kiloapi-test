package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.*;
import com.salesianostriana.kilo.entities.keys.DetalleAportacionPK;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import com.salesianostriana.kilo.repositories.TipoAlimentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AportacionServiceTest {

    @Mock
    AportacionRepository aportacionRepository;

    @Mock
    TipoAlimentoRepository tipoAlimentoRepository;

    @InjectMocks
    AportacionService aportacionService;

    @Mock
    TipoAlimentoSaveService tipoAlimentoSaveService;

    @Test
    void testCambiarKilosDetalleNegativeResultNotEnoughKilos() {
        Clase clase = new Clase();
        Aportacion aportacion = new Aportacion(1L, LocalDate.now(), clase, null);
        DetalleAportacion detalle = new DetalleAportacion();
        DetalleAportacionPK pk = new DetalleAportacionPK(1L, aportacion.getId());
        detalle.setAportacion(aportacion);
        detalle.setDetalleAportacionPK(pk);
        detalle.setCantidadKg(20.0);
        aportacion.setDetalleAportaciones(List.of(detalle));
        TipoAlimento tipoAlimento = new TipoAlimento(1L, "Lentejas", List.of(detalle), null);
        KilosDisponibles kilosDisponibles = new KilosDisponibles(tipoAlimento, 1L, 10.0);
        tipoAlimento.setKilosDisponibles(kilosDisponibles);
        detalle.setTipoAlimento(tipoAlimento);

        assertFalse(aportacionService.cambiarKilosDetalle(detalle, 2.0).isPresent());
    }

    @Test
    void testCambiarKilosDetalleNegativeResultEnoughKilos() {
        Clase clase = new Clase();
        Aportacion aportacion = new Aportacion(1L, LocalDate.now(), clase, null);
        DetalleAportacion detalle = new DetalleAportacion();
        DetalleAportacionPK pk = new DetalleAportacionPK(1L, aportacion.getId());
        detalle.setAportacion(aportacion);
        detalle.setDetalleAportacionPK(pk);
        detalle.setCantidadKg(20.0);
        aportacion.setDetalleAportaciones(List.of(detalle));
        TipoAlimento tipoAlimento = new TipoAlimento(1L, "Lentejas", List.of(detalle), null);
        KilosDisponibles kilosDisponibles = new KilosDisponibles(tipoAlimento, 1L, 120.0);
        tipoAlimento.setKilosDisponibles(kilosDisponibles);
        detalle.setTipoAlimento(tipoAlimento);

        Mockito.when(tipoAlimentoSaveService.save(tipoAlimento))
                .thenReturn(tipoAlimento);

        Mockito.when(aportacionRepository.save(aportacion)).thenReturn(aportacion);

        assertTrue(aportacionService.cambiarKilosDetalle(detalle, 2.0).isPresent());
    }

    @Test
    void testCambiarKilosDetallePositiveResult(){
        Clase clase = new Clase();
        Aportacion aportacion = new Aportacion(1L, LocalDate.now(), clase, null);
        DetalleAportacion detalle = new DetalleAportacion();
        DetalleAportacionPK pk = new DetalleAportacionPK(1L, aportacion.getId());
        detalle.setAportacion(aportacion);
        detalle.setDetalleAportacionPK(pk);
        detalle.setCantidadKg(20.0);
        aportacion.setDetalleAportaciones(List.of(detalle));
        TipoAlimento tipoAlimento = new TipoAlimento(1L, "Lentejas", List.of(detalle), null);
        KilosDisponibles kilosDisponibles = new KilosDisponibles(tipoAlimento, 1L, 120.0);
        tipoAlimento.setKilosDisponibles(kilosDisponibles);
        detalle.setTipoAlimento(tipoAlimento);

        Mockito.when(tipoAlimentoSaveService.save(tipoAlimento))
                .thenReturn(tipoAlimento);

        Mockito.when(aportacionRepository.save(aportacion)).thenReturn(aportacion);

        assertTrue(aportacionService.cambiarKilosDetalle(detalle, 25).isPresent());
    }

}