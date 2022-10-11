package com.p3ngine.br.aimservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BRMessageSource {

    @Autowired
    private MessageSource messageSource;

    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    public String getMessage(String code){
        return messageSource.getMessage(code, null, null, LocaleContextHolder.getLocale()==DEFAULT_LOCALE?DEFAULT_LOCALE: null);
    }

    public String getMessage(String code, Object[] args){
        return messageSource.getMessage(code, args, null, LocaleContextHolder.getLocale()==DEFAULT_LOCALE?DEFAULT_LOCALE: null);
    }
}
