package com.stockathings.StockaThings.controllers;

import com.stockathings.StockaThings.domain.user.AuthenticationDTO;
import com.stockathings.StockaThings.domain.user.LoginResponseDTO;
import com.stockathings.StockaThings.domain.user.RegisterDTO;
import com.stockathings.StockaThings.domain.user.User;
import com.stockathings.StockaThings.infra.security.TokenService;
import com.stockathings.StockaThings.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.getToken((User) auth.getPrincipal());

        return  ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().body("Ja existe um login com esse email !");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());

        User newUser = new User(data.login(), encryptedPassword, data.nome(),
                data.cnpj(), data.nomeSocial(), data.cidade(), data.estado(),
                data.cep(), data.endereco(), data.numEndereco(), data.bairro(),
                data.telefone());

        this.repository.save(newUser);

        return ResponseEntity.ok("Conta criada com sucesso");
    }
}
