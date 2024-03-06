package br.app.iftmparacatu.baoounao.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class CycleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 100)
    private String title;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime finishedAt;

}
