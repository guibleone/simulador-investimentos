package br.gov.caixa.dto.response;

public record ConstraintViolationResponse(
        String campo,
        String mensagem
) {
}
