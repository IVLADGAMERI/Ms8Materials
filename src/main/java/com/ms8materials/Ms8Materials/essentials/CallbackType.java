package com.ms8materials.Ms8Materials.essentials;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CallbackType {
    GET_SUBJECTS_LIST("GET_SUBJECTS_LIST"),
    GET_SUBJECT_FILES_LIST("GET_SUBJECT_FILES_LIST"),
    GET_SUBJECT_PHOTOS_LIST("GET_SUBJECT_PHOTOS_LIST"),
    GET_SUBJECT_NOTES_LIST("GET_SUBJECT_NOTES_LIST"),
    GET_SUBJECT_MATERIALS("GET_SUBJECT_MATERIALS"),

    GET_SUBJECT_FILE("GET_SUBJECT_FILE"),
    GET_SUBJECT_PHOTO("GET_SUBJECT_PHOTO"),
    GET_SUBJECT_NOTE("GET_SUBJECT_NOTE");
    private final String name;
}
