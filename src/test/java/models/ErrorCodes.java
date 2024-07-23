package models;

/**
 * Основные коды ошибок
 */
public enum ErrorCodes {
    NOT_FOUND{
        public String toString() {
            return "300";
        }
    },
    GROUP_RESTRICTION{
        public String toString() {
            return "456";
        }
    }
}
