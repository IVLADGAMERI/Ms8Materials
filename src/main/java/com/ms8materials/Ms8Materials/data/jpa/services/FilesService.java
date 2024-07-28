package com.ms8materials.Ms8Materials.data.jpa.services;

import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.jpa.repositories.SubjectDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FilesService {
    @Value("${files.directory}")
    private String filesPath;
    @Autowired
    private SubjectDataRepository subjectDataRepository;

    public Optional<File> getFile(String name) {
        Optional<SubjectDataEntity> subjectDataEntityOptional = subjectDataRepository.findByName(name);
        if (subjectDataEntityOptional.isEmpty()) {
            return Optional.empty();

        }
        SubjectDataEntity subjectDataEntity = subjectDataEntityOptional.get();
        Path path = Paths.get(filesPath, subjectDataEntity.getFileRelativePath());
        if (!Files.exists(path)) {
            return Optional.empty();
        }
        return Optional.of(path.toFile());
    }
}
