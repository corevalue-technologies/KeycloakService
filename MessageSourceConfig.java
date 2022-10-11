package com.p3ngine.br.aimservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class MessageSourceConfig {

    @Bean
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultLocale(Locale.ENGLISH);
        return source;
    }

    @Bean
    public LocalValidatorFactoryBean validator(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
