package com.deep.clean.app.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "SKU no puede estar vacío")
    @Column(unique = true, nullable = false)
    private String sku;

    @NotNull(message = "El nombre no puede ser nulo")
    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "category_id")
    private Integer categoryId;

    @PositiveOrZero(message = "El stock actual no puede ser negativo")
    @Column(name = "current_stock")
    private Integer currentStock;

    @PositiveOrZero(message = "El umbral de stock mínimo no puede ser negativo")
    @Column(name = "min_stock_threshold")
    private Integer minStockThreshold;

    @NotNull(message = "El estado no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
