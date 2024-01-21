package com.salesianostriana.kilo.services;

import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import com.salesianostriana.kilo.entities.KilosDisponibles;
import com.salesianostriana.kilo.entities.TipoAlimento;
import com.salesianostriana.kilo.repositories.AportacionRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AportacionServiceTest {

    @Mock
    AportacionRepository aportacionRepository;

    @InjectMocks
    AportacionService aportacionService;

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
}