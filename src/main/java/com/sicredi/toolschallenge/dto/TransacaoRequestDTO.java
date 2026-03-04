package com.sicredi.toolschallenge.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
public class TransacaoRequestDTO {
@JsonProperty("cartao")
String numeroCartao;
String id;
DescricaoDTO descricao;
FormaPagamentoDTO formaPagamento;

}
