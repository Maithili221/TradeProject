package com.example.tradestore.service;

import com.example.tradestore.exception.TradeException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TradeServiceTest {
    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTrade() {
        Trade trade = new Trade("CP1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), 1);
        when(tradeRepository.existsByTradeIdAndVersion("T1", 1)).thenReturn(false);
        tradeService.saveTrade(trade);
        verify(tradeRepository).save(trade);
    }

    @Test
    void testSaveTradeWithLowerVersion() {
        Trade trade = new Trade("CP1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), 0);
        when(tradeRepository.existsByTradeIdAndVersion("T1", 1)).thenReturn(false);
        when(tradeRepository.findByTradeId("T1")).thenReturn(new Trade("CP1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), 1));
        doThrow(new TradeException("Trade with lower version cannot be accepted")).when(tradeRepository).save(trade);
        tradeService.saveTrade(trade);
    }

    @Test
    void testInvalidMaturityDate() {
        Trade trade = new Trade("CP1", "B1", LocalDate.now().minusDays(1), LocalDate.now(), 1);
        doThrow(new TradeException("Maturity date cannot be earlier than today's date.")).when(tradeService).saveTrade(trade);
    }


}
