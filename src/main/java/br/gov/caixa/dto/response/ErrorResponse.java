package br.gov.caixa.dto.response;

public record ErrorResponse(
        String mensagemNegocial,
        String mensagemTecnica,
        String timestamp,
        Integer codigoHttp
) {
}
