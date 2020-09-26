package com.pontointeligente.pontointeligente.api.service;

import com.pontointeligente.pontointeligente.api.model.Empresa;

import java.util.Optional;

public interface EmpresaService {
    Optional<Empresa> buscaPorCnpj(String cnpj);

    Empresa persistir(Empresa empresa);
}
