package br.com.netprecision.prova.dao;

import br.com.netprecision.prova.model.RegistroPonto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by jairo on 09/07/2016.
 */
public class RegistroPontoDAO extends AbstractDAO<RegistroPonto> {

    public RegistroPontoDAO() {
        super(RegistroPonto.class);
    }

    public RegistroPonto getLastRegistroPonto(Long idFuncionario) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("funcionario.id", idFuncionario));
        criteria.addOrder(Order.desc("data"));
        List<RegistroPonto> list = criteria.list();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

}
