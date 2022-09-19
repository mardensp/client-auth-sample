package com.example.clientauthsample.controller;

import com.example.clientauthsample.dto.SampleDTO;
import com.example.clientauthsample.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/client")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<SampleDTO> main() {
        SampleDTO response = service.proxyToAuth();

        return ResponseEntity.ok(response);
    }
}
