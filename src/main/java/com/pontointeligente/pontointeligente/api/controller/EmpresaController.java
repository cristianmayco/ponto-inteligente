package com.pontointeligente.pontointeligente.api.controller;

import com.pontointeligente.pontointeligente.api.dto.EmpresaDto;
import com.pontointeligente.pontointeligente.api.model.Empresa;
import com.pontointeligente.pontointeligente.api.response.Response;
import com.pontointeligente.pontointeligente.api.service.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService empresaService;

    public EmpresaController() {
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj){
        log.info("BUscando empresa por CNPJ: {}", cnpj);

        Response<EmpresaDto> response =  new Response<EmpresaDto>();
        Optional<Empresa> empresa =  empresaService.buscaPorCnpj(cnpj);

        if(!empresa.isPresent()){
            log.info("Empresa não encontrada com o CNPJ:{}", cnpj);
            response.getErros().add("Empresa não encontrada com o CNPJ: " +  cnpj);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.converterEmpresaDto(empresa.get()));
        return ResponseEntity.ok(response);
    }

    private EmpresaDto converterEmpresaDto(Empresa empresa){
        EmpresaDto empresaDto =  new EmpresaDto();
        empresaDto.setId(empresa.getId());
        empresaDto.setCnpj(empresa.getCnpj());
        empresaDto.setRazaoSocial(empresa.getRazaoSocial());

        return empresaDto;
    }

}
