package com.sicredi.toolschallenge.domain.entity;



import com.sicredi.toolschallenge.domain.enums.StatusTransacao;
import com.sicredi.toolschallenge.domain.enums.TipoPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transacao {

    @Id
    private String id;

    @Column(nullable = false)
    private BigDecimal valor;

    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusTransacao status;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    private Integer parcelas;

    private String estabelecimento;

    @Column(unique = true)
    private String codigoNsu;

    private String codigoAutorizacao;

    @Column(nullable = false)
    private String numeroCartao;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public void setStatus(StatusTransacao status) {
        this.status = status;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public String getCodigoNsu() {
        return codigoNsu;
    }

    public void setCodigoNsu(String codigoNsu) {
        this.codigoNsu = codigoNsu;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
}
