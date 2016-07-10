package br.com.netprecision.prova.resource;

import br.com.netprecision.prova.dao.ItemPedidoDAO;
import br.com.netprecision.prova.dao.PedidoDAO;
import br.com.netprecision.prova.dao.ProdutoDAO;
import br.com.netprecision.prova.model.ItemPedido;
import br.com.netprecision.prova.model.Pedido;
import br.com.netprecision.prova.model.Produto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Path("/pedido")
public class PedidoResource {

    @GET
    @Path("ping")
    public String getServerTime() {
        return String.format("received ping on %s", new Date());
    }

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Pedido> getPedidos() throws Exception {
        PedidoDAO dao = new PedidoDAO();
        return dao.listAll();
    }

    @GET
    @Path("/criarPedido")
    @Produces({MediaType.TEXT_PLAIN})
    public Long criarPedido() throws Exception {
        PedidoDAO dao = new PedidoDAO();
        return dao.criarPedido();
    }

    PedidoDAO dao = new PedidoDAO();
    ProdutoDAO daoProduto = new ProdutoDAO();
    ItemPedidoDAO daoItemPedido = new ItemPedidoDAO();

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/adicioneProdutoNoPedido")
    public byte adicioneProdutoNoPedido(PostOBJ json) {
        try {
            Pedido pedido = dao.find(json.getId());
            if (pedido == null) {
                return -1;
            }
            Produto produto = daoProduto.findByCodigo(json.getCodigo());
            if (produto == null) {
                return -2;
            }
            ItemPedido itemPedido = new ItemPedido(pedido, produto, json.getQuantidade());
            daoItemPedido.create(itemPedido);
            return 0;
        } catch (Exception e) {
            return -9;
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/retireProdutoDoPedido")
    public byte retireProdutoDoPedido(PostOBJ obj) {
        try {
            PedidoDAO dao = new PedidoDAO();
            ProdutoDAO daoProduto = new ProdutoDAO();
            Pedido pedido = dao.find(obj.getId());
            if (pedido == null) {
                return -1;
            }
            Produto produto = daoProduto.findByCodigo(obj.getCodigo());
            if (produto == null) {
                return -2;
            }
            ItemPedido itemPedido = null;
            for (ItemPedido item : pedido.getItens()) {
                if (produto.getId().equals(item.getProduto().getId())) {
                    itemPedido = item;
                    break;
                }
            }
            if (itemPedido == null) {
                return -3;
            }
            Integer novaQuantidade = itemPedido.getQuantidade() - obj.getQuantidade();
            novaQuantidade = novaQuantidade < 0 ? 0 : novaQuantidade;
            itemPedido.setQuantidade(novaQuantidade);
            dao.update(pedido);
            return 0;
        } catch (Exception e) {
            return -9;
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/calculePrecoTotalPedido")
    public float calculePrecoTotalPedido(PostOBJ obj) {
        PedidoDAO dao = new PedidoDAO();
        ProdutoDAO daoProduto = new ProdutoDAO();
        Pedido pedido = dao.find(obj.getId());
        if (pedido == null) {
            return -1;
        }
        BigDecimal totalPedido = BigDecimal.ZERO;
        for (ItemPedido itemPedido : pedido.getItens()) {
            totalPedido = totalPedido.add(itemPedido.getProduto().getPreco().multiply(new BigDecimal(itemPedido.getQuantidade())));
        }

        return totalPedido.floatValue();

    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/fechePedido")
    public float fechePedido(PostOBJ obj) {
        float valor = calculePrecoTotalPedido(obj);
        if (valor < 0) {
            return -1;
        }
        if (obj.getValor().floatValue() < valor) {
            return -2;
        }
        return obj.getValor().floatValue() - valor;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/calculePrecoTotalPedidos")
    public float calculePrecoTotalPedidos(List<PostOBJ> pedidos) {
        ProdutoDAO daoProduto = new ProdutoDAO();
        BigDecimal total = BigDecimal.ZERO;
        for (PostOBJ obj : pedidos) {
            Produto produto = daoProduto.findByCodigo(obj.getCodigo());
            if (produto == null) {
                return -2;
            }
            total = total.add(produto.getPreco().multiply(new BigDecimal(obj.getQuantidade())));
        }
        return total.floatValue();
    }

    public static class PostOBJ {
        private Long id;
        private String codigo;
        private Integer quantidade;
        private BigDecimal valor;

        @JsonCreator
        public PostOBJ(@JsonProperty("id") Long id,
                       @JsonProperty("codigo") String codigo,
                       @JsonProperty("quantidade") Integer quantidade,
                       @JsonProperty("valor") BigDecimal valor) {
            this.id = id;
            this.codigo = codigo;
            this.quantidade = quantidade;
            this.valor = valor;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }

        public BigDecimal getValor() {
            return valor;
        }

        public void setValor(BigDecimal valor) {
            this.valor = valor;
        }
    }
}


