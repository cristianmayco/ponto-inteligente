package com.pontointeligente.pontointeligente.api.controller;

import com.pontointeligente.pontointeligente.api.dto.FuncionarioDto;
import com.pontointeligente.pontointeligente.api.model.Funcionario;
import com.pontointeligente.pontointeligente.api.response.Response;
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
@RequestMapping(path = "/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    public FuncionarioController() {
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<FuncionarioDto>> atualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody FuncionarioDto funcionarioDto,
            BindingResult result) throws NoSuchAlgorithmException {

        log.info("Atualizando funcionário: {}", funcionarioDto.toString());
        Response<FuncionarioDto> response = new Response<FuncionarioDto>();

        Optional<Funcionario> funcionario = this.funcionarioService.buscaPorId(id);
        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("funcionario", "Funcionãrio não encontrado"));
        }

        this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);

        if (result.hasErrors()) {
            log.error("Erro ao validar dados do funcionário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.funcionarioService.persistir(funcionario.get());
        response.setData(this.converterFuncionarioDto(funcionario.get()));

        return ResponseEntity.ok(response);
    }

    private void atualizarDadosFuncionario(Funcionario funcionario,
                                           FuncionarioDto funcionarioDto,
                                           BindingResult result) throws NoSuchAlgorithmException {

        funcionario.setNome(funcionarioDto.getNome());

        if (!funcionario.getEmail().equals(funcionario.getEmail())) {
            this.funcionarioService.buscaPorEmail(funcionarioDto.getEmail())
                    .ifPresent(func -> result.addError(new ObjectError("email", "Email já existe")));
            funcionario.setEmail(funcionarioDto.getEmail());
        }

        funcionario.setQtdHorasAlmoco(null);
        funcionarioDto.getQtdHorasAlmoco().
                ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));

        funcionario.setQtdHorasTrabalhadaDia(null);
        funcionarioDto.getQtdHorasTrabalhadaDia()
                .ifPresent(qtdHoras -> funcionario.setQtdHorasTrabalhadaDia(Float.valueOf(qtdHoras)));

        funcionario.setValorHora(null);
        funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));

        if (!funcionarioDto.getSenha().isPresent()) {
            funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
        }

    }

    private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        funcionarioDto.setId(funcionario.getId());
        funcionarioDto.setEmail(funcionario.getEmail());
        funcionarioDto.setNome(funcionario.getNome());

        Optional<Float> qtdHoras = Optional.ofNullable(funcionario.getQtdHorasAlmoco());
        qtdHoras.ifPresent(horas -> funcionarioDto.setQtdHorasAlmoco(Optional.of(horas.toString())));

        qtdHoras = Optional.ofNullable(funcionario.getQtdHorasTrabalhadaDia());
        qtdHoras.ifPresent(horas -> funcionarioDto.setQtdHorasTrabalhadaDia(Optional.of(horas.toString())));

        Optional<BigDecimal> valorHora = Optional.ofNullable(funcionario.getValorHora());
        valorHora.ifPresent(valor -> funcionarioDto.setValorHora(Optional.of(valor.toString())));


        return funcionarioDto;
    }
}
