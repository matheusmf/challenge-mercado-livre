package br.com.matheusmf.challenge.application.rest;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class PageModule extends SimpleModule {
    private static final long serialVersionUID = 1L;

    public PageModule() {
        addDeserializer(Page.class, new PageDeserializer());
    }
}
