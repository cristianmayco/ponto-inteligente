package com.pontointeligente.pontointeligente.api.service;

import com.pontointeligente.pontointeligente.api.model.Funcionario;

import java.util.Optional;

public interface FuncionarioService {

    Funcionario persistir(Funcionario funcionario);

    Optional<Funcionario> buscaPorCpf(String cpf);

    Optional<Funcionario> buscaPorEmail(String email);

    Optional<Funcionario> buscaPorId(Long id);


}
