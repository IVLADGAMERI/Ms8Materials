package com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8materials.Ms8Materials.data.services.SubjectsDataService;
import com.ms8materials.Ms8Materials.essentials.InlineKeyboardButtonData;
import com.ms8materials.Ms8Materials.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.events.EditMessageEvent;
import com.ms8materials.Ms8Materials.events.MessageSentEvent;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import com.ms8materials.Ms8Materials.interaction.callbacks.callbacksHandlers.EditingCallbackHandlerImpl;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.CallbackData;
import com.ms8materials.Ms8Materials.interaction.callbacks.data.SubjectIdAndMessageIdCallbackData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
@Slf4j
public class GetSubjectMaterialsListCallbackHandler extends EditingCallbackHandlerImpl {
    @Autowired
    private SubjectsDataService subjectsDataService;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public EditMessageText editMessage(int messageId, long chatId, Object payload) {
        StringBuilder stringBuilder = new StringBuilder();
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(messageId);
        editMessageText.setChatId(chatId);
        try {
            CallbackData castedPayload = (CallbackData) payload;
            SubjectIdAndMessageIdCallbackData callbackData = objectMapper.readValue(castedPayload.getData(),
                    SubjectIdAndMessageIdCallbackData.class);
            List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = List.of(
                    new InlineKeyboardButtonData(
                            MessagesConstants.INLINE_BUTTONS_TEXT.FILES.getValue(),
                            new CallbackData(
                                    CallbackType.GET_SUBJECT_FILES_LIST.getName(),
                                    objectMapper.writeValueAsString(callbackData)
                            )
                    ),
                    new InlineKeyboardButtonData(
                            MessagesConstants.INLINE_BUTTONS_TEXT.PHOTOS.getValue(),
                            new CallbackData(
                                    CallbackType.GET_SUBJECT_PHOTOS_LIST.getName(),
                                    objectMapper.writeValueAsString(callbackData)
                            )
                    )
            );
            InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(
                    inlineKeyboardButtonDataList,
                    2);
            stringBuilder.append(MessagesConstants.ANSWERS.SUBJECT_MATERIALS_LIST_HAT.getValue());
            editMessageText.setText(stringBuilder.toString());
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
//            int subjectId = callbackData.getSubId();
//            int pageIndex = callbackData.getPg();
//            List<SubjectDataEntity> subjectDataEntityList =
//                    subjectsDataService.findAllBySubjectId(subjectId,
//                            pageIndex, 10);
//            InlineKeyboardMarkup inlineKeyboardMarkup;
//            List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = new ArrayList<>();
//            int inlineKeyboardButtonDataListIndex = 0;
//            if (subjectDataEntityList.isEmpty()) {
//                stringBuilder.append("Нет данных.");
//                if (pageIndex - 1 >= 0) {
//                    inlineKeyboardButtonDataList.add(
//                            inlineKeyboardButtonDataListIndex,
//                            new InlineKeyboardButtonData(
//                                    MessagesConstants.ANSWERS.PREVIOUS_PAGE.getValue(),
//                                    new CallbackData(
//                                            CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
//                                            objectMapper.writeValueAsString(
//                                                    new GetSubjectMaterialsListCallbackData(
//                                                            subjectId,
//                                                            pageIndex - 1,
//                                                            messageId)
//                                            )
//                                    )
//                            )
//                    );
//                    inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(inlineKeyboardButtonDataList, 1);
//                    editMessageText.setReplyMarkup(inlineKeyboardMarkup);
//                }
//            } else {
//                SubjectDataEntity firstSubjectDataEntity = subjectDataEntityList.getFirst();
//                SubjectEntity subjectEntity = firstSubjectDataEntity.getSubject();
//                stringBuilder.append(String.format(MessagesConstants.ANSWERS.SUBJECT_MATERIALS_LIST_HAT.getValue(),
//                                subjectEntity.getName(), pageIndex + 1))
//                        .append("\n");
//                if (pageIndex - 1 >= 0) {
//                    inlineKeyboardButtonDataList.add(
//                            inlineKeyboardButtonDataListIndex,
//                            new InlineKeyboardButtonData(
//                                    MessagesConstants.ANSWERS.PREVIOUS_PAGE.getValue(),
//                                    new CallbackData(
//                                            CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
//                                            objectMapper.writeValueAsString(
//                                                    new GetSubjectMaterialsListCallbackData(
//                                                            subjectId,
//                                                            pageIndex - 1,
//                                                            messageId)
//                                            )
//                                    )
//                            )
//                    );
//                    inlineKeyboardButtonDataListIndex += 1;
//                }
//                inlineKeyboardButtonDataList.add(
//                        inlineKeyboardButtonDataListIndex,
//                        new InlineKeyboardButtonData(
//                                MessagesConstants.ANSWERS.NEXT_PAGE.getValue(),
//                                new CallbackData(
//                                        CallbackType.GET_SUBJECT_MATERIALS_LIST.getName(),
//                                        objectMapper.writeValueAsString(
//                                                new GetSubjectMaterialsListCallbackData(
//                                                        subjectId,
//                                                        pageIndex + 1,
//                                                        messageId)
//                                        )
//                                )
//                        )
//                );
//                inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(
//                        inlineKeyboardButtonDataList, inlineKeyboardButtonDataList.size()
//                );
//                log.info(inlineKeyboardMarkup.toString());
//                for (SubjectDataEntity item : subjectDataEntityList) {
//                    stringBuilder.append(item.getId())
//                            .append(") ")
//                            .append(item.getName())
//                            .append("\n")
//                            .append(item.getType())
//                            .append("\n");
//                }
//                editMessageText.setReplyMarkup(inlineKeyboardMarkup);
//            }
//            editMessageText.setText(stringBuilder.toString());
        } catch (Exception e) {
            editMessageText.setText("Failed!\nException: " + e.getMessage());
            e.printStackTrace();
        }
        return editMessageText;
    }

    @Override
    public void handleMessageSentEvent(MessageSentEvent event) {
        if (event.getSource() == this) {
            EditMessageText editMessageText = editMessage(event.getMessageId(), event.getChatId(), event.getPayload());
            applicationEventPublisher.publishEvent(new EditMessageEvent(
                    this, editMessageText
            ));
        }
    }
}
