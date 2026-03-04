package com.sicredi.toolschallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoListResponseWrapperDTO {
    private List<TransacaoResponseDTO> transacoes;
}
