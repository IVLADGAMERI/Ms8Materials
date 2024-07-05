package com.ms8materials.Ms8Materials.data.repositories;

import com.ms8materials.Ms8Materials.data.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectsRepository extends JpaRepository<SubjectEntity, Integer> {
    List<SubjectEntity> findAllBySemesterEntityId(int id);
}
