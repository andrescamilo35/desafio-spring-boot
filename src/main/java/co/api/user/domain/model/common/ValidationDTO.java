package co.api.user.domain.model.common;


public record ValidationDTO(
        String field,
        String error
) {
}