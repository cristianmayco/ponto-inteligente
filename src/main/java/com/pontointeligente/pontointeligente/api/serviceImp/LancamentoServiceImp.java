package com.pontointeligente.pontointeligente.api.serviceImp;

import com.pontointeligente.pontointeligente.api.model.Lancamento;
import com.pontointeligente.pontointeligente.api.repository.LancamentoRepository;
import com.pontointeligente.pontointeligente.api.service.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoServiceImp implements LancamentoService {

    private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImp.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Page<Lancamento> buscaPorFuncionarioId(Long funcionarioId, Pageable pageable) {
        log.info("Buscando lançamentos para o funcionário de id: {}", funcionarioId);

        return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageable);
    }

    @Override
    public Optional<Lancamento> buscaPorId(Long id) {
        log.info("Buscando um lançamento pelo ID: {}", id);

        return this.lancamentoRepository.findById(id);
    }

    @Override
    public Lancamento persistir(Lancamento lancamento) {
        log.info("Persistindo o lançamento: {}", lancamento);

        return this.lancamentoRepository.save(lancamento);
    }

    @Override
    public void remover(Long id) {
        log.info("Removendo o lançamento:", id);

        this.lancamentoRepository.deleteById(id);
    }
}
