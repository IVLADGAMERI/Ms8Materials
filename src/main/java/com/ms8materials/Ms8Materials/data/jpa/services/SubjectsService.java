package com.ms8materials.Ms8Materials.data.jpa.services;

import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.jpa.repositories.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<SubjectEntity> findById(int id) {
        return subjectsRepository.findById(id);
    }
}
