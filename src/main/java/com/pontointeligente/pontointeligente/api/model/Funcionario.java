package com.pontointeligente.pontointeligente.api.model;

import com.pontointeligente.pontointeligente.api.enums.Perfil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    @Column(name = "valor_hora")
    private BigDecimal valorHora;
    @Column(name = "qtd_horas_trabalhada_dia")
    private Float qtdHorasTrabalhadaDia;
    @Column(name = "qtd_horas_almoco")
    private Float qtdHorasAlmoco;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;
    @Column(name = "data_criacao")
    private Date dataCriacao;
    @Column(name = "data_atualizacao")
    private Date dataAtualizacao;
    @ManyToOne(fetch = FetchType.EAGER)
    private Empresa empresa;
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lancamento> lancamentos;

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

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public Float getQtdHorasTrabalhadaDia() {
        return qtdHorasTrabalhadaDia;
    }

    public void setQtdHorasTrabalhadaDia(Float qtdHorasTrabalhadaDia) {
        this.qtdHorasTrabalhadaDia = qtdHorasTrabalhadaDia;
    }

    public Float getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Lancamento> getLancamentos() {
        if (this.lancamentos == null) {
            this.lancamentos = new ArrayList<Lancamento>();
        }
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = new Date();
    }

    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        this.dataCriacao = atual;
        this.dataAtualizacao = atual;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", valorHora=" + valorHora +
                ", qtdHorasTrabalhadaDia=" + qtdHorasTrabalhadaDia +
                ", qtdHorasAlmoco=" + qtdHorasAlmoco +
                ", perfil=" + perfil +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", empresa=" + empresa +
                ", lancamentos=" + lancamentos +
                '}';
    }
}
