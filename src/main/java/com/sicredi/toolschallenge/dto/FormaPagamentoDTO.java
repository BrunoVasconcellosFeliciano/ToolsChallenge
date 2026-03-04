package com.sicredi.toolschallenge.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sicredi.toolschallenge.domain.enums.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormaPagamentoDTO {

    @JsonProperty("tipo")
    TipoPagamento tipoPagamento;
    @JsonProperty("parcela")
    Integer parcelas;

}
