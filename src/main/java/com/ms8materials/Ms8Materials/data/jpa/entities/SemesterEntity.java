package com.ms8materials.Ms8Materials.data.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "semesters")
public class SemesterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int number;
}
