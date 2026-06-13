package br.gov.caixa.resource;

import br.gov.caixa.dto.request.DadosSimulacaoRequest;
import br.gov.caixa.dto.response.ResultadoSimulacaoResponse;
import br.gov.caixa.dto.response.SimulacaoResponse;
import br.gov.caixa.service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@Path("/simulacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(ref = "Simulação")
public class SimulacaoResource {

    private final SimulacaoService simulacaoService;

    @Inject
    public SimulacaoResource(SimulacaoService simulacaoService){
        this.simulacaoService = simulacaoService;
    }

    @GET
    @Operation(summary = "Listar todas as simulações", description = "Retorna uma lista de todas as simulações cadastradas.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de simulações", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = SimulacaoResponse.class))),
            @APIResponse(responseCode = "500", description = "Erro interno")
    })
    public Response listarTodas(){
        List<SimulacaoResponse> simulacoes = simulacaoService.listarTodos();
        return Response.ok().entity(simulacoes).build();
    }

    @GET
    @Path("/{coSimulacao}")
    @Operation( summary = "Detalhar simulação", description = "Retorna os detalhes de uma simulação específica, incluindo as parcelas.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Detalhes da simulação", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultadoSimulacaoResponse.class))),
            @APIResponse(responseCode = "404", description = "Simulação não encontrada"),
            @APIResponse(responseCode = "500", description = "Erro interno")
    })
    public Response detalharSimulacao(@RestPath String coSimulacao){
        ResultadoSimulacaoResponse resultadoSimulacaoResponse = simulacaoService.encontrarSimulacao(coSimulacao);
        return Response.ok(resultadoSimulacaoResponse).build();
    }

    @POST
    @Path("/investimento")
    @Operation(summary = "Realizar simulação de investimento", description = "Realiza uma simulação de investimento com base nos dados fornecidos e retorna os resultados.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Simulação realizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultadoSimulacaoResponse.class))),
            @APIResponse(responseCode = "400", description = "Dados de simulação inválidos"),
            @APIResponse(responseCode = "500", description = "Erro interno")
    })
    public Response realizarSimulacao(@Valid DadosSimulacaoRequest dadosSimulacao){
        ResultadoSimulacaoResponse resultadoSimulacaoResponse = simulacaoService.realizarSimulacao(dadosSimulacao);
        return Response.status(Response.Status.CREATED).entity(resultadoSimulacaoResponse).build();
    }
}
