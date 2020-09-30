package com.pontointeligente.pontointeligente.api.controller;

import com.pontointeligente.pontointeligente.api.model.Empresa;
import com.pontointeligente.pontointeligente.api.service.EmpresaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters=false)
@ActiveProfiles("test")
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaService empresaService;

    private static final String BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
    private static final Long ID = Long.valueOf(1);
    private static final String CNPJ = "55855866699";
    private static final String RAZAO_SOCIAL = "Empresa Test Controller";

    @Test
    public void testBuscarEmpresaCnpjInvalido() throws Exception {
        BDDMockito.given(this.empresaService.buscaPorCnpj(Mockito.anyString())).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros").value("Empresa não encontrada com o CNPJ: " + CNPJ));

    }

    @Test
    public void testBuscarEmpresaCnpjValido() throws Exception {
        BDDMockito.given(this.empresaService.buscaPorCnpj(Mockito.anyString()))
                .willReturn(Optional.of(this.obterDadosEmpresa()));

        mockMvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.razaoSocial", equalTo(RAZAO_SOCIAL)))
                .andExpect(jsonPath("$.data.cnpj", equalTo(CNPJ)))
                .andExpect(jsonPath("$.erros").isEmpty());


    }

    private Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(ID);
        empresa.setRazaoSocial(RAZAO_SOCIAL);
        empresa.setCnpj(CNPJ);

        return empresa;
    }
}
