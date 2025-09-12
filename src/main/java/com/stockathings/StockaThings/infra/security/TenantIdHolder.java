package com.stockathings.StockaThings.infra.security;


import org.springframework.stereotype.Component;

@Component
public class TenantIdHolder {
    private static final ThreadLocal<java.util.UUID> TL = new ThreadLocal<>();

    public void set(java.util.UUID id) {
        TL.set(id);
    }

    public java.util.UUID get() {
        return TL.get();
    }

    public void clear() {
        TL.remove();
    }
}
