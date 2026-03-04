package com.sicredi.toolschallenge.service;

import com.sicredi.toolschallenge.domain.entity.Transacao;
import com.sicredi.toolschallenge.domain.enums.StatusTransacao;

public interface AutorizadorService {

    StatusTransacao autorizar(Transacao transacao);
}
