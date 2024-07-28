package com.ms8materials.Ms8Materials.data.jpa;

import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum SubjectDataType {
    FILE('F'),
    PHOTO('P');
    public static SubjectDataType getByCallbackDataValue(char callbackDataValue) {
        for (SubjectDataType item : SubjectDataType.values()) {
            if (Objects.equals(item.getCallbackDataValue(), callbackDataValue)) return item;
        }
        return null;
    }
    private final char callbackDataValue;
}
