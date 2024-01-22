package com.salesianostriana.kilo.repositories;

import com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO;
import com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO;
import com.salesianostriana.kilo.entities.Aportacion;
import com.salesianostriana.kilo.entities.DetalleAportacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AportacionRepository extends JpaRepository<Aportacion, Long> {
    @Query("""
            SELECT CASE 
                WHEN da.tipoAlimento.id = :id THEN 1 
                ELSE 0 END
            FROM DetalleAportacion da
            """)
    List<Integer> tipoAlimentoInAportacion(@Param("id") Long id);

    @Query("""
            SELECT new com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO(a.id, a.fecha, c.nombre, SUM(da.cantidadKg))
            FROM Aportacion a LEFT JOIN Clase c ON (a.clase.id = c.id)
                              JOIN DetalleAportacion da ON (da.aportacion.id = a.id)
            GROUP BY a.id
            """)
    List<AportacionesReponseDTO> getAllAportaciones();

    @Query("""
            SELECT new com.salesianostriana.kilo.dtos.aportaciones.AportacionesReponseDTO(a.id, a.fecha)
            FROM Aportacion a
            WHERE a.clase.id = :id
            """)
    List<AportacionesReponseDTO> getAllAportacionesOfClass(@Param("id") Long id);


    @Query("""
            SELECT new com.salesianostriana.kilo.dtos.detalles_aportacion.DetallesAportacionResponseDTO(da.aportacion.id, da.tipoAlimento.nombre, da.cantidadKg)
            FROM DetalleAportacion da
            WHERE da.aportacion.clase.id = :id
            """)
    List<DetallesAportacionResponseDTO> getAllDetalleAportacion(@Param("id") Long id);

    @Query("""            
            SELECT d
            FROM DetalleAportacion d JOIN TipoAlimento t ON d.tipoAlimento.id = t.id JOIN KilosDisponibles k ON t.id = k.tipoAlimento.id
            WHERE d.cantidadKg <= k.cantidadDisponible
            """)
    List<DetalleAportacion> findDetallesBorrables();
}
