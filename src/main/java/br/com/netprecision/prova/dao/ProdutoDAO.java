package br.com.netprecision.prova.dao;

import br.com.netprecision.prova.model.Produto;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by jairo on 08/07/2016.
 */
public class ProdutoDAO extends AbstractDAO<Produto> {

    public ProdutoDAO() {
        super(Produto.class);
    }

    public List<Produto> listAll() {
        return findWithNamedQuery(Produto.ALL);
    }

    public Produto findByCodigo(String codigo) {
        Query query = getEm().createNamedQuery(Produto.BY_CODE);
        query.setParameter("codigo", codigo);
        List<Produto> produtos = query.getResultList();
        return produtos.isEmpty() ? null : produtos.get(0);
    }
}
