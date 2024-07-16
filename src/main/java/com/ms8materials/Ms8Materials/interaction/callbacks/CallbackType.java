package com.ms8materials.Ms8Materials.interaction.callbacks;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum CallbackType {
    GET_SUBJECTS_LIST("GSL"),
    GET_SUBJECT_FILES_LIST("GSFL"),
    GET_SUBJECT_PHOTOS_LIST("GSPL"),
    GET_SUBJECT_NOTES_LIST("GSNL"),
    GET_SUBJECT_MATERIALS_LIST("GSML"),
    GET_SUBJECT_NOTE("GSN"),

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
