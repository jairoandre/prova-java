package br.com.netprecision.prova.resource;

import br.com.netprecision.prova.dao.ProdutoDAO;
import br.com.netprecision.prova.model.Produto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("/produto")
public class ProdutoResource {

    // private @Inject ProdutoDAO dao;

    @GET
    @Path("/ping")
    public String getServerTime() {
        return String.format("received ping on %s", new Date());
    }

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Produto> getProdutos() throws Exception {
        ProdutoDAO dao = new ProdutoDAO();
        return dao.listAll();
    }

}
