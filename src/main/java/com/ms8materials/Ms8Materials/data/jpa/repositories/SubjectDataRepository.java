package com.ms8materials.Ms8Materials.data.jpa.repositories;

import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectDataRepository extends JpaRepository<SubjectDataEntity, Integer> {
    Optional<SubjectDataEntity> findByName(String name);
    Page<SubjectDataEntity> findAllBySubjectIdAndType(int subjectId, String type, Pageable pageable);
    Page<SubjectDataEntity> findAllBySubjectIdAndTypeAndNameContains(int subjectId, String type,
                                                  String name, Pageable pageable);
    @Query("SELECT s FROM SubjectDataEntity s WHERE s.id IN (:ids) AND s.type = :type")
    List<SubjectDataEntity> findAllByIdAndType(@Param("ids") List<Integer> ids, @Param("type") String type);
}
