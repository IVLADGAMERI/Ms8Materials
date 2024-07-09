package com.ms8materials.Ms8Materials.data.services;

import com.ms8materials.Ms8Materials.data.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.repositories.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectsService {
    @Autowired
    private SubjectsRepository subjectsRepository;

    public List<SubjectEntity> getAll() {
        return subjectsRepository.findAll();
    }
    public List<SubjectEntity> findAllBySemesterEntityId(int semesterEntityId) {
        return subjectsRepository.findAllBySemesterEntityId(semesterEntityId);
    }
}
