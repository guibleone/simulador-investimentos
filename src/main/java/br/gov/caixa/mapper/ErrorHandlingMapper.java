package br.gov.caixa.mapper;

import br.gov.caixa.comum.Utils;
import br.gov.caixa.dto.response.ErrorResponse;
import jakarta.ws.rs.NotFoundException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ErrorHandlingMapper {

    @ServerExceptionMapper
    public RestResponse<ErrorResponse> handleException(Exception exception) {
        RestResponse.Status status = RestResponse.Status.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = criarErrorResponse("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.", exception, status);
        return RestResponse.status(status, errorResponse);
    }

    @ServerExceptionMapper
    public RestResponse<ErrorResponse> handleNotFoundException(NotFoundException exception) {
        RestResponse.Status status = RestResponse.Status.NOT_FOUND;
        ErrorResponse errorResponse = criarErrorResponse("O recurso solicitado não foi encontrado.", exception, status);
        return RestResponse.status(status, errorResponse);
    }

    private <T extends Exception> ErrorResponse criarErrorResponse(String mensagemNegocial, T exception, RestResponse.Status status) {
        String formattedDate = Utils.formatarDataHora(OffsetDateTime.now(ZoneOffset.UTC));
        return new ErrorResponse(
                mensagemNegocial,
                exception.getMessage(),
                formattedDate,
                status.getStatusCode()
        );
    }
}
