package com.sicredi.toolschallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TransacaoResponseDTO {

    @JsonProperty("cartao")
    String numeroCartao;
    String id;
    DescricaoDTO descricao;
    FormaPagamentoDTO formaPagamento;
}
