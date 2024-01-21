package com.salesianostriana.kilo.repositories;
import com.salesianostriana.kilo.dtos.ranking.RankQueryResponseDTO;
import com.salesianostriana.kilo.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {

    @Query(value = """
               SELECT SUM(det.cantidad_kg)
               FROM clase c
               JOIN aportacion a ON c.id = a.clase_id
               JOIN detalle_aportacion det ON a.id = det.aportacion_id
               JOIN tipo_alimento ta ON det.tipo_alimento = ta.id
               WHERE c.id = :idClase
            """, nativeQuery = true)
    Double findKilos(@Param("idClase") Long idCLase);



    @Query("SELECT new com.salesianostriana.kilo.dtos.ranking.RankQueryResponseDTO(a.clase.id, a.clase.nombre, SUM(d.cantidadKg) AS cantidadPorAp) " +
            "FROM Aportacion a JOIN DetalleAportacion d ON a.id = d.aportacion.id GROUP BY a.id")
    public List<RankQueryResponseDTO> findClasesOrderedByRank();



}
