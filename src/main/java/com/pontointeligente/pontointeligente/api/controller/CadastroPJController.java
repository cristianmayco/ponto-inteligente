package com.pontointeligente.pontointeligente.api.controller;

import com.pontointeligente.pontointeligente.api.dto.CadastroPJDto;
import com.pontointeligente.pontointeligente.api.enums.Perfil;
import com.pontointeligente.pontointeligente.api.model.Empresa;
import com.pontointeligente.pontointeligente.api.model.Funcionario;
import com.pontointeligente.pontointeligente.api.response.Response;
import com.pontointeligente.pontointeligente.api.service.EmpresaService;
import com.pontointeligente.pontointeligente.api.service.FuncionarioService;
import com.pontointeligente.pontointeligente.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;

    public CadastroPJController() {
    }

    @PostMapping
    public ResponseEntity<Response<CadastroPJDto>> cadastrar(
            @Valid @RequestBody CadastroPJDto cadastroPJDto,
            BindingResult result) throws NoSuchAlgorithmException {

        log.info("Cadastrando PJ: {}", cadastroPJDto.toString());
        Response<CadastroPJDto> response = new Response<CadastroPJDto>();

        validarDadosExistentes(cadastroPJDto, result);
        Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
        Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto);

        if (result.hasErrors()) {
            log.error("Erro ao validar dados do cadastro PJ: {}", result.getAllErrors());

            for(ObjectError error : result.getAllErrors()){
                response.getErros().add(error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(response);
        }

        this.empresaService.persistir(empresa);
        funcionario.setEmpresa(empresa);
        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterCadastroPJDto(funcionario));

        return ResponseEntity.ok(response);
    }

    private void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult result) {
        this.empresaService.buscaPorCnpj(cadastroPJDto.getCnpj())
                .ifPresent(emp -> result.addError(
                        new ObjectError("empresa", "Empresa já existe")
                ));

        this.funcionarioService.buscaPorCpf(cadastroPJDto.getCpf())
                .ifPresent(func -> result.addError(
                        new ObjectError("funcionario", "CPF já existe")
                ));

        this.funcionarioService.buscaPorEmail(cadastroPJDto.getEmail())
                .ifPresent(func -> result.addError(
                        new ObjectError("funcionario", "Email já cadastrado")
                ));
    }

    private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPJDto.getCnpj());
        empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());

        return empresa;
    }

    private Funcionario converterDtoParaFuncionario(
            CadastroPJDto cadastroPJDto) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setPerfil(Perfil.ROLE_ADMIN);
        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));

        return funcionario;
    }

    private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
        CadastroPJDto cadastroPJDto = new CadastroPJDto();
        cadastroPJDto.setId(funcionario.getId());
        cadastroPJDto.setNome(funcionario.getNome());
        cadastroPJDto.setEmail(funcionario.getEmail());
        cadastroPJDto.setCpf(funcionario.getCpf());
        cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        cadastroPJDto.setCpf(funcionario.getEmpresa().getCnpj());

        return cadastroPJDto;
    }
}
