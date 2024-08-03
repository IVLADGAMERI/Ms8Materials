package com.ms8materials.Ms8Materials.interaction.callbacks;

import com.ms8materials.Ms8Materials.interaction.data.SemesterIdData;
import com.ms8materials.Ms8Materials.interaction.data.SubjectIdData;
import com.ms8materials.Ms8Materials.interaction.data.SubjectMaterialsData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum CallbackType {
    GET_SUBJECTS_LIST("SL", SemesterIdData.class),
    GET_SUBJECT_FILES_LIST_DESCENDING("SFLD", SubjectMaterialsData.class),
    GET_SUBJECT_FILES_LIST_ASCENDING("SFLA", SubjectMaterialsData.class),
    GET_SUBJECT_PHOTOS_LIST_DESCENDING("SPLD", SubjectMaterialsData.class),
    GET_SUBJECT_PHOTOS_LIST_ASCENDING("SPLA", SubjectMaterialsData.class),
    GET_SUBJECT_NOTES_LIST("SNL", Object.class),
    GET_SUBJECT_MATERIALS_TYPES_LIST("SMTL", SubjectIdData.class),
    GET_SUBJECT_NOTE("SN", Object.class),
    FIND_SUBJECT_FILE("FSF", SubjectIdData.class),

    GET_SUBJECT_FILE("SF", SubjectIdData.class),
    GET_SUBJECT_PHOTO("SP", SubjectIdData.class);

    public static CallbackType getByName(String name) {
        for (CallbackType item : CallbackType.values()) {
            if (Objects.equals(item.getName(), name)) return item;
        }
        return null;
    }

    private final String name;
    private final Class dataClass;
}
