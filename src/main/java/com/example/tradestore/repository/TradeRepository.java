package com.example.tradestore.repository;

import com.example.tradestore.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TradeRepository extends JpaRepository<Trade, String>, MongoRepository<Trade, String> {
    boolean existsByTradeIdAndVersion(String tradeId, int version);
    Trade findByTradeId(String tradeId);
}
