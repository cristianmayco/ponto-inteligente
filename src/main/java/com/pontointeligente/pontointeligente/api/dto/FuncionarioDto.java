package com.pontointeligente.pontointeligente.api.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class FuncionarioDto {

    private Long id;
    @NotEmpty
    @Length(min = 3, max = 200, message = "Nome deve conter entre 3 a 200 caracters")
    private String nome;
    @NotEmpty
    @Length(min = 5, max = 200, message = "Email deve conter entre 5 a 200 caracteres")
//    @Email(message = "Email inválido")
    private String email;
    private Optional<String> senha = Optional.empty();
    private Optional<String> valorHora = Optional.empty();
    private Optional<String> qtdHorasTrabalhadaDia = Optional.empty();
    private Optional<String> qtdHorasAlmoco = Optional.empty();

    public FuncionarioDto() {
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

    public Optional<String> getSenha() {
        return senha;
    }

    public void setSenha(Optional<String> senha) {
        this.senha = senha;
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

    @Override
    public String toString() {
        return "FuncionarioDto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha=" + senha +
                ", valorHora=" + valorHora +
                ", qtdHorasTrabalhadaDia=" + qtdHorasTrabalhadaDia +
                ", qtdHorasAlmoco=" + qtdHorasAlmoco +
                '}';
    }
}
