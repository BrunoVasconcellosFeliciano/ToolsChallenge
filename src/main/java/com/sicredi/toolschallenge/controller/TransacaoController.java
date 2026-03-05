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


        TransacaoResponseDTO response = transacaoService.criarTransacao(wrapper.getTransacao());

        TransacaoResponseWrapperDTO wrapperResponse = new TransacaoResponseWrapperDTO(response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(wrapperResponse);
    }

    @PatchMapping("/{id}/cancelamento")
    public ResponseEntity<TransacaoResponseWrapperDTO> realizarCancelamento(@PathVariable String id) {

        TransacaoResponseDTO response = transacaoService.cancelarTransacao(id);

        return ResponseEntity.ok(new TransacaoResponseWrapperDTO(response));
    }

    @GetMapping("/consulta/estorno")
    public ResponseEntity<TransacaoResponseWrapperDTO> consultarEstorno(
            @RequestParam String id){

        TransacaoResponseDTO response = transacaoService.consultarTransacaoCancelada(id);

        return ResponseEntity.ok(new TransacaoResponseWrapperDTO(response));
    }

    @GetMapping("/consulta/transacao")
    public ResponseEntity<TransacaoResponseWrapperDTO> consultarTransacaoPorId(
            @RequestParam String id){

        TransacaoResponseDTO response = transacaoService.consultarTransacaoPorId(id);

        return ResponseEntity.ok(new TransacaoResponseWrapperDTO(response));
    }

    @GetMapping("/consulta/todos")
    public ResponseEntity<TransacaoListResponseWrapperDTO> consultarTodasTransacoes() {

        List<TransacaoResponseDTO> lista =
                transacaoService.consultarTodasTransacoes();

        return ResponseEntity.ok(
                new TransacaoListResponseWrapperDTO(lista)
        );
    }





}
