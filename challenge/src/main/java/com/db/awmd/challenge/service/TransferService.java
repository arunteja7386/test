package com.db.awmd.challenge.service;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.TransferException;
import com.db.awmd.challenge.repository.TransferRepository;

@Service
public class TransferService {
	
	 @Getter
	  private final TransferRepository transferRepository;
	 


	  @Autowired
	  public TransferService (TransferRepository transferRepository) {
	    this.transferRepository = transferRepository;
	  }
	  

	  
	  public void transferAmount(Transfer transfer) throws TransferException {
		    this.transferRepository.transferAmount(transfer.getAccountFromId(), transfer.getAccountToId(), transfer.getAmountTotransfer());
	  }
		  

}
