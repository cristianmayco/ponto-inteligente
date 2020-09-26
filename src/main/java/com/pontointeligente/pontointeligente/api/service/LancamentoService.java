package com.pontointeligente.pontointeligente.api.service;

import com.pontointeligente.pontointeligente.api.model.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LancamentoService {

    Page<Lancamento> buscaPorFuncionarioId(Long funcionarioId, Pageable pageable);

    Optional<Lancamento> buscaPorId(Long id);

    Lancamento persistir(Lancamento lancamento);

    void remover(Long id);
}
