package com.example.tradestore.kafka;

import com.example.tradestore.model.Trade;
import com.example.tradestore.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TradeConsumer {

    @Autowired
    private TradeService tradeService;

    @KafkaListener(topics = "trades", groupId = "trade_group")
    public void consumeTrade(Trade trade) {
        tradeService.saveTrade(trade);
        tradeService.markExpiredTrades();
    }
}