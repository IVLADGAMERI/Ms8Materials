package com.ms8materials.Ms8Materials.data.jpa.services;

import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.jpa.repositories.SubjectDataRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class SubjectsDataService {
    @Autowired
    private SubjectDataRepository subjectDataRepository;
    public Page<SubjectDataEntity> findAllBySubjectIdAndType(int subjectId, String type, int pageNumber, int pageSize,
                                                             Sort.Direction sortDirection) {
        return subjectDataRepository.findAllBySubjectIdAndType(subjectId,
                type,
                PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, "uploadedDate")));
    }
    @Transactional
    public Page<SubjectDataEntity> findAllByName(String name, int subId, String type, int pageNumber, int pageSize) {
        return subjectDataRepository.findAllBySubjectIdAndTypeAndNameContains(subId, type, name,
                PageRequest.of(pageNumber, pageSize));
    }
    public List<SubjectDataEntity> findAllByIdsAndType(List<Integer> ids, String type) {
        return subjectDataRepository.findAllByIdAndType(ids, type);
    }

}
