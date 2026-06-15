package br.gov.caixa.mapper;

import br.gov.caixa.comum.Utils;
import br.gov.caixa.dto.response.ConstraintViolationResponse;
import br.gov.caixa.dto.response.ErrorResponse;
import io.quarkus.hibernate.validator.runtime.jaxrs.ResteasyReactiveViolationException;
import io.quarkus.logging.Log;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @ServerExceptionMapper
    public RestResponse<ErrorResponse>  handleConstraintViolation(ResteasyReactiveViolationException exception){
        RestResponse.Status status = RestResponse.Status.BAD_REQUEST;
        Log.info(exception);
        ErrorResponse errorResponse = criarErrorResponse("Erro de validação de regra de entrada", exception, status);
        return RestResponse.status(status, errorResponse);
    }

    private <T extends Exception> ErrorResponse criarErrorResponse(String mensagemNegocial, T exception, RestResponse.Status status) {
       List<ConstraintViolationResponse> violacoes = new ArrayList<>();
        String formattedDate = Utils.formatarDataHora(OffsetDateTime.now(ZoneOffset.UTC));

        String mensagemTecnica  =  exception.getMessage();

        if(exception instanceof ResteasyReactiveViolationException){
           String[] listViolations = mensagemTecnica.split(",");
           for (String violation : listViolations){
               String[] violationDetail = violation.split(": "); // Espaço para não quebrar a formatação do texto
               violacoes.add(new ConstraintViolationResponse(violationDetail[0],violationDetail[1]));
           }
           mensagemTecnica = "Erro de vioalação de contrato de API";
        }

        return new ErrorResponse(
                mensagemNegocial,
                mensagemTecnica,
                formattedDate,
                status.getStatusCode(),
                violacoes
        );
    }
}
