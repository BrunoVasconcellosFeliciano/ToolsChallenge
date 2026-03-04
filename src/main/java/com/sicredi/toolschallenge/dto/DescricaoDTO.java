package com.sicredi.toolschallenge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sicredi.toolschallenge.domain.enums.StatusTransacao;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DescricaoDTO {

    BigDecimal valor;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime dataHora;
    String estabelecimento;
    String nsu;
    String codigoAutorizador;
    StatusTransacao status;
}
