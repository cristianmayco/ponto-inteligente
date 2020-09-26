package com.pontointeligente.pontointeligente.api.serviceImp;

import com.pontointeligente.pontointeligente.api.model.Funcionario;
import com.pontointeligente.pontointeligente.api.repository.FuncionarioRepository;
import com.pontointeligente.pontointeligente.api.service.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioServiceImp implements FuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImp.class);

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @Override
    public Funcionario persistir(Funcionario funcionario) {
        log.info("Persistindo funcionário: {}", funcionario);

        return this.funcionarioRepository.save(funcionario);
    }

    @Override
    public Optional<Funcionario> buscaPorCpf(String cpf) {
        log.info("Buscando funcionário pelo CPF: {}", cpf);

        return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Funcionario> buscaPorEmail(String email) {
        log.info("Buscando funcionário por email: {}", email);

        return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
    }

    @Override
    public Optional<Funcionario> buscaPorId(Long id) {
        log.info("Buscando funcionário por id: {}", id);

        return this.funcionarioRepository.findById(id);
    }
}
