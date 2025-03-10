package co.api.user.domain.model.common.exception;

import lombok.Getter;

@Getter
public enum CodeError {
    FOUND(302),
    NOT_FOUND(404),
    INTERNAL_ERROR(500),
    BAD_REQUEST(400);

    private final Integer code;

    CodeError(Integer code) {
        this.code = code;
    }
}
