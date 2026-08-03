package com.apps.quantitymeasurement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    @GetMapping(value = {
            "/",
            "/{path:^(?!api|swagger-ui|api-docs|h2-console|assets|index\\.html).*$}"
    })
    public String forward() {
        return "forward:/index.html";
    }
}