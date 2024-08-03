package com.ms8materials.Ms8Materials.interaction.essentials;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MessagesConstants {
    @Getter
    @AllArgsConstructor
    public enum MAIN_KEYBOARD_BUTTONS {
        FILES("/\uD83D\uDCDA Материалы"), NOTES("/\uD83D\uDCCB Информация");
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
        EMPTY_LIST("Список пуст!"),
        NO_DATA("Нет данных."),
        FIND_SUBJECT_FILES("Поиск файлов\nНомер предмета: %s\nВведи название файла или его часть.\n" +
                "Чтобы указать номер страницы добавь в конец запроса следующую конструкцию: /n, где n - номер страницы. Например: " +
                "Метода АПК /2."),
        MESSAGE_UNRECOGNISED("Сообщение не распознано."),
        SUBJECT_FILES_LIST_HAT("Список файлов\nПредмет: %s\nСтраница: %s из %s\n"),
        SUBJECT_FILES_LIST_ITEM_MARKUP("%s) %s\nЗагрузил: %s\n"),
        SUBJECT_FILES_LIST_FOOTER("Напиши в чат список нужных файлов (например 1, 10, 21).\n" +
                "Если файл долго не отправляется - просто подожди окончания загрузки.");
        private final String value;
    }
    @Getter
    @AllArgsConstructor
    public enum INLINE_BUTTONS_TEXT {
        FIND("\uD83D\uDD0E Найти"),
        PREVIOUS_PAGE("⬅ Назад"),
        NEXT_PAGE("Вперед ➡"),
        FILES("\uD83D\uDCBE Файлы"),
        PHOTOS("\uD83D\uDCF7 Фото"),
        SORT_DIRECTION_DESC("Сортировка: новые"),
        SORT_DIRECTION_ASC("Сортировка: старые"),
        QUIT("⬅ Выход");
        private final String value;
    }
}
