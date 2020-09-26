package com.pontointeligente.pontointeligente.api.serviceImp;

import com.pontointeligente.pontointeligente.api.model.Empresa;
import com.pontointeligente.pontointeligente.api.repository.EmpresaRepository;
import com.pontointeligente.pontointeligente.api.service.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpresaServiceImp implements EmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImp.class);

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Optional<Empresa> buscaPorCnpj(String cnpj) {
        log.info("Buscando uma empresa para o cnpj {}", cnpj);

        return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
    }

    @Override
    public Empresa persistir(Empresa empresa) {
        log.info("Persistindo empresa: {}", empresa);

        return this.empresaRepository.save(empresa);
    }
}
