package com.stockathings.StockaThings.infra.security;

import com.stockathings.StockaThings.domain.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TenantPropagationFilter extends OncePerRequestFilter {

    private final TenantIdHolder tenantIdHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        try {
            UUID tenant = null;

            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof User user) {
                tenant = user.getId();
            }
            if (tenant == null) {
                String h = req.getHeader("X-Tenant-ID");
                if (h != null && !h.isBlank()) tenant = UUID.fromString(h);
            }
            if (tenant != null) tenantIdHolder.set(tenant);
            chain.doFilter(req, res);
        } finally {
            tenantIdHolder.clear();
        }
    }
}
