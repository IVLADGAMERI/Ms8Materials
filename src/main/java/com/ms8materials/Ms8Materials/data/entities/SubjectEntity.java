package com.ms8materials.Ms8Materials.data.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "subjects")
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, name = "teachers_data")
    private String teachersData;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private SemesterEntity semesterEntity;
    @OneToMany(mappedBy = "subject", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SubjectDataEntity> subjectDataList;
}
