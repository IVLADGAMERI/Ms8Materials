package com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms8materials.Ms8Materials.data.jpa.SubjectDataType;
import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.jpa.services.SubjectsDataService;
import com.ms8materials.Ms8Materials.data.jpa.services.SubjectsService;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.SubjectIdAndPageNumberAndTypeCallbackData;
import com.ms8materials.Ms8Materials.interaction.essentials.InlineKeyboardButtonData;
import com.ms8materials.Ms8Materials.interaction.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.SubjectIdCallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.SubjectIdAndSemesterIdCallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.ResponseBasedEditingCallbackHandler;
import com.ms8materials.Ms8Materials.interaction.messages.MessageHandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class GetSubjectMaterialsListCallbackHandler extends ResponseBasedEditingCallbackHandler {

    @Autowired
    private SubjectsDataService subjectsDataService;
    @Autowired
    private SubjectsService subjectsService;

    @Override
    public EditMessageText editMessage(int messageId, long chatId, Object payload, Response response) throws JsonProcessingException {
        StringBuilder stringBuilder = new StringBuilder();
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId);
        CallbackData castedPayload = (CallbackData) payload;
        SubjectIdAndPageNumberAndTypeCallbackData callbackData = objectMapper.readValue(castedPayload.getD(),
                SubjectIdAndPageNumberAndTypeCallbackData.class);
        int subjectId = callbackData.getSId();
        int pageIndex = callbackData.getP();
        SubjectDataType subjectDataType = SubjectDataType.getByCallbackDataValue(callbackData.getT());
        Optional<SubjectEntity> subjectEntityOptional = subjectsService.findById(subjectId);
        if (subjectEntityOptional.isEmpty()) {
            throw new NullPointerException("Предмет с таким id не найден!");
        }
        Page<SubjectDataEntity> subjectDataEntityPage =
                subjectsDataService.findAllBySubjectIdAndType(subjectId, subjectDataType.name(),
                        pageIndex, 10);
        InlineKeyboardMarkup inlineKeyboardMarkup;
        List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = new ArrayList<>();
        int semesterId = subjectEntityOptional.get().getSemesterEntity().getId();
        InlineKeyboardButtonData quitButtonData = new InlineKeyboardButtonData(
                MessagesConstants.INLINE_BUTTONS_TEXT.QUIT.getValue(),
                new CallbackData(
                        CallbackType.GET_SUBJECT_MATERIALS_TYPES_LIST.getName(),
                        objectMapper.writeValueAsString(
                                new SubjectIdAndSemesterIdCallbackData(
                                        subjectId,
                                        semesterId
                                )
                        ),
                        messageId
                )
        );
        InlineKeyboardButtonData prevPageButtonData = new InlineKeyboardButtonData(
                MessagesConstants.INLINE_BUTTONS_TEXT.PREVIOUS_PAGE.getValue(),
                new CallbackData(
                        CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
                        objectMapper.writeValueAsString(
                                new SubjectIdAndPageNumberAndTypeCallbackData(
                                        subjectId,
                                        pageIndex - 1,
                                        SubjectDataType.FILE.getCallbackDataValue()
                                )
                        ),
                        messageId
                )
        );
        InlineKeyboardButtonData nextPageButtonData = new InlineKeyboardButtonData(
                MessagesConstants.INLINE_BUTTONS_TEXT.NEXT_PAGE.getValue(),
                new CallbackData(
                        CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
                        objectMapper.writeValueAsString(
                                new SubjectIdAndPageNumberAndTypeCallbackData(
                                        subjectId,
                                        pageIndex + 1,
                                        SubjectDataType.FILE.getCallbackDataValue()
                                )
                        ),
                        messageId
                )
        );
        InlineKeyboardButtonData findButtonData = new InlineKeyboardButtonData(
                MessagesConstants.INLINE_BUTTONS_TEXT.FIND.getValue(),
                new CallbackData(
                        CallbackType.FIND_SUBJECT_FILE.getName(),
                        objectMapper.writeValueAsString(
                                new SubjectIdCallbackData(
                                        subjectId
                                )
                        ),
                        messageId
                )
        );
        List<SubjectDataEntity> subjectDataEntityList = subjectDataEntityPage.stream().toList();
        if (subjectDataEntityList.isEmpty()) {
            stringBuilder.append("Нет данных.");
            if (pageIndex == 0) {
                inlineKeyboardButtonDataList.add(quitButtonData);
            } else {
                inlineKeyboardButtonDataList.add(prevPageButtonData);
            }
            inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(inlineKeyboardButtonDataList, 1);
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        } else {
            SubjectDataEntity firstSubjectDataEntity = subjectDataEntityList.getFirst();
            SubjectEntity subjectEntity = firstSubjectDataEntity.getSubject();
            stringBuilder.append(String.format(
                    MessagesConstants.ANSWERS.SUBJECT_FILES_LIST_HAT.getValue(),
                    subjectEntity.getName(), pageIndex + 1, subjectDataEntityPage.getTotalPages()));
            log.info(String.valueOf(subjectDataEntityPage.getTotalPages()));
            int totalPages = subjectDataEntityPage.getTotalPages();
            if (totalPages - 1 == pageIndex) {
                if (pageIndex != 0) {
                    inlineKeyboardButtonDataList.add(prevPageButtonData);
                }
            } else if (pageIndex - 1 >= 0) {
                inlineKeyboardButtonDataList.add(prevPageButtonData);
                inlineKeyboardButtonDataList.add(nextPageButtonData);
            } else {
                inlineKeyboardButtonDataList.add(nextPageButtonData);
            }
            inlineKeyboardButtonDataList.add(quitButtonData);
            inlineKeyboardButtonDataList.add(findButtonData);
            inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(
                    inlineKeyboardButtonDataList, 2
            );
            log.info(inlineKeyboardMarkup.toString());
            for (SubjectDataEntity item : subjectDataEntityList) {
                stringBuilder.append(String.format(
                        MessagesConstants.ANSWERS.SUBJECT_FILES_LIST_ITEM_MARKUP.getValue(),
                        item.getId(), item.getName()));
            }
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
            stringBuilder.append(MessagesConstants.ANSWERS.SUBJECT_FILES_LIST_FOOTER.getValue());
            response.setMessageHandlerType(MessageHandlerType.GET_SUBJECT_MATERIALS);
        }
        editMessageText.setText(stringBuilder.toString());

        return editMessageText;
    }
}
