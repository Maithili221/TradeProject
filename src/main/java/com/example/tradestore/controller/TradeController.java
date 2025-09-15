package com.example.tradestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tradestore.exception.TradeException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.service.TradeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping
    public ResponseEntity<String> submitTrade(@Valid @RequestBody Trade trade) {
        try {
            tradeService.saveTrade(trade);
            return ResponseEntity.ok("Trade saved successfully");
        } catch (TradeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
