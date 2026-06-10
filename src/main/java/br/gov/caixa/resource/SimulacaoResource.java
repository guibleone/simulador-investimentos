package br.gov.caixa.resource;

import br.gov.caixa.dto.response.SimulacaoResponse;
import br.gov.caixa.service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
}
