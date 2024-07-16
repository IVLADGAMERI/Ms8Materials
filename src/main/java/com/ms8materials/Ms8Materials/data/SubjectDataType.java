package com.ms8materials.Ms8Materials.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubjectDataType {
    FILE("FILE"),
    PHOTO("PHOTO");
    private final String dbStringValue;
}
