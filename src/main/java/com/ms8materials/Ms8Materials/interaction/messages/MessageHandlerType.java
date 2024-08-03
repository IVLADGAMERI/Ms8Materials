package com.ms8materials.Ms8Materials.interaction.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageHandlerType {
    GET_SUBJECT_MATERIALS("GSM"),
    FIND_SUBJECT_DATA("FSD");
    private String name;
}
