package br.com.sylo.sylo.repository;

import br.com.sylo.sylo.entity.IotDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IotDeviceRepository extends JpaRepository<IotDevice, Long> {
    List<IotDevice> findByFieldId(Long fieldId);
    List<IotDevice> findByStatus(String status);
    List<IotDevice> findByDeviceType(String deviceType);
}
