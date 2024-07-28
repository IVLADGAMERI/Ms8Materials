package com.ms8materials.Ms8Materials.data.app.containers.chat;

import com.ms8materials.Ms8Materials.data.app.ConversationContextData;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConversationContextContainer {
    @Getter
    private static final Map<Long, ConversationContextData> data = new HashMap<>();
    public static void put(long chatId, ConversationContextData item) {
        data.put(chatId, item);
    }
    public static ConversationContextData get(long chatId) {
        return data.get(chatId);
    }
}
