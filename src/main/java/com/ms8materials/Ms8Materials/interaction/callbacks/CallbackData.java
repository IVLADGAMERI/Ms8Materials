package com.ms8materials.Ms8Materials.interaction.callbacks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallbackData {
    private String type;
    private String data;
}
