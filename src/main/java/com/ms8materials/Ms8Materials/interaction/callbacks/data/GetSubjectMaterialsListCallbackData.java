package com.ms8materials.Ms8Materials.interaction.callbacks.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSubjectMaterialsListCallbackData {
    private int subId;
    private int pg;
    private int mId;
}
