package com.ms8materials.Ms8Materials.interaction.callbacks.handlers;

import com.ms8materials.Ms8Materials.data.jpa.SubjectDataType;
import com.ms8materials.Ms8Materials.interaction.callbacks.CallbackType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum SubjectDataTypeAndSortDirection {
    GET_FILES_DESC(CallbackType.GET_SUBJECT_FILES_LIST_DESCENDING, Sort.Direction.DESC, SubjectDataType.FILE),
    GET_FILES_ASC(CallbackType.GET_SUBJECT_FILES_LIST_ASCENDING, Sort.Direction.ASC, SubjectDataType.FILE),
    GET_PHOTOS_DESC(CallbackType.GET_SUBJECT_PHOTOS_LIST_DESCENDING, Sort.Direction.DESC, SubjectDataType.PHOTO),
    GET_PHOTOS_ASC(CallbackType.GET_SUBJECT_PHOTOS_LIST_ASCENDING, Sort.Direction.ASC, SubjectDataType.PHOTO),
    FIND_FILES(CallbackType.FIND_SUBJECT_FILE, Sort.Direction.DESC, SubjectDataType.FILE),
    FIND_PHOTOS(CallbackType.FIND_SUBJECT_PHOTO, Sort.Direction.DESC, SubjectDataType.PHOTO);
    private final CallbackType callbackType;
    private final Sort.Direction sortDirection;
    private final SubjectDataType subjectDataType;
    public static SubjectDataTypeAndSortDirection getBySubjectDataTypeAndSortDirection(
            SubjectDataType subjectDataType, Sort.Direction sortDirection) {
        for (SubjectDataTypeAndSortDirection item : SubjectDataTypeAndSortDirection.values()) {
            if (Objects.equals(item.subjectDataType, subjectDataType) && Objects.equals(item.sortDirection, sortDirection))
                return item;
        }
        return null;
    }
    public static SubjectDataTypeAndSortDirection getByCallbackType(CallbackType callbackType) {
        for (SubjectDataTypeAndSortDirection item : SubjectDataTypeAndSortDirection.values()) {
            if (Objects.equals(item.callbackType, callbackType)) return item;
        }
        return null;
    }
}
