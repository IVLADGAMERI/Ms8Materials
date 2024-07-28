package com.ms8materials.Ms8Materials.interaction.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallbackData {
    private String t;
    private String d;
    private int mI;
}
