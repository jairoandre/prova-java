package br.com.netprecision.prova.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
@Table(name = "PEDIDO")
@NamedQueries({
        @NamedQuery(name = Pedido.ALL, query = "select p from Pedido p")
})
public class Pedido {

    public static final String ALL = "Pedido.ALL";

    @Id
    @SequenceGenerator(name = "seqPedido", sequenceName = "seq_pedido", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPedido")
    @Column(name = "ID")
    private Long id;

    @Column(name = "FECHADO")
    private Boolean fechado = false;

    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER,orphanRemoval = true)
    private List<ItemPedido>  itens = new ArrayList<>();

    public Pedido(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFechado() {
        return fechado;
    }

    public void setFechado(Boolean fechado) {
        this.fechado = fechado;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

}
