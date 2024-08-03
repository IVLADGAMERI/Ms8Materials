package com.ms8materials.Ms8Materials.interaction.data;

import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallbackData {
    private String t;
    private String d;
    private int mId;
}
