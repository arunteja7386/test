package com.db.awmd.challenge.domain;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Transfer {
	
	@NotNull
	private String accountFromId;
	
	@NotNull
	private String accountToId;
	
	@NotNull
	@Min(value = 1, message = "Amount to transfer must be positive.")
	private BigDecimal amountTotransfer;
	
	@JsonCreator
	  public Transfer(@JsonProperty("accountFromId") String accountFromId,
	    @JsonProperty("accountToId") String accountToId, @JsonProperty("amountTotransfer") BigDecimal amountTotransfer) {
	    this.accountFromId = accountFromId;
	    this.accountToId = accountToId;
	    this.amountTotransfer = amountTotransfer;
	  }

}
