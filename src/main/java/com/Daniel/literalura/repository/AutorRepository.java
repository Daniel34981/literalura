package com.Daniel.literalura.repository;

import com.Daniel.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqual(Integer anio, Integer anio2);

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaFallecimientoIsNull(Integer anio);
}