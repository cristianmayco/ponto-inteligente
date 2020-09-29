package com.pontointeligente.pontointeligente.api.repository;

import com.pontointeligente.pontointeligente.api.enums.Perfil;
import com.pontointeligente.pontointeligente.api.enums.Tipo;
import com.pontointeligente.pontointeligente.api.model.Empresa;
import com.pontointeligente.pontointeligente.api.model.Funcionario;
import com.pontointeligente.pontointeligente.api.model.Lancamento;
import com.pontointeligente.pontointeligente.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    private Long funcionarioId;

    private Empresa obterEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa Test");
        empresa.setCnpj("558888");

        return empresa;
    }

    public Funcionario obterFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf("123456");
        funcionario.setEmail("test@test.com");
        funcionario.setNome("Funcionario");
        funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
        funcionario.setPerfil(Perfil.ROLE_USUARIO);

        return funcionario;
    }

    public Lancamento obterLancamento(Funcionario funcionario) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao("Descricao de lancamento teste");
        lancamento.setLocalizacao("Em casa");
        lancamento.setTipo(Tipo.INICIO_TRABALHO);
        lancamento.setFuncionario(funcionario);
        return lancamento;
    }

    @Before
    public void setUp() {
        Empresa empresa = this.empresaRepository.save(obterEmpresa());

        Funcionario funcionario = obterFuncionario();
        funcionario.setEmpresa(empresa);
        funcionario = this.funcionarioRepository.save(funcionario);
        this.funcionarioId = funcionario.getId();

        this.lancamentoRepository.save(obterLancamento(funcionario));
        this.lancamentoRepository.save(obterLancamento(funcionario));
        this.lancamentoRepository.save(obterLancamento(funcionario));

    }

    @After
    public void tearDown() throws Exception {
        this.lancamentoRepository.deleteAll();
    }

    @Test
    public void testBuscarLancamentosPorFuncionario() {
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(this.funcionarioId);

        Assert.assertEquals(3,
                lancamentos.size());
    }

    @Test
    public void testBuscaLancamentosPorFuncionarioPaginado() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(this.funcionarioId, pageable);

        Assert.assertEquals(3, lancamentos.getTotalElements());
    }
}
