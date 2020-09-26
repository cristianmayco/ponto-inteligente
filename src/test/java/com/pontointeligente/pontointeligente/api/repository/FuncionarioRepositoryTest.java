package com.pontointeligente.pontointeligente.api.repository;

import com.pontointeligente.pontointeligente.api.enums.Perfil;
import com.pontointeligente.pontointeligente.api.model.Empresa;
import com.pontointeligente.pontointeligente.api.model.Funcionario;
import com.pontointeligente.pontointeligente.api.utils.PasswordUtils;
import org.junit.After;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String EMAIL = "test@test.com";
    private static final String CPF = "45689123";

    private Empresa obterEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa Test");
        empresa.setCnpj("558888");
        return this.empresaRepository.save(empresa);
    }

    @Before
    public void setUp() throws Exception {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF);
        funcionario.setEmail(EMAIL);
        funcionario.setName("Funcionario");
        funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
        funcionario.setEmpresa(obterEmpresa());
        funcionario.setPerdil(Perfil.ROLE_USUARIO);

        this.funcionarioRepository.save(funcionario);
    }

    @After
    public final void tearDown() {
        this.empresaRepository.deleteAll();
        this.funcionarioRepository.deleteAll();
    }

    @Test
    public void testBuscarFuncionarioPorEmail() {
        Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);

        assertEquals(EMAIL, funcionario.getEmail());
    }

    @Test
    public void testBuscarFuncionarioPorCPF() {
        Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);

        assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    public void testBuscarFuncionarioPorEmailOuCpf() {
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, null);
        assertEquals(CPF, funcionario.getCpf());

        funcionario = this.funcionarioRepository.findByCpfOrEmail(null, EMAIL);
        assertEquals(EMAIL, funcionario.getEmail());

        funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
        assertEquals(CPF, funcionario.getCpf());
        assertEquals(EMAIL, funcionario.getEmail());

    }
}
