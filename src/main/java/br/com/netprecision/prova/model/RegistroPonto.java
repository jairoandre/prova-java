package br.com.netprecision.prova.model;

import br.com.netprecision.prova.constant.TipoRegistroPonto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jairo on 10/07/2016.
 */
@Entity
@Table(name = "REGISTRO_PONTO")
public class RegistroPonto implements Serializable {

    @Id
    @SequenceGenerator(name = "seqRegistroPonto", sequenceName = "seq_registro_ponto", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRegistroPonto")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_FUNCIONARIO")
    private Funcionario funcionario;

    @Column(name = "DT_DATA")
    private Date data;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "CD_TIPO")
    private TipoRegistroPonto tipo;

    public RegistroPonto() {}

    public RegistroPonto(Long id, Funcionario funcionario, Date data, TipoRegistroPonto tipo) {
        this.id = id;
        this.funcionario = funcionario;
        this.data = data;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TipoRegistroPonto getTipo() {
        return tipo;
    }

    public void setTipo(TipoRegistroPonto tipo) {
        this.tipo = tipo;
    }
}
