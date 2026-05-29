package br.com.sylo.sylo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "crop_types")
public class CropType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "CLOB")
    private String description;

    @Column(name = "ideal_min_soil_moisture", precision = 5, scale = 2)
    private BigDecimal idealMinSoilMoisture;

    @Column(name = "ideal_max_soil_moisture", precision = 5, scale = 2)
    private BigDecimal idealMaxSoilMoisture;

    @Column(name = "ideal_min_temperature", precision = 5, scale = 2)
    private BigDecimal idealMinTemperature;

    @Column(name = "ideal_max_temperature", precision = 5, scale = 2)
    private BigDecimal idealMaxTemperature;

    @Column(name = "ideal_min_ndvi", precision = 5, scale = 2)
    private BigDecimal idealMinNdvi;

    @Column(name = "ideal_max_ndvi", precision = 5, scale = 2)
    private BigDecimal idealMaxNdvi;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getIdealMinSoilMoisture() { return idealMinSoilMoisture; }
    public void setIdealMinSoilMoisture(BigDecimal idealMinSoilMoisture) { this.idealMinSoilMoisture = idealMinSoilMoisture; }

    public BigDecimal getIdealMaxSoilMoisture() { return idealMaxSoilMoisture; }
    public void setIdealMaxSoilMoisture(BigDecimal idealMaxSoilMoisture) { this.idealMaxSoilMoisture = idealMaxSoilMoisture; }

    public BigDecimal getIdealMinTemperature() { return idealMinTemperature; }
    public void setIdealMinTemperature(BigDecimal idealMinTemperature) { this.idealMinTemperature = idealMinTemperature; }

    public BigDecimal getIdealMaxTemperature() { return idealMaxTemperature; }
    public void setIdealMaxTemperature(BigDecimal idealMaxTemperature) { this.idealMaxTemperature = idealMaxTemperature; }

    public BigDecimal getIdealMinNdvi() { return idealMinNdvi; }
    public void setIdealMinNdvi(BigDecimal idealMinNdvi) { this.idealMinNdvi = idealMinNdvi; }

    public BigDecimal getIdealMaxNdvi() { return idealMaxNdvi; }
    public void setIdealMaxNdvi(BigDecimal idealMaxNdvi) { this.idealMaxNdvi = idealMaxNdvi; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
