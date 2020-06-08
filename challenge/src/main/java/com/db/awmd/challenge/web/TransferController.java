package com.db.awmd.challenge.web;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.awmd.challenge.domain.Transfer;
import com.db.awmd.challenge.exception.TransferException;
import com.db.awmd.challenge.service.TransferService;

@RestController
@RequestMapping("/v1/transfer")
@Slf4j
public class TransferController {
	
	private final TransferService transferService;
	
	@Autowired
	  public TransferController(TransferService transferService) {
	    this.transferService = transferService;
	  }

	  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Object> transferAmount(@RequestBody @Valid Transfer transfer) {
	    log.info("transfering amount {}", transfer);
	    try{
	    transferService.transferAmount(transfer);
	    }
	    catch(TransferException e){
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	     return new ResponseEntity<>(HttpStatus.OK);
	  }


}
