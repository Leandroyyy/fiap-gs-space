package br.com.sylo.sylo.repository;

import br.com.sylo.sylo.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findByFieldId(Long fieldId);
    List<Alert> findByStatus(String status);
    List<Alert> findBySeverity(String severity);
}
