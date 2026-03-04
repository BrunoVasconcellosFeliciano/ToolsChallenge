package com.sicredi.toolschallenge.controller;



import com.sicredi.toolschallenge.dto.TransacaoListResponseWrapperDTO;
import com.sicredi.toolschallenge.dto.TransacaoResponseDTO;
import com.sicredi.toolschallenge.dto.TransacaoRequestWrapperDTO;
import com.sicredi.toolschallenge.dto.TransacaoResponseWrapperDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sicredi.toolschallenge.service.TransacaoService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping("/pagamento")
    public ResponseEntity<TransacaoResponseWrapperDTO> realizarPagamento(
            @Valid @RequestBody TransacaoRequestWrapperDTO wrapper) {


        TransacaoResponseDTO response = transacaoService.criarPagamento(wrapper.getTransacao());

        TransacaoResponseWrapperDTO wrapperResponse = new TransacaoResponseWrapperDTO(response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(wrapperResponse);
    }

    @PatchMapping("/{id}/cancelamento")
    public ResponseEntity<TransacaoResponseWrapperDTO> cancelar(@PathVariable String id) {

        TransacaoResponseDTO response = transacaoService.cancelarTransacao(id);

        return ResponseEntity.ok(new TransacaoResponseWrapperDTO(response));
    }

    @GetMapping("/consulta/estorno")
    public ResponseEntity<TransacaoResponseWrapperDTO> consultaEstorno(
            @RequestParam String id){

        TransacaoResponseDTO response = transacaoService.consultaTransacaoCancelada(id);

        return ResponseEntity.ok(new TransacaoResponseWrapperDTO(response));
    }

    @GetMapping("/consulta/transacao")
    public ResponseEntity<TransacaoResponseWrapperDTO> consultaTransacaoPorId(
            @RequestParam String id){

        TransacaoResponseDTO response = transacaoService.consultaTransacaoporId(id);

        return ResponseEntity.ok(new TransacaoResponseWrapperDTO(response));
    }

    @GetMapping("/consulta/todos")
    public ResponseEntity<TransacaoListResponseWrapperDTO> consultaTodasTransacoes() {

        List<TransacaoResponseDTO> lista =
                transacaoService.consultaTodasTransacoes();

        return ResponseEntity.ok(
                new TransacaoListResponseWrapperDTO(lista)
        );
    }





}
