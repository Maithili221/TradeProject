package com.example.tradestore.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.tradestore.exception.TradeException;
import com.example.tradestore.model.Trade;
import com.example.tradestore.service.TradeService;
import com.fasterxml.jackson.databind.ObjectMapper;


class TradeControllerTest {
    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    void testSubmitTradeSuccess() throws Exception {
        Trade trade = new Trade("CP1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), 1);
        mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(trade)))
                .andExpect(status().isOk())
                .andExpect(content().string("Trade saved successfully"));
    }

    @Test
    void testSubmitTradeFailure() throws Exception {
        Trade trade = new Trade("CP1", "B1", LocalDate.now().minusDays(1), LocalDate.now(), 1);
        doThrow(new TradeException("Maturity date cannot be in the past")).when(tradeService).saveTrade(any(Trade.class));
        mockMvc.perform(post("/api/trades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(trade)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Maturity date cannot be in the past"));
    }
}
