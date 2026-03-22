package com.proxies.client.controller;

import java.util.List;

import com.proxies.client.dto.ProxyDto;
import com.proxies.client.service.ProxyService;
import com.proxies.client.util.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proxies")
public class ProxyController {
    @Autowired
    private ProxyService service;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> proxies() {
        final ApiResponse<List<ProxyDto>> res = new ApiResponse<>();

        try {

            res.setOk(true);
            res.setMessage("Success");
            res.setItem(this.service.getAllProxies());

            return ResponseEntity.ok(res);

        } catch(Exception e) {

            res.setOk(false);
            res.setMessage("Error: " + e.getMessage());
            res.setItem(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(res);
        }
    }
}
