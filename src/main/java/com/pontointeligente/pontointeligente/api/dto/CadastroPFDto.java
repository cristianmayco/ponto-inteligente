package com.pontointeligente.pontointeligente.api.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class CadastroPFDto {

    private Long id;
    @NotEmpty(message = "Nome não pode ser vazio")
    @Length(min = 3, max = 200, message = "Nome deve conter entre 3 a 200 caracteres")
    private String nome;
    @NotEmpty(message = "Email não pode ser vazio")
    @Length(min = 5, max = 200, message = "Email deve conter entre 5 a 200 caracteres")
//    @Email(message = "Email inválido")
    private String email;
    @NotEmpty(message = "Senha não pode ser vazia")
    private String senha;
    @NotEmpty(message = "CPF não pode ser vazio")
//    @CPF(message = "CPF inválido")
    private String cpf;
    private Optional<String> valorHora = Optional.empty();
    private Optional<String> qtdHorasTrabalhadaDia = Optional.empty();
    private Optional<String> qtdHorasAlmoco = Optional.empty();
    @NotEmpty(message = "CNPJ não pode ser vazio")
//    @CNPJ(message = "CNPJ inválido")
    private String cnpj;

    public CadastroPFDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Optional<String> getValorHora() {
        return valorHora;
    }

    public void setValorHora(Optional<String> valorHora) {
        this.valorHora = valorHora;
    }

    public Optional<String> getQtdHorasTrabalhadaDia() {
        return qtdHorasTrabalhadaDia;
    }

    public void setQtdHorasTrabalhadaDia(Optional<String> qtdHorasTrabalhadaDia) {
        this.qtdHorasTrabalhadaDia = qtdHorasTrabalhadaDia;
    }

    public Optional<String> getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "CadastroPFDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", valorHora=" + valorHora +
                ", qtdHorasTrabalhadaDia=" + qtdHorasTrabalhadaDia +
                ", qtdHorasAlmoco=" + qtdHorasAlmoco +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}