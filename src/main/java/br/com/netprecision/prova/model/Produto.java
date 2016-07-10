package br.com.netprecision.prova.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@Entity
@Table(name = "PRODUTO")
@NamedQueries({
        @NamedQuery(name = Produto.ALL, query = "select p from Produto p"),
        @NamedQuery(name = Produto.BY_CODE, query = "select p from Produto p where p.codigo = :codigo")
})
public class Produto implements Serializable {

    public static final String ALL = "Produto.ALL";
    public static final String BY_CODE = "Produto.BY_CODE";

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CD_CODIGO")
    private String codigo;

    @Column(name = "NM_NOME")
    private String nome;

    @Column(name = "VL_PRECO")
    private BigDecimal preco;

    public Produto(){}

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

}
