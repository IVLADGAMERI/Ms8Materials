package com.ms8materials.Ms8Materials.data.services;

import com.ms8materials.Ms8Materials.data.entities.SemesterEntity;
import com.ms8materials.Ms8Materials.data.repositories.SemestersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemestersService {
    @Autowired
    private SemestersRepository semestersRepository;

    public List<SemesterEntity> findAll() {
        return semestersRepository.findAll();
    }
}
