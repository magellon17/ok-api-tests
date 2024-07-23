package models;

/**
 * Основные сообщения об ошибках
 */
public enum ErrorMessages {
    NOT_FOUND{
        public String toString() {
            return "NOT_FOUND : not.found.object";
        }
    },
    GROUP_RESTRICTION{
        public String toString() {
            return "GROUP_RESTRICTION : errors.group.cannot_view_group";
        }
    }
}
