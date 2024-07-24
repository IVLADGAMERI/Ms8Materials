package com.ms8materials.Ms8Materials;

import com.ms8materials.Ms8Materials.interaction.messages.MessageHandlerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ConversationContextData {
    private MessageHandlerType messageHandlerType;
    private Object payload;
}
