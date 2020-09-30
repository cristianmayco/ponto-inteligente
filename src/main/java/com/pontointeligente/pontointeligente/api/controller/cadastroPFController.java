package com.pontointeligente.pontointeligente.api.controller;

import com.pontointeligente.pontointeligente.api.dto.CadastroPFDto;
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
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class cadastroPFController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    public cadastroPFController() {
    }

    @PostMapping
    public ResponseEntity<Response<CadastroPFDto>> cadastrar(
            @Valid @RequestBody CadastroPFDto cadastroPFDto, BindingResult result) throws NoSuchAlgorithmException {
        log.info("Cadastrando PF: {}", cadastroPFDto.toString());
        Response<CadastroPFDto> response = new Response<>();

        validarDadosExistentes(cadastroPFDto, result);
        Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPFDto);

        if (result.hasErrors()) {
            log.error("Erro ao validar dados de cadastro de PF: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);

        }

        Optional<Empresa> empresa = this.empresaService.buscaPorCnpj(cadastroPFDto.getCnpj());
        empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterCadastroPFDto(funcionario));

        return ResponseEntity.ok(response);
    }

    private void validarDadosExistentes(CadastroPFDto cadastroPFDto, BindingResult result) {
        Optional<Empresa> empresa = this.empresaService.buscaPorCnpj(cadastroPFDto.getCnpj());
        if (!empresa.isPresent()) {
            result.addError(new ObjectError("empresa", "Empresa não cadastrada"));
        }

        this.funcionarioService.buscaPorCpf(cadastroPFDto.getCpf())
                .ifPresent(func ->
                        result.addError(new ObjectError("funcionario", "CPF já existe")));

        this.funcionarioService.buscaPorEmail(cadastroPFDto.getEmail())
                .ifPresent(func ->
                        result.addError(new ObjectError("funcionario", "Email já existe")));
    }

    private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPFDto) throws NoSuchAlgorithmException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(cadastroPFDto.getNome());
        funcionario.setEmail(cadastroPFDto.getEmail());
        funcionario.setCpf(cadastroPFDto.getCpf());
        funcionario.setPerfil(Perfil.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDto.getSenha()));

        cadastroPFDto.getQtdHorasAlmoco()
                .ifPresent(qtdHorasAlmoco ->
                        funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

        cadastroPFDto.getQtdHorasTrabalhadaDia()
                .ifPresent(qtdHorasTrabalhadaDia ->
                        funcionario.setQtdHorasTrabalhadaDia(Float.valueOf(qtdHorasTrabalhadaDia)));

        cadastroPFDto.getValorHora()
                .ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        return funcionario;
    }

    private CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
        CadastroPFDto cadastroPFDto = new CadastroPFDto();
        cadastroPFDto.setId(funcionario.getId());
        cadastroPFDto.setNome(funcionario.getNome());
        cadastroPFDto.setEmail(funcionario.getEmail());
        cadastroPFDto.setCpf(funcionario.getCpf());
        cadastroPFDto.setCnpj(funcionario.getEmpresa().getCnpj());

        Optional<Float> qtdHoras = Optional.ofNullable(funcionario.getQtdHorasAlmoco());
        qtdHoras.ifPresent(horas -> cadastroPFDto.setQtdHorasAlmoco(Optional.of(horas.toString())));

        qtdHoras = Optional.ofNullable(funcionario.getQtdHorasTrabalhadaDia());
        qtdHoras.ifPresent(horas -> cadastroPFDto.setQtdHorasTrabalhadaDia(Optional.of(horas.toString())));

        Optional<BigDecimal> valorHora = Optional.ofNullable(funcionario.getValorHora());
        valorHora.ifPresent(valor -> cadastroPFDto.setValorHora(Optional.of(valor.toString())));



        return cadastroPFDto;
    }

}
