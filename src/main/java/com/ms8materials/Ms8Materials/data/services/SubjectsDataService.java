package com.ms8materials.Ms8Materials.data.services;

import com.ms8materials.Ms8Materials.data.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.repositories.SubjectDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectsDataService {
    @Autowired
    private SubjectDataRepository subjectDataRepository;

    public List<SubjectDataEntity> findAllBySubjectId(int subjectId) {
        return subjectDataRepository.findAllBySubjectId(subjectId);
    }
}
