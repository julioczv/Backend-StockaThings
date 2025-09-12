package com.stockathings.StockaThings.infra.security;

import org.springframework.stereotype.Component;

@Component
public class TenantRlsConfigurer {

    @jakarta.persistence.PersistenceContext
    private jakarta.persistence.EntityManager em;

    public void apply(java.util.UUID tenantId) {
        org.hibernate.Session session = em.unwrap(org.hibernate.Session.class);
        session.doWork(conn -> {
            try (var ps = conn.prepareStatement("set local app.current_usuario = ?")) {
                ps.setString(1, tenantId.toString());
                ps.execute();
            }
        });
    }
}
