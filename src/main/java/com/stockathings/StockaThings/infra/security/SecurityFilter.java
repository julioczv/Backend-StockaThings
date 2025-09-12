package com.stockathings.StockaThings.infra.security;

import com.stockathings.StockaThings.domain.user.User;
import com.stockathings.StockaThings.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Component
//herda essa OncePerRequestFilter que faz a verificação do token, ele descriptografa o has verifica
// a senha se está correta e depois faz a liberação da autenticação
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private DataSource dataSource;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String p = request.getServletPath();
        return p.startsWith("/auth/") || p.startsWith("/api/auth/") || "/error".equals(p);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String subject = tokenService.validateToken(token);
            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = userRepository.findByLogin(subject);

                if (user != null) {
                    var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    if(user instanceof User u && u.getId() != null) {
                        Connection conn = null;
                        try{
                            conn = DataSourceUtils.getConnection(dataSource);
                            try (PreparedStatement ps = conn.prepareStatement(
                                    "select set_config('app.current_usuario', ?, false)")) {
                                ps.setString(1, u.getId().toString());
                                ps.execute();
                            }
                        } catch (Exception e) {
                            throw new ServletException("Falha ao propagar tenant para RLS", e);
                        } finally {
                            DataSourceUtils.releaseConnection(conn, dataSource);
                        }

                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) return null;
        return header.substring(7).trim();
    }

    private String recoveryToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer", ""); //Bearer token faz a identificação do token
    }
}
