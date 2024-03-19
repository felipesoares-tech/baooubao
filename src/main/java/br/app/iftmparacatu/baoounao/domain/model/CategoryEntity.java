package br.app.iftmparacatu.baoounao.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
@Entity
@Data
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String title;
    @Column
    private boolean active;
    @Column(nullable = false)
    @NonNull
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime finishedAt;

}
