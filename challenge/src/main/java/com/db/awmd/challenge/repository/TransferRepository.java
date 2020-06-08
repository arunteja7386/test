package com.db.awmd.challenge.repository;

import java.math.BigDecimal;

public interface TransferRepository {
	
	void transferAmount(String accountFromId, String accountToId, BigDecimal amountToTransfer);

}
