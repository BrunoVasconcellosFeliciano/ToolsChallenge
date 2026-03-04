package com.sicredi.toolschallenge.service;

import com.sicredi.toolschallenge.domain.entity.Transacao;
import com.sicredi.toolschallenge.domain.enums.StatusTransacao;
import com.sicredi.toolschallenge.exception.ServicoException;
import com.sicredi.toolschallenge.utils.CodigoGenerator;
import com.sicredi.toolschallenge.dto.DescricaoDTO;
import com.sicredi.toolschallenge.dto.FormaPagamentoDTO;
import com.sicredi.toolschallenge.dto.TransacaoRequestDTO;
import com.sicredi.toolschallenge.dto.TransacaoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sicredi.toolschallenge.repository.TransacaoRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final AutorizadorFakeService autorizador;

    public TransacaoResponseDTO criarPagamento(TransacaoRequestDTO request){

        validarTransacaoPagamento(request.getDescricao().getValor(), request.getFormaPagamento().getParcelas(), request.getId());

        Transacao transacao = new Transacao();

        transacao.setId(request.getId());
        transacao.setNumeroCartao(request.getNumeroCartao());
        transacao.setValor(request.getDescricao().getValor());
        transacao.setDataHora(request.getDescricao().getDataHora());
        transacao.setEstabelecimento(request.getDescricao().getEstabelecimento());
        transacao.setCodigoAutorizacao(CodigoGenerator.gerarCodigoAutorizacao());
        transacao.setCodigoNsu(CodigoGenerator.gerarNSU());
        transacao.setStatus(autorizador.autorizar(transacao));
        transacao.setTipoPagamento(request.getFormaPagamento().getTipoPagamento());
        transacao.setParcelas(request.getFormaPagamento().getParcelas());
        transacaoRepository.saveAndFlush(transacao);

        DescricaoDTO descricaoDTO = new DescricaoDTO(
                transacao.getValor(),
                transacao.getDataHora(),
                transacao.getEstabelecimento(),
                transacao.getCodigoAutorizacao(),
                transacao.getCodigoNsu(),
                transacao.getStatus()
        );

        FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO(
                transacao.getTipoPagamento(), transacao.getParcelas()
        );

        return new TransacaoResponseDTO(
                transacao.getNumeroCartao(),
                transacao.getId(),
                descricaoDTO,
                formaPagamentoDTO
        );
    }

    public TransacaoResponseDTO cancelarTransacao(String id){

        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ServicoException(HttpStatus.NOT_FOUND, "Transação inexistente"));

        if (transacao.getStatus() != StatusTransacao.AUTORIZADO) {
            throw new ServicoException(HttpStatus.BAD_REQUEST,
                    "Somente transações autorizadas podem ser canceladas.");
        }

        transacao.setStatus(StatusTransacao.CANCELADO);

        transacaoRepository.saveAndFlush(transacao);

        DescricaoDTO descricaoDTO = new DescricaoDTO(
                transacao.getValor(),
                transacao.getDataHora(),
                transacao.getEstabelecimento(),
                transacao.getCodigoAutorizacao(),
                transacao.getCodigoNsu(),
                transacao.getStatus()
        );

        FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO(
                transacao.getTipoPagamento(), transacao.getParcelas()
        );

        return new TransacaoResponseDTO(
                transacao.getNumeroCartao(),
                transacao.getId(),
                descricaoDTO,
                formaPagamentoDTO
        );

    }

    public TransacaoResponseDTO consultaTransacaoCancelada(String id){

        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ServicoException(HttpStatus.NOT_FOUND, "Transação inexistente"));

        if(!transacao.getStatus().equals(StatusTransacao.CANCELADO)){
            throw new ServicoException(HttpStatus.NOT_FOUND, "ID de transação não é cancelado");
        }

        DescricaoDTO descricaoDTO = new DescricaoDTO(
                transacao.getValor(),
                transacao.getDataHora(),
                transacao.getEstabelecimento(),
                transacao.getCodigoAutorizacao(),
                transacao.getCodigoNsu(),
                transacao.getStatus()
        );

        FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO(
                transacao.getTipoPagamento(), transacao.getParcelas()
        );

        return new TransacaoResponseDTO(
                transacao.getNumeroCartao(),
                transacao.getId(),
                descricaoDTO,
                formaPagamentoDTO
        );


    }

    public TransacaoResponseDTO consultaTransacaoporId(String id){

        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() ->
                        new ServicoException(HttpStatus.NOT_FOUND, "Transação inexistente"));

        DescricaoDTO descricaoDTO = new DescricaoDTO(
                transacao.getValor(),
                transacao.getDataHora(),
                transacao.getEstabelecimento(),
                transacao.getCodigoAutorizacao(),
                transacao.getCodigoNsu(),
                transacao.getStatus()
        );

        FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO(
                transacao.getTipoPagamento(), transacao.getParcelas()
        );

        return new TransacaoResponseDTO(
                transacao.getNumeroCartao(),
                transacao.getId(),
                descricaoDTO,
                formaPagamentoDTO
        );


    }

    public List<TransacaoResponseDTO> consultaTodasTransacoes(){

        List<Transacao> todasTransacoes = transacaoRepository.findAll();

        List<TransacaoResponseDTO> responseList = new ArrayList<>();

        for (Transacao transacao : todasTransacoes){

            DescricaoDTO descricaoDTO = new DescricaoDTO(
                    transacao.getValor(),
                    transacao.getDataHora(),
                    transacao.getEstabelecimento(),
                    transacao.getCodigoAutorizacao(),
                    transacao.getCodigoNsu(),
                    transacao.getStatus()
            );

            FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO(
                    transacao.getTipoPagamento(),
                    transacao.getParcelas()
            );

            TransacaoResponseDTO responseDTO = new TransacaoResponseDTO(
                    transacao.getNumeroCartao(),
                    transacao.getId(),
                    descricaoDTO,
                    formaPagamentoDTO
            );

            responseList.add(responseDTO);
        }

        return responseList;
    }





    public void validarTransacaoPagamento(BigDecimal valor, Integer parcela, String id){

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServicoException(HttpStatus.BAD_REQUEST, "Valor deve ser maior que zero!");
        }

        if (parcela == null || parcela <= 0) {
            throw new ServicoException(HttpStatus.BAD_REQUEST, "Parcela deve ser maior que zero!");
        }

        if(transacaoRepository.existsById(id)){
            throw new ServicoException(HttpStatus.BAD_REQUEST, "Transação já existente!");
        }

    }






}
