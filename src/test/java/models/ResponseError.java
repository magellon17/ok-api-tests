package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pojo класс тела ответа об ошибке
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private String error_code;
    private String error_msg;
    private Object error_data;
}
