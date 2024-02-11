package com.creditCardPaymentService.model;

import java.io.Serializable;

import jakarta.persistence.*;
import com.creditCardPaymentService.model.FormaPagamento;


@Entity
@Table(name = "transacao")
public class Transacao implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(length = 255) 
    private String cartao;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;





    @OneToOne(cascade = CascadeType.ALL)
    private DescricaoTransacao descricao;

    @OneToOne(cascade = CascadeType.ALL)
    private FormaPagamento formaPagamento;

    private String tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public DescricaoTransacao getDescricao() {
        return descricao;
    }

    public void setDescricao(DescricaoTransacao descricao) {
        this.descricao = descricao;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
