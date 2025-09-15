package com.example.tradestore.model;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Document
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String tradeId;

    public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCounterPartyId() {
		return counterPartyId;
	}

	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public LocalDate getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(LocalDate maturityDate) {
		this.maturityDate = maturityDate;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	private int version;
    private String counterPartyId;
    private String bookId;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    private boolean expired;

    public Trade(String counterPartyId, String bookId, LocalDate maturityDate, LocalDate createdDate, int version) {
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdDate = createdDate;
        this.version = version;
        this.expired = false;
    }

    // Getters and Setters
    // equals and hashCode based on tradeId and version
}
