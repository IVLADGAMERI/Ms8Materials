package com.ms8materials.Ms8Materials.data.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subjects_data")
public class SubjectDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String name;
    @Column(name = "file_relative_path", nullable = false)
    private String fileRelativePath;
    @ManyToOne
    private SubjectEntity subject;
}
