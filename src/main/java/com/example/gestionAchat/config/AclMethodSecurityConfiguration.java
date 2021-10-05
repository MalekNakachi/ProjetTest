package com.example.gestionAchat.config;



import com.example.gestionAchat.service.CustomPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AclMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    @Autowired
    MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler;
    @Autowired
    AclService aclService;
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
//   return defaultMethodSecurityExpressionHandler;
       DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator(aclService));
        return expressionHandler;
    }
 }


