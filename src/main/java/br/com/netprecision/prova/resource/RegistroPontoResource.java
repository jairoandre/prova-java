package br.com.netprecision.prova.resource;

import br.com.netprecision.prova.constant.TipoRegistroPonto;
import br.com.netprecision.prova.dao.FuncionarioDAO;
import br.com.netprecision.prova.dao.RegistroPontoDAO;
import br.com.netprecision.prova.model.Funcionario;
import br.com.netprecision.prova.model.RegistroPonto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * Created by jairo on 10/07/2016.
 */

@Path("/ponto")
public class RegistroPontoResource {

    private RegistroPontoDAO registroPontoDAO = new RegistroPontoDAO();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @GET
    @Path("/ultimo")
    @Produces({MediaType.APPLICATION_JSON})
    public RegistroPonto ultimoRegistro(@QueryParam("id") Long id) throws Exception {
        return registroPontoDAO.getLastRegistroPonto(id);
    }

    @POST
    @Path("/registrar")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Integer registrarPonto(RegistroPontoJSON json) {
        Funcionario funcionario = funcionarioDAO.find(json.getId());
        if (funcionario == null) {
            return -1;
        } else {
            RegistroPonto registroAnterior = registroPontoDAO.getLastRegistroPonto(json.getId());
            RegistroPonto registroNovo = new RegistroPonto();
            registroNovo.setData(new Date());
            registroNovo.setFuncionario(funcionario);
            if (registroAnterior == null) {
                registroNovo.setTipo(TipoRegistroPonto.ENTRADA);
            } else {
                TipoRegistroPonto tipo = TipoRegistroPonto.ENTRADA;
                if (TipoRegistroPonto.ENTRADA.equals(registroAnterior.getTipo())){
                    tipo = TipoRegistroPonto.SAIDA;
                }
                registroNovo.setTipo(tipo);
            }
            try {
                registroPontoDAO.create(registroNovo);
                return 0;
            } catch (Exception e) {
                return -9;
            }
        }
    }

    public static class RegistroPontoJSON {

        private Long id;

        @JsonCreator
        public RegistroPontoJSON(@JsonProperty("id") Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
