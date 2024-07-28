package com.ms8materials.Ms8Materials.interaction.messages.handlers;

import com.ms8materials.Ms8Materials.data.jpa.entities.SubjectDataEntity;
import com.ms8materials.Ms8Materials.data.jpa.services.FilesService;
import com.ms8materials.Ms8Materials.data.jpa.services.SubjectsDataService;
import com.ms8materials.Ms8Materials.interaction.essentials.MessagesConstants;
import com.ms8materials.Ms8Materials.interaction.Response;
import com.ms8materials.Ms8Materials.interaction.ResponseType;
import com.ms8materials.Ms8Materials.interaction.messages.MessageHandlerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class GetSubjectMaterialsMessageHandler implements MessageHandler {
    @Autowired
    private SubjectsDataService subjectsDataService;
    @Autowired
    private FilesService filesService;
    @Override
    public Response handle(Message message, Object payload) {
        long chatId = message.getChatId();
        String messageText = message.getText();
        Response response = new Response();
        response.setSource(this);
        response.setMessageHandlerType(MessageHandlerType.GET_SUBJECT_MATERIALS);
        try {
            List<Integer> dataIdList = Arrays.stream(
                    messageText.replaceAll(" ", "").split(",")
            ).map(Integer::valueOf).toList();
            List<SubjectDataEntity> subjectDataEntityList = subjectsDataService.findAllByIds(dataIdList);
            List<String> fileNameList = new ArrayList<>();
            for (SubjectDataEntity item : subjectDataEntityList) {
                fileNameList.add(item.getName());
            }
            List<SendDocument> sendDocumentList = new ArrayList<>();
            for (String item : fileNameList) {
                Optional<File> fileOptional = filesService.getFile(item);
                if (fileOptional.isEmpty()) continue;
                sendDocumentList.add(
                        new SendDocument(
                                String.valueOf(chatId),
                                new InputFile(fileOptional.get())
                        )
                );
            }
            if (sendDocumentList.isEmpty()) {
                response.setType(ResponseType.MESSAGE);
                response.setSendMessage(
                        new SendMessage(
                                String.valueOf(chatId),
                                MessagesConstants.ANSWERS.NO_DATA.getValue()
                        )
                );
            } else {
                response.setType(ResponseType.DOCUMENT);
                response.setSendDocumentList(sendDocumentList);
            }
        } catch (Exception e) {
            response.setSendMessage(new SendMessage(String.valueOf(chatId), MessagesConstants.ANSWERS.MESSAGE_UNRECOGNISED.getValue()));
        }
        return response;
    }
}
