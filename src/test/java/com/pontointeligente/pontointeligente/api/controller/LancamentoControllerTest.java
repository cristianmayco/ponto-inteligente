package com.pontointeligente.pontointeligente.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pontointeligente.pontointeligente.api.dto.LancamentoDto;
import com.pontointeligente.pontointeligente.api.enums.Tipo;
import com.pontointeligente.pontointeligente.api.model.Funcionario;
import com.pontointeligente.pontointeligente.api.model.Lancamento;
import com.pontointeligente.pontointeligente.api.service.FuncionarioService;
import com.pontointeligente.pontointeligente.api.service.LancamentoService;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class LancamentoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FuncionarioService funcionarioService;

    @MockBean
    private LancamentoService lancamentoService;

    private static final String URL_BASE = "/api/lancamentos/";
    private static final Long ID_FUNCIONARIO = 1L;
    private static final Long ID_LANCAMENTO = 1L;
    private static final String TIPO = Tipo.INICIO_TRABALHO.name();
    private static final Date DATA = new Date();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Lancamento obterDadosLancamento() {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(ID_LANCAMENTO);
        lancamento.setData(DATA);
        lancamento.setTipo(Tipo.valueOf(TIPO));
        lancamento.setFuncionario(new Funcionario());
        lancamento.getFuncionario().setId(ID_FUNCIONARIO);

        return lancamento;
    }

    private String obterJsonRequisicao() throws JsonProcessingException {
        LancamentoDto lancamentoDto = new LancamentoDto();
        lancamentoDto.setId(null);
        lancamentoDto.setData(this.dateFormat.format(DATA));
        lancamentoDto.setTipo(TIPO);
        lancamentoDto.setFuncionarioId(ID_FUNCIONARIO);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(lancamentoDto);
    }

    @Test
    public void testCadastrarLancamento() throws Exception {
        Lancamento lancamento = obterDadosLancamento();
        BDDMockito.given(this.funcionarioService.buscaPorId(Mockito.anyLong()))
                .willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.lancamentoService.persistir(Mockito.any(Lancamento.class)))
                .willReturn(lancamento);

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.obterJsonRequisicao())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID_LANCAMENTO))
                .andExpect(jsonPath("$.data.tipo").value(TIPO))
                .andExpect(jsonPath("$.data.data").value(this.dateFormat.format(DATA)))
                .andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
                .andExpect(jsonPath("$.erros").isEmpty());

    }

    @Test
    public void testCadastrarLancamentoFuncionarioIdInvalido() throws Exception {
        BDDMockito.given(this.funcionarioService.buscaPorId(Mockito.anyLong()))
                .willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(this.obterJsonRequisicao())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros")
                        .value("Funcionário não encontrado. Id não existe"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void testRemoverLancamento() throws Exception {
        BDDMockito.given(this.lancamentoService.buscaPorId(Mockito.anyLong()))
                .willReturn(Optional.of(new Lancamento()));

        mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LANCAMENTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
