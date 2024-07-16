package com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages;

import com.ms8materials.Ms8Materials.data.SubjectDataType;
import com.ms8materials.Ms8Materials.data.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.services.SubjectsDataService;
import com.ms8materials.Ms8Materials.data.services.SubjectsService;
import com.ms8materials.Ms8Materials.essentials.InlineKeyboardButtonData;
import com.ms8materials.Ms8Materials.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.EventBasedEditingCallbackHandlerImpl;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.SubjectIdAndPageNumberCallbackData;
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
public class GetSubjectFilesListCallbackHandler extends ResponseBasedEditingCallbackHandler {

    @Autowired
    private SubjectsDataService subjectsDataService;
    @Autowired
    private SubjectsService subjectsService;

    @Override
    public EditMessageText editMessage(int messageId, long chatId, Object payload, Response response) {
        StringBuilder stringBuilder = new StringBuilder();
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId);
        try {
            CallbackData castedPayload = (CallbackData) payload;
            SubjectIdAndPageNumberCallbackData callbackData = objectMapper.readValue(castedPayload.getData(),
                    SubjectIdAndPageNumberCallbackData.class);
            int subjectId = callbackData.getSubId();
            int pageIndex = callbackData.getPg();
            Optional<SubjectEntity> subjectEntityOptional = subjectsService.findById(subjectId);
            if (subjectEntityOptional.isEmpty()) {
                throw new NullPointerException("Предмет с таким id не найден!");
            }
            Page<SubjectDataEntity> subjectDataEntityPage =
                    subjectsDataService.findAllBySubjectIdAndType(subjectId, SubjectDataType.FILE.getDbStringValue(),
                            pageIndex, 10);
            InlineKeyboardMarkup inlineKeyboardMarkup;
            List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = new ArrayList<>();
            int inlineKeyboardButtonDataListIndex = 0;
            int semesterId = subjectEntityOptional.get().getSemesterEntity().getId();
            InlineKeyboardButtonData quitButtonData = new InlineKeyboardButtonData(
                    MessagesConstants.INLINE_BUTTONS_TEXT.QUIT.getValue(),
                    new CallbackData(
                            CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
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
                            CallbackType.GET_SUBJECT_FILES_LIST.getName(),
                            objectMapper.writeValueAsString(
                                    new SubjectIdAndPageNumberCallbackData(
                                            subjectId,
                                            pageIndex - 1
                                    )
                            ),
                            messageId
                    )
            );
            InlineKeyboardButtonData nextPageButtonData = new InlineKeyboardButtonData(
                    MessagesConstants.INLINE_BUTTONS_TEXT.NEXT_PAGE.getValue(),
                    new CallbackData(
                            CallbackType.GET_SUBJECT_FILES_LIST.getName(),
                            objectMapper.writeValueAsString(
                                    new SubjectIdAndPageNumberCallbackData(
                                            subjectId,
                                            pageIndex + 1
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
                        subjectEntity.getName(), pageIndex + 1));
                log.info(String.valueOf(subjectDataEntityPage.getTotalPages()));
                if ((subjectDataEntityPage.getTotalPages() - 1) == pageIndex) {
                    inlineKeyboardButtonDataList.add(prevPageButtonData);
                } else if (pageIndex - 1 >= 0){
                    inlineKeyboardButtonDataList.add(prevPageButtonData);
                    inlineKeyboardButtonDataList.add(nextPageButtonData);
                } else {
                    inlineKeyboardButtonDataList.add(nextPageButtonData);
                }
                inlineKeyboardButtonDataList.add(quitButtonData);
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
        } catch (Exception e) {
            editMessageText.setText("Failed!\nException: " + e.getMessage());
            e.printStackTrace();
        }

        return editMessageText;
    }
}
