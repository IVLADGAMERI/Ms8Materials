package com.ms8materials.Ms8Materials.interaction.callbacks;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum CallbackType {
    GET_SUBJECTS_LIST("SL"),
    GET_SUBJECT_MATERIALS_LIST("SML"),
    GET_SUBJECT_NOTES_LIST("SNL"),
    GET_SUBJECT_MATERIALS_TYPES_LIST("SMTL"),
    GET_SUBJECT_NOTE("SN"),
    FIND_SUBJECT_FILE("FSF"),

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
