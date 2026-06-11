package br.gov.caixa.resource;

import br.gov.caixa.dto.request.DadosSimulacaoRequest;
import br.gov.caixa.dto.response.SimulacaoResponse;
import br.gov.caixa.service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/simulacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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

    @POST
    @Path("/investimento")
    public Response realizarSimulacao(DadosSimulacaoRequest dadosSimulacao){
        return Response.ok().build();
    }
}
