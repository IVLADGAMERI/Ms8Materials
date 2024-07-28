package com.ms8materials.Ms8Materials.interaction.callbacks.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectIdAndPageNumberAndTypeCallbackData {
    private int sId;
    private int p;
    private char t;
}
