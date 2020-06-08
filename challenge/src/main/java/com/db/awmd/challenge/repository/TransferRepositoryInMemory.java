package com.db.awmd.challenge.repository;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.exception.TransferException;
import com.db.awmd.challenge.service.AccountsService;
import com.db.awmd.challenge.service.NotificationService;

public class TransferRepositoryInMemory implements TransferRepository {
	@Autowired
	AccountsService accountsService;
	
	@Autowired
	NotificationService notificationService;

	@Override
	public void transferAmount(String accountFromId, String accountToId,
			BigDecimal amountToTransfer) throws TransferException {
		Account fromIdAccountDetails=accountsService.getAccount(accountFromId);
		Account toIdAccountDetails=accountsService.getAccount(accountToId);
		BigDecimal accountFromIdTotalbalance=fromIdAccountDetails.getBalance();
		BigDecimal accountToIdTotalbalance=fromIdAccountDetails.getBalance();
		if(((accountFromIdTotalbalance.compareTo(BigDecimal.ZERO))>0 || accountFromIdTotalbalance.compareTo(BigDecimal.ZERO)>1) && amountToTransfer.compareTo(BigDecimal.ZERO)>1){
		if(!accountFromId.equals(accountToId) ){//To and from account Id should be different as we cannot transfer money to same account
			if(((accountFromIdTotalbalance.compareTo(amountToTransfer))>0 ||(accountFromIdTotalbalance.compareTo(amountToTransfer))>1 )){
				fromIdAccountDetails.setBalance(accountFromIdTotalbalance.subtract(amountToTransfer));
				toIdAccountDetails.setBalance(accountToIdTotalbalance.add(amountToTransfer));
				notificationService.notifyAboutTransfer(toIdAccountDetails, "Your account has been successfully deposited with amount of "+amountToTransfer +" from "+accountFromId);
				notificationService.notifyAboutTransfer(fromIdAccountDetails, "You have  successfully transferred amount of "+amountToTransfer +"to "+accountToId);
				
			}
			else{
				notificationService.notifyAboutTransfer(fromIdAccountDetails, "Your transfer of  "+amountToTransfer +" failed because your account "+ accountFromId + "has balance "
						+ fromIdAccountDetails.getBalance() + " which is less than "+amountToTransfer);
				throw new TransferException( "Your transfer of  "+amountToTransfer +" failed because your account "+ accountFromId + "has balance "
						+ fromIdAccountDetails.getBalance() + " which is less than "+amountToTransfer);
			}
		}
		else{
			notificationService.notifyAboutTransfer(fromIdAccountDetails, "Your transfer of  "+amountToTransfer +" failed because "+ accountFromId + " "+accountToId + " are same");
			throw new TransferException("Your transfer of  "+amountToTransfer +" failed because "+ accountFromId + " "+accountToId + " are same");
		}
		}
		else{
			notificationService.notifyAboutTransfer(fromIdAccountDetails, "You cannot transfer because your accout balance is "+accountFromIdTotalbalance);//zero balance in account
			throw new TransferException("You cannot transfer because your accout balance is "+accountFromIdTotalbalance);
		}
				
	}

}
