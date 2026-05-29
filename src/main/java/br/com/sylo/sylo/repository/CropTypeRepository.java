package br.com.sylo.sylo.repository;

import br.com.sylo.sylo.entity.CropType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropTypeRepository extends JpaRepository<CropType, Long> {
    List<CropType> findByNameContainingIgnoreCase(String name);
}
