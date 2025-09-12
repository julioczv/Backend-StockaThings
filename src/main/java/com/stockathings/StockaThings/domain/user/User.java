package com.stockathings.StockaThings.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Table(name = "usuario")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {


    @Id
    @GeneratedValue
    @Column(name = "usuario_id")
    private UUID id;

    @Column(name = "login")
    private String login;

    @JsonIgnore
    @Column(name = "senha")
    private String senha;

    @Column(name = "usuario_nome")
    private String nome;

    @Column(name = "usuario_cnpj")
    private String cnpj;

    @Column(name = "usuario_nome_social")
    private String nomeSocial;

    @Column(name = "usuario_cidade")
    private String cidade;

    @Column(name = "usuario_estado")
    private String estado;

    @Column(name = "usuario_cep")
    private String cep;

    @Column(name = "usuario_endereco")
    private String endereco;

    @Column(name = "usuario_numendereco")
    private String numEndereco;

    @Column(name = "usuario_bairro")
    private String bairro;

    @Column(name = "usuario_fone")
    private String telefone;

    public User(String login, String senha, String nome, String cnpj,
                String nomeSocial, String cidade, String estado, String cep, String endereco,
                String numEndereco, String bairro, String telefone) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cnpj = cnpj;
        this.nomeSocial = nomeSocial;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.endereco = endereco;
        this.numEndereco = numEndereco;
        this.bairro = bairro;
        this.telefone = telefone;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
