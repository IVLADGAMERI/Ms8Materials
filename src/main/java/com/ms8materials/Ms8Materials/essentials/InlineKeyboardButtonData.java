package com.ms8materials.Ms8Materials.essentials;

import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackData;
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
