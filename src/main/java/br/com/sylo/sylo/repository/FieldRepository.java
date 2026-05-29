package br.com.sylo.sylo.repository;

import br.com.sylo.sylo.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFarmId(Long farmId);
    List<Field> findByStatus(String status);
}
