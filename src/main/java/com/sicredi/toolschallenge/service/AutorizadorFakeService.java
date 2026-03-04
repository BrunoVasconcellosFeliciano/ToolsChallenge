package com.sicredi.toolschallenge.service;

import com.sicredi.toolschallenge.domain.entity.Transacao;
import com.sicredi.toolschallenge.domain.enums.StatusTransacao;
import org.springframework.stereotype.Service;

@Service
public class AutorizadorFakeService implements AutorizadorService{


    @Override
    public StatusTransacao autorizar(Transacao transacao) {
        return Math.random() > 0.5 ? StatusTransacao.AUTORIZADO : StatusTransacao.NEGADO;
    }
}
