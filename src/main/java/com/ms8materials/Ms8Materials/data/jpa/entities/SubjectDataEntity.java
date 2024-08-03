package com.ms8materials.Ms8Materials.data.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "subjects_data")
public class SubjectDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "file_relative_path", nullable = false)
    private String fileRelativePath;
    @Column(name = "uploaded_date", nullable = false)
    private Timestamp uploadedDate;
    @Column(name = "author_username", nullable = false)
    private String authorUsername;
    @ManyToOne
    private SubjectEntity subject;
}
