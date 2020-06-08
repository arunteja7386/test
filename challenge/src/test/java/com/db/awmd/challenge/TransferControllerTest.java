package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.service.AccountsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TransferControllerTest {
	private MockMvc mockMvc;

	  @Autowired
	  private AccountsService accountsService;

	  @Autowired
	  private WebApplicationContext webApplicationContext;

	  @Before
	  public void prepareMockMvc() {
	    this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

	    // Reset the existing accounts before each test.
	    accountsService.getAccountsRepository().clearAccounts();
	  }

	  @Test
	  public void createAccount() throws Exception {
	    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
	      .content("{\"accountId\":\"Id-111\",\"balance\":3000}")).andExpect(status().isCreated());
        
	    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
	    	      .content("{\"accountId\":\"Id-222\",\"balance\":2000}")).andExpect(status().isCreated());
	    
	    this.mockMvc.perform(post("/v1/accounts").contentType(MediaType.APPLICATION_JSON)
	    	      .content("{\"accountId\":\"Id-333\",\"balance\":0}")).andExpect(status().isCreated());
	    
	    
	    
	    Account account = accountsService.getAccount("Id-111");
	    assertThat(account.getAccountId()).isEqualTo("Id-111");
	    assertThat(account.getBalance()).isEqualByComparingTo("3000");
	    
	    Account anotherAccount = accountsService.getAccount("Id-222");
	    assertThat(anotherAccount.getAccountId()).isEqualTo("Id-222");
	    assertThat(anotherAccount.getBalance()).isEqualByComparingTo("2000");
	  }
	  
	  @Test
	  public void transferSuccess() throws Exception {
		  this.mockMvc.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"accountFromId\":\"Id-111\",\"accountToId\":\"Id-222\",\"amountTotransfer\":3000}")).andExpect(status().isOk());
	  }
	  
	  @Test
	  public void transferNotSuccess() throws Exception {
		  this.mockMvc.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"accountFromId\":\"Id-333\",\"accountToId\":\"Id-222\",\"amountTotransfer\":1000}")).andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  public void transferNotSuccessSameAccount() throws Exception {
		  this.mockMvc.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"accountFromId\":\"Id-222\",\"accountToId\":\"Id-222\",\"amountTotransfer\":1000}")).andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  public void transferNotSuccessNoSufficientAmount() throws Exception {
		  this.mockMvc.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"accountFromId\":\"Id-222\",\"accountToId\":\"Id-222\",\"amountTotransfer\":1000}")).andExpect(status().isBadRequest());
	  }
	  
	  @Test
	  public void transferNotSuccessNegativeTransferAmount() throws Exception {
		  this.mockMvc.perform(post("/v1/transfer").contentType(MediaType.APPLICATION_JSON)
			      .content("{\"accountFromId\":\"Id-111\",\"accountToId\":\"Id-222\",\"amountTotransfer\":-1000}")).andExpect(status().isBadRequest());
	  }

}
