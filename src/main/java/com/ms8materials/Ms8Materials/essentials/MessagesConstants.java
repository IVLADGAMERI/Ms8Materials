package com.ms8materials.Ms8Materials.essentials;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MessagesConstants {
    @Getter
    @AllArgsConstructor
    public enum MAIN_KEYBOARD_BUTTONS {
        FILES("\uD83D\uDCDA Материалы"), DASHBOARD("\uD83D\uDDD3 Расписание"),
        SUBJECTS("\uD83D\uDCDA Предметы"), NOTES("\uD83D\uDCCB Заметки");
        private final String value;
    }
    @Getter
    @AllArgsConstructor
    public enum ANSWERS {
        START_COMMAND_MESSAGE("Привет! Это бот для удобного использования, " +
                "добавления и хранения учебных файлов. " +
                "Возникли вопросы? Напиши https://t.me/IVladiSlave."),
        SUBJECTS_SEMESTERS_HAT("Выбери семестр: \n"),
        WAIT("Обработка..."),
        SUBJECTS_LIST_HAT("Список предметов \nСеместр: %s"),
        SUBJECTS_LIST_FOOTER("Выбери номер интересующего предмета:"),
        MARKER("\uD83D\uDD39"),
        SUBJECT_MATERIALS_LIST_HAT("Выбери тип:"),
        EMPTY_LIST("Список пуст!");
        private final String value;
    }
    @Getter
    @AllArgsConstructor
    public enum INLINE_BUTTONS_TEXT {
        PREVIOUS_PAGE("⬅ Назад"),
        NEXT_PAGE("Вперед ➡"),
        FILES("\uD83D\uDCBE Файлы"),
        PHOTOS("\uD83D\uDCF7 Фото");
        private final String value;
    }
}
