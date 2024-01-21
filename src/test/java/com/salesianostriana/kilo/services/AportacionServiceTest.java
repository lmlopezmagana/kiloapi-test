package com.salesianostriana.kilo.services;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.entities.KilosDisponibles;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AportacionServiceTest {

    @Mock
    AportacionRepository aportacionRepository;

    @InjectMocks
    AportacionService aportacionService;

    @Mock
    TipoAlimentoSaveService tipoAlimentoSaveService;

    @Test
    void editAportacionOK() {

        Long idAportacionEdit = 1L;
        Long idLineaEdit = 1L;
        double nuevosKilos = 15;

        Aportacion a1 = Aportacion.builder()
                .id(1L)
                .build();

        KilosDisponibles kd1 = KilosDisponibles.builder()
                .id(1L)
                .cantidadDisponible(20.0)
                .build();

        TipoAlimento ta1 = TipoAlimento.builder()
                .id(1L)
                .nombre("Fruta")
                .kilosDisponibles(kd1)
                .detalleAportaciones(new ArrayList<>())
                .build();

        DetalleAportacion da1 = DetalleAportacion.builder()
                .cantidadKg(5.0)
                .tipoAlimento(ta1)
                .build();

        List<DetalleAportacion> daList = new ArrayList<>();

        daList.add(da1);

        ta1.setDetalleAportaciones(daList);
        a1.setDetalleAportaciones(daList);

        //Esto te dice que cuando busques por id, te devuelva un optional de la aportación
        //que tenga ese id asignada.
        Mockito.when(aportacionRepository.findById(a1.getId())).thenReturn(Optional.of(a1));

        Optional<Aportacion> resultadoEsperado = aportacionService.editAportacion(
                idAportacionEdit, idLineaEdit, nuevosKilos);

        assertEquals(nuevosKilos, resultadoEsperado.get().getDetalleAportaciones().get(0).getCantidadKg());
    }

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