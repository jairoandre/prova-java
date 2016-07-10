package br.com.netprecision.prova.dao;

import br.com.netprecision.prova.model.Pedido;
import br.com.netprecision.prova.model.Produto;

import java.util.List;

/**
 * Created by jairo on 08/07/2016.
 */
public class PedidoDAO extends AbstractDAO<Pedido> {

    public PedidoDAO() {
        super(Pedido.class);
    }

    public List<Pedido> listAll() {
        return findWithNamedQuery(Pedido.ALL);
    }

    public Long criarPedido() {
        Pedido pedido = create(new Pedido());
        return pedido.getId();
    }
}
