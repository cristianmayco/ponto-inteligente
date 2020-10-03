package com.pontointeligente.pontointeligente.api.security;

import com.pontointeligente.pontointeligente.api.enums.Perfil;
import com.pontointeligente.pontointeligente.api.model.Funcionario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    /**
     * Converte e gera um JwtUser com base nos dados de um funcionário.
     *
     * @param funcionario
     * @return JwtUser
     */
    public static JwtUser create(Funcionario funcionario) {
        return new JwtUser(funcionario.getId(), funcionario.getEmail(), funcionario.getSenha(),
                mapToGrantedAuthorities(funcionario.getPerfil()));
    }

    /**
     * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
     *
     * @param perfil
     * @return List<GrantedAuthority>
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(Perfil perfil) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(perfil.toString()));
        return authorities;
    }

}
