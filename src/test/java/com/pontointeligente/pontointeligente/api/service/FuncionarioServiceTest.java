package com.pontointeligente.pontointeligente.api.service;

import com.pontointeligente.pontointeligente.api.model.Funcionario;
import com.pontointeligente.pontointeligente.api.repository.FuncionarioRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
    }

    @Test
    public void testPersistirFuncionario() {
        Funcionario funcionario = this.funcionarioService.persistir(new Funcionario());

        Assert.assertNotNull(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorId() {

        Optional<Funcionario> funcionario = this.funcionarioService.buscaPorId(1L);

        Assert.assertTrue(funcionario.isPresent());
    }

    @Test
    public void testBuscarFuncionarioEmail() {
        Optional<Funcionario> funcionario = this.funcionarioService.buscaPorEmail("test@test.com");

        Assert.assertTrue(funcionario.isPresent());
    }

    @Test
    public void testBuscarFuncionarioCpf() {
        Optional<Funcionario> funcionario = this.funcionarioService.buscaPorCpf("123456789");

        Assert.assertTrue(funcionario.isPresent());
    }

}
