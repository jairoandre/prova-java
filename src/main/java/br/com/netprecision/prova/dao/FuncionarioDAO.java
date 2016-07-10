package br.com.netprecision.prova.dao;

import br.com.netprecision.prova.model.Funcionario;
import br.com.netprecision.prova.model.Pedido;

import java.util.List;

/**
 * Created by jairo on 08/07/2016.
 */
public class FuncionarioDAO extends AbstractDAO<Funcionario> {

    public FuncionarioDAO() {
        super(Funcionario.class);
    }

}
