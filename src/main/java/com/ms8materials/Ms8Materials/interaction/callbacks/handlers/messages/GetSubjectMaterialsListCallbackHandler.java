package com.ms8materials.Ms8Materials.interaction.callbacks.handlers.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms8materials.Ms8Materials.data.jpa.SubjectDataType;
import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectEntity;
import com.ms8materials.Ms8Materials.data.jpa.services.SubjectsDataService;
import com.ms8materials.Ms8Materials.data.jpa.services.SubjectsService;
import com.ms8materials.Ms8Materials.interaction.data.*;
import com.ms8materials.Ms8Materials.interaction.essentials.InlineKeyboardButtonData;
import com.ms8materials.Ms8Materials.interaction.essentials.KeyboardsFactory;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import com.ms8materials.Ms8Materials.interaction.callbacks.handlers.ResponseBasedEditingCallbackHandler;
import com.ms8materials.Ms8Materials.interaction.messages.MessageHandlerType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
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
        CallbackData callbackData = (CallbackData) payload;
        SubjectMaterialsData subjectMaterialsData = objectMapper.readValue(callbackData.getD(), SubjectMaterialsData.class) ;
        int subjectId = subjectMaterialsData.getId();
        int pageIndex = subjectMaterialsData.getP();
        Sort.Direction sortDirection;
        SubjectDataType subjectDataType;
        CallbackType callbackType = CallbackType.getByName(callbackData.getT());
        MessagesConstants.INLINE_BUTTONS_TEXT sortDirectionButtonText;
        CallbackType sortDirectionButtonCallbackType;
        sortDirection = switch (callbackType) {
            case CallbackType.GET_SUBJECT_FILES_LIST_ASCENDING -> {
                subjectDataType = SubjectDataType.FILE;
                sortDirectionButtonText = MessagesConstants.INLINE_BUTTONS_TEXT.SORT_DIRECTION_ASC;
                sortDirectionButtonCallbackType = CallbackType.GET_SUBJECT_FILES_LIST_DESCENDING;
                yield Sort.Direction.ASC;
            }
            case CallbackType.GET_SUBJECT_PHOTOS_LIST_ASCENDING -> {
                subjectDataType = SubjectDataType.PHOTO;
                sortDirectionButtonText = MessagesConstants.INLINE_BUTTONS_TEXT.SORT_DIRECTION_ASC;
                sortDirectionButtonCallbackType = CallbackType.GET_SUBJECT_PHOTOS_LIST_DESCENDING;
                yield Sort.Direction.ASC;
            }
            case CallbackType.GET_SUBJECT_PHOTOS_LIST_DESCENDING -> {
                subjectDataType = SubjectDataType.PHOTO;
                sortDirectionButtonText = MessagesConstants.INLINE_BUTTONS_TEXT.SORT_DIRECTION_DESC;
                sortDirectionButtonCallbackType = CallbackType.GET_SUBJECT_PHOTOS_LIST_ASCENDING;
                yield Sort.Direction.DESC;
            }
            case null -> {
                throw new IllegalArgumentException("Callback type not found.");
            }
            default -> {
                subjectDataType = SubjectDataType.FILE;
                sortDirectionButtonText = MessagesConstants.INLINE_BUTTONS_TEXT.SORT_DIRECTION_DESC;
                sortDirectionButtonCallbackType = CallbackType.GET_SUBJECT_FILES_LIST_ASCENDING;
                yield Sort.Direction.DESC;
            }
        };
        Optional<SubjectEntity> subjectEntityOptional = subjectsService.findById(subjectId);
        if (subjectEntityOptional.isEmpty()) {
            throw new NullPointerException("Предмет с таким id не найден!");
        }
        Page<SubjectDataEntity> subjectDataEntityPage =
                subjectsDataService.findAllBySubjectIdAndType(subjectId, subjectDataType.name(),
                        pageIndex, 10, sortDirection);
        InlineKeyboardMarkup inlineKeyboardMarkup;
        List<InlineKeyboardButtonData> inlineKeyboardButtonDataList = new ArrayList<>();
        int semesterId = subjectEntityOptional.get().getSemesterEntity().getId();
        InlineKeyboardButtonData sortDirectionButtonData = new InlineKeyboardButtonData(
                sortDirectionButtonText.getValue(),
                new CallbackData(
                        sortDirectionButtonCallbackType.getName(),
                        objectMapper.writeValueAsString(
                                new SubjectMaterialsData(
                                        subjectId,
                                        pageIndex
                                )
                        ),
                        messageId
                )
        );
        InlineKeyboardButtonData quitButtonData = new InlineKeyboardButtonData(
                MessagesConstants.INLINE_BUTTONS_TEXT.QUIT.getValue(),
                new CallbackData(
                        CallbackType.GET_SUBJECT_MATERIALS_TYPES_LIST.getName(),
                        objectMapper.writeValueAsString(
                                new SubjectIdAndSemesterIdData(
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
                        callbackType.getName(),
                        objectMapper.writeValueAsString(
                                new SubjectMaterialsData(
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
                        callbackType.getName(),
                        objectMapper.writeValueAsString(
                                new SubjectMaterialsData(
                                        subjectId,
                                        pageIndex + 1
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
                                new SubjectIdData(
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
            inlineKeyboardButtonDataList.add(sortDirectionButtonData);
            inlineKeyboardButtonDataList.add(findButtonData);
            inlineKeyboardMarkup = KeyboardsFactory.generateInlineKeyboard(
                    inlineKeyboardButtonDataList, 2
            );
            log.info(inlineKeyboardMarkup.toString());
            for (SubjectDataEntity item : subjectDataEntityList) {
                log.info(item.getUploadedDate().toString());
                stringBuilder.append(String.format(
                        MessagesConstants.ANSWERS.SUBJECT_FILES_LIST_ITEM_MARKUP.getValue(),
                        item.getId(), item.getName(), item.getAuthorUsername()));
            }
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
            stringBuilder.append(MessagesConstants.ANSWERS.SUBJECT_FILES_LIST_FOOTER.getValue());
            response.setMessageHandlerType(MessageHandlerType.GET_SUBJECT_MATERIALS);
            response.setPayload(new SubjectDataTypeData(subjectDataType.getCallbackDataValue()));
        }
        editMessageText.setText(stringBuilder.toString());

        return editMessageText;
    }
}
