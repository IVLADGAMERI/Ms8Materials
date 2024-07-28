package com.ms8materials.Ms8Materials.interaction.essentials;

import com.ms8materials.Ms8Materials.interaction.data.CallbackData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InlineKeyboardButtonData {
    private String text;
    private CallbackData callbackData;
}
