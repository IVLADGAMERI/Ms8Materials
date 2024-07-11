package com.ms8materials.Ms8Materials.data.repositories;

import com.ms8materials.Ms8Materials.data.entities.SubjectDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectDataRepository extends JpaRepository<SubjectDataEntity, Integer> {
    Optional<SubjectDataEntity> findByName(String name);
    Page<SubjectDataEntity> findAllBySubjectId(int subjectId, Pageable pageable);
}
