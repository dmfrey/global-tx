package io.pivotal.distributedtx.service;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.distributedtx.dao.AddressDao;
import io.pivotal.distributedtx.dao.CustomerDao;
import io.pivotal.distributedtx.model.Address;
import io.pivotal.distributedtx.model.Customer;

@RunWith( SpringRunner.class )
@SpringBootTest
public class CustomerServiceTest {

	@MockBean
	private CustomerDao customerDao;
	
	@MockBean
	private AddressDao addressDao;

	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testAddCustomer() throws Exception {
	
		given( this.customerDao.addCustomer( any( Customer.class ) ) ).willReturn( new Customer() );
		given( this.addressDao.addAddress( any( Customer.class ), any( Address.class ) ) ).willReturn( new Address() );
		
		Customer created = customerService.addCustomer( new Customer(), new Address() );
		assertThat( created, not( nullValue() ) );

	}
	
}
