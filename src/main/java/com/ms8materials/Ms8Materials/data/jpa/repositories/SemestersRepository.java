package com.ms8materials.Ms8Materials.data.jpa.repositories;

import com.ms8materials.Ms8Materials.data.jpa.entities.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemestersRepository extends JpaRepository<SemesterEntity, Integer> {
}
