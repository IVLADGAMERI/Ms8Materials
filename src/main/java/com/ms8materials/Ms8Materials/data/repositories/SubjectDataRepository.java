package com.ms8materials.Ms8Materials.data.repositories;

import com.ms8materials.Ms8Materials.data.entities.SubjectDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectDataRepository extends JpaRepository<SubjectDataEntity, Integer> {
    Optional<SubjectDataEntity> findByName(String name);
    List<SubjectDataEntity> findAllBySubjectId(int subjectId);
}
