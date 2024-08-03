package com.ms8materials.Ms8Materials.interaction.messages.handlers;

import com.ms8materials.Ms8Materials.data.jpa.SubjectDataType;
import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.jpa.services.SubjectsDataService;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.data.SubjectIdData;
import com.ms8materials.Ms8Materials.interaction.messages.MessageHandlerType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
@Slf4j
public class FindSubjectFilesMessageHandler implements MessageHandler {
    @Autowired
    private SubjectsDataService subjectsDataService;

    @Override
    @Transactional
    public Response handle(Message message, Object payload) {
        String messageText = message.getText().strip();
        int pageIndex = 0;
        int pageMarkPosition = messageText.lastIndexOf('/');
        String searchString = messageText;
        if (pageMarkPosition > 0) {
            pageIndex = Integer.parseInt(messageText.substring(pageMarkPosition + 1)) - 1;
            searchString = messageText.substring(0, pageMarkPosition - 1).strip();
        }
        SubjectIdData callbackData = (SubjectIdData) payload;
        Page<SubjectDataEntity> subjectDataEntityPage = subjectsDataService.findAllByName(
                searchString,
                callbackData.getSbId(),
                SubjectDataType.FILE.name(),
                pageIndex,
                10
        );
        Response response = new Response();
        response.setType(ResponseType.MESSAGE);
        response.setSource(this);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        StringBuilder stringBuilder = new StringBuilder();
        response.setMessageHandlerType(MessageHandlerType.GET_SUBJECT_MATERIALS);
        response.setPayload(payload);
        List<SubjectDataEntity> subjectDataEntityList = subjectDataEntityPage.toList();
        if (subjectDataEntityList.isEmpty()) {
            stringBuilder.append(MessagesConstants.ANSWERS.NO_DATA.getValue());
        } else {
            stringBuilder.append(String.format(
                            MessagesConstants.ANSWERS.SUBJECT_FILES_LIST_HAT.getValue(),
                            subjectDataEntityList.getFirst().getSubject().getName(),
                            pageIndex + 1,
                            subjectDataEntityPage.getTotalPages()
                    )
            );
            for (SubjectDataEntity item : subjectDataEntityList) {
                stringBuilder.append(
                        String.format(
                                MessagesConstants.ANSWERS.SUBJECT_FILES_LIST_ITEM_MARKUP.getValue(),
                                item.getId(),
                                item.getName()
                        )
                );
            }
            stringBuilder.append(
                    MessagesConstants.ANSWERS.SUBJECT_FILES_LIST_FOOTER.getValue()
            );
        }
        sendMessage.setText(stringBuilder.toString());
        response.setSendMessage(sendMessage);
        return response;
    }
}
