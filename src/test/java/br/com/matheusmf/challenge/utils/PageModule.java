package br.com.matheusmf.challenge.utils;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class PageModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public PageModule() {
        addDeserializer(Page.class, new PageDeserializer());
    }
}
