package com.stockathings.StockaThings.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TenantRlsAspect {

    private final TenantIdHolder tenantIdHolder;
    private final TenantRlsConfigurer rls;

    @org.aspectj.lang.annotation.Around("@annotation(org.springframework.transaction.annotation.Transactional) || within(@org.springframework.transaction.annotation.Transactional *)")
    public Object aroundTx(org.aspectj.lang.ProceedingJoinPoint pjp) throws Throwable {
        var id = tenantIdHolder.get();
        if (id != null) {
            rls.apply(id);
        }
        return pjp.proceed();
    }
}
