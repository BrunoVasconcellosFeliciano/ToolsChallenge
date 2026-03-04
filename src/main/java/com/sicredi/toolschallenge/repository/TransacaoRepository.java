package com.sicredi.toolschallenge.repository;

import com.sicredi.toolschallenge.domain.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransacaoRepository extends JpaRepository<Transacao, String> {

}
