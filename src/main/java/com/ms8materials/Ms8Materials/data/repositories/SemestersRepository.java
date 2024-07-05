package com.ms8materials.Ms8Materials.data.repositories;

import com.ms8materials.Ms8Materials.data.entities.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemestersRepository extends JpaRepository<SemesterEntity, Integer> {
}
