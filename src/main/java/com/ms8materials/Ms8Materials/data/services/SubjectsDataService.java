package com.ms8materials.Ms8Materials.data.services;

import com.ms8materials.Ms8Materials.data.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.repositories.SubjectDataRepository;
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

    public List<SubjectDataEntity> findAllBySubjectId(int subjectId, int pageNumber, int pageSize) {
        Page<SubjectDataEntity> subjectDataEntityPage = subjectDataRepository.findAllBySubjectId(subjectId,
                PageRequest.of(pageNumber, pageSize));
        return subjectDataEntityPage.get().toList();
    }
}
