package com.ms8materials.Ms8Materials.data.services;

import com.ms8materials.Ms8Materials.data.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.repositories.SubjectDataRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubjectsDataService {
    @Autowired
    private SubjectDataRepository subjectDataRepository;
    public Page<SubjectDataEntity> findAllBySubjectIdAndType(int subjectId, String type, int pageNumber, int pageSize) {
        return subjectDataRepository.findAllBySubjectIdAndType(subjectId,
                type,
                PageRequest.of(pageNumber, pageSize));
    }
    @Transactional
    public Page<SubjectDataEntity> findAllByName(String name, int subId, String type, int pageNumber, int pageSize) {
        return subjectDataRepository.findAllBySubjectIdAndTypeAndNameContains(subId, type, name,
                PageRequest.of(pageNumber, pageSize));
    }
    public List<SubjectDataEntity> findAllByIds(List<Integer> ids) {
        return subjectDataRepository.findAllById(ids);
    }

}
