package com.stockathings.StockaThings.infra.security;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@EnableAspectJAutoProxy
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE) // aplica a variável antes de rodar a lógica do método
public class TenantRlsAspect {

    private final TenantIdHolder tenantIdHolder;
    private final TenantRlsConfigurer rls;

    @Around("@annotation(org.springframework.transaction.annotation.Transactional) || " +
            "within(@org.springframework.transaction.annotation.Transactional *)")
    public Object aroundTx(ProceedingJoinPoint pjp) throws Throwable {
        var id = tenantIdHolder.get();
        if (id != null) {
            rls.apply(id);
        }
        return pjp.proceed();
    }
}
