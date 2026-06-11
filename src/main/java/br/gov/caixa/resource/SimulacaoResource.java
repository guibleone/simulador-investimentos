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
    public Response listarTodas(){
        List<SimulacaoResponse> simulacoes = simulacaoService.listarTodos();
        return Response.ok().entity(simulacoes).build();
    }

    @GET
    @Path("/{coSimulacao}")
    public Response detalharSimulacao(@RestPath String coSimulacao){
        ResultadoSimulacaoResponse resultadoSimulacaoResponse = simulacaoService.encontrarSimulacao(coSimulacao);
        return Response.ok(resultadoSimulacaoResponse).build();
    }

    @POST
    @Path("/investimento")
    public Response realizarSimulacao(@Valid DadosSimulacaoRequest dadosSimulacao){
        ResultadoSimulacaoResponse resultadoSimulacaoResponse = simulacaoService.realizarSimulacao(dadosSimulacao);
        return Response.status(Response.Status.CREATED).entity(resultadoSimulacaoResponse).build();
    }
}
