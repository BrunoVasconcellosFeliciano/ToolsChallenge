package com.sicredi.toolschallenge.service;

import com.sicredi.toolschallenge.domain.entity.Transacao;
import com.sicredi.toolschallenge.domain.enums.StatusTransacao;
import com.sicredi.toolschallenge.domain.enums.TipoPagamento;
import com.sicredi.toolschallenge.dto.DescricaoDTO;
import com.sicredi.toolschallenge.dto.FormaPagamentoDTO;
import com.sicredi.toolschallenge.dto.TransacaoRequestDTO;
import com.sicredi.toolschallenge.dto.TransacaoResponseDTO;
import com.sicredi.toolschallenge.exception.ServicoException;
import com.sicredi.toolschallenge.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private AutorizadorFakeService autorizador;

    @Test
    void deveAutorizarPagamentoQuandoValorValido() {

        TransacaoRequestDTO req = criarRequestComValorValido();

        when(transacaoRepository.existsById(req.getId())).thenReturn(false);
        when(autorizador.autorizar(any())).thenReturn(StatusTransacao.AUTORIZADO);

        TransacaoResponseDTO response =
                transacaoService.criarTransacao(req);

        assertEquals(StatusTransacao.AUTORIZADO, response.getDescricao().getStatus());

        verify(transacaoRepository).saveAndFlush(any(Transacao.class));
    }

    @Test
    void deveNegarPagamentoQuandoValorZeroOuNegativo() {
        TransacaoRequestDTO req = criarRequestComValorZero();

        ServicoException e = assertThrows(
                ServicoException.class,
                () -> transacaoService.criarTransacao(req)
        );

        assertEquals("Valor deve ser maior que zero!", e.getMessage());
    }

    @Test
    void deveRetornarConflictQuandoIDExistente() {
        TransacaoRequestDTO req = criarRequestComValorValido();
        when(transacaoRepository.existsById(req.getId())).thenReturn(true);

        ServicoException e = assertThrows(
                ServicoException.class,
                () -> transacaoService.criarTransacao(req)
        );

        assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
    }

    @Test
    void deveCancelarTransacaoExistente() {
        Transacao t = criarTransacaoAutorizada();
        when(transacaoRepository.findById("123")).thenReturn(Optional.of(t));

        TransacaoResponseDTO resp =
                transacaoService.cancelarTransacao("123");

        assertEquals(StatusTransacao.CANCELADO,
                resp.getDescricao().getStatus());
        verify(transacaoRepository).saveAndFlush(t);
    }

    @Test
    void deveLancarNotFoundAoCancelarTransacaoInexistente() {
        when(transacaoRepository.findById("999")).thenReturn(Optional.empty());

        ServicoException e = assertThrows(
                ServicoException.class,
                () -> transacaoService.cancelarTransacao("999")
        );

        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
    }

    @Test
    void deveNegarPagamentoQuandoParcelasInvalidas() {

        TransacaoRequestDTO req = criarRequestComValorValido();
        req.getFormaPagamento().setParcelas(0);

        ServicoException e = assertThrows(
                ServicoException.class,
                () -> transacaoService.criarTransacao(req)
        );

        assertEquals("Parcela deve ser maior que zero!", e.getMessage());
    }

    @Test
    void deveRetornarListaDeTransacoes() {

        Transacao t = criarTransacaoAutorizada();

        when(transacaoRepository.findAll())
                .thenReturn(List.of(t));

        List<TransacaoResponseDTO> lista =
                transacaoService.consultarTodasTransacoes();

        assertEquals(1, lista.size());
    }

    private TransacaoRequestDTO criarRequestComValorValido() {

        DescricaoDTO descricao = new DescricaoDTO(
                new BigDecimal("100"),
                LocalDateTime.now(),
                "Loja Teste",
                null,
                null,
                null
        );

        FormaPagamentoDTO formaPagamento =
                new FormaPagamentoDTO(TipoPagamento.AVISTA, 1);

        return new TransacaoRequestDTO(
                "4587********9852",
                UUID.randomUUID().toString(),
                descricao,
                formaPagamento
        );
    }

    private TransacaoRequestDTO criarRequestComValorZero() {

        DescricaoDTO descricao = new DescricaoDTO(
                BigDecimal.ZERO,
                LocalDateTime.now(),
                "Loja Teste",
                null,
                null,
                null
        );
        FormaPagamentoDTO formaPagamento =
                new FormaPagamentoDTO(TipoPagamento.AVISTA, 1);

        return new TransacaoRequestDTO(
                "4587********9852",
                UUID.randomUUID().toString(),
                descricao,
                formaPagamento
        );
    }

    private Transacao criarTransacaoAutorizada() {
        Transacao t = new Transacao();
        t.setId("123");
        t.setNumeroCartao("4587********9852");
        t.setValor(new BigDecimal("100"));
        t.setDataHora(LocalDateTime.now());
        t.setEstabelecimento("Loja Teste");
        t.setStatus(StatusTransacao.AUTORIZADO);
        t.setTipoPagamento(TipoPagamento.AVISTA);
        t.setParcelas(1);
        return t;
    }
}
