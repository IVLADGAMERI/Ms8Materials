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
    GET_SUBJECTS_LIST("SL"),
    GET_SUBJECT_FILES_LIST_DESCENDING("SFLD"),
    GET_SUBJECT_FILES_LIST_ASCENDING("SFLA"),
    GET_SUBJECT_PHOTOS_LIST_DESCENDING("SPLD"),
    GET_SUBJECT_PHOTOS_LIST_ASCENDING("SPLA"),
    GET_SUBJECT_NOTES_LIST("SNL"),
    GET_SUBJECT_MATERIALS_TYPES_LIST("SMTL"),
    GET_SUBJECT_NOTE("SN"),
    FIND_SUBJECT_FILE("FSF"),
    FIND_SUBJECT_PHOTO("FSP"),

    GET_SUBJECT_FILE("SF"),
    GET_SUBJECT_PHOTO("SP");

    public static CallbackType getByName(String name) {
        for (CallbackType item : CallbackType.values()) {
            if (Objects.equals(item.getName(), name)) return item;
        }
        return null;
    }

    private final String name;
}
