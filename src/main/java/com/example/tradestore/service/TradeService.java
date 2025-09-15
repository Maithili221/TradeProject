package com.example.tradestore.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tradestore.exception.TradeException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.repository.TradeRepository;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    public void saveTrade(Trade trade) {
        validateTrade(trade);
        if (tradeRepository.existsByTradeIdAndVersion(trade.getTradeId(), trade.getVersion())) {
            tradeRepository.save(trade);
        } else if (trade.getVersion() > tradeRepository.findByTradeId(trade.getTradeId()).getVersion()) {
            tradeRepository.save(trade);
        } else {
            throw new TradeException("Trade with lower version cannot be accepted");
        }
    }
    
    public void markExpiredTrades() {
        tradeRepository.findAll().forEach(trade -> {
            if (trade.isExpired()) {
                trade.setExpired(true);
                tradeRepository.save(trade);
            }
        });
    }

    private void validateTrade(Trade trade) {
        if (trade.getMaturityDate().isBefore(LocalDate.now())) {
            throw new TradeException("Maturity date cannot be in the past");
        }
        if (trade.getMaturityDate().isAfter(LocalDate.now())) {
            trade.setExpired(true);
        }
    }
}
