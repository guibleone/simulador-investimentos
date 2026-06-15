package br.gov.caixa.dto.response;

import java.util.List;
import java.util.Optional;

public record ErrorResponse(
        String mensagemNegocial,
        String mensagemTecnica,
        String timestamp,
        Integer codigoHttp,
        List<ConstraintViolationResponse> violacoes
) {
}
