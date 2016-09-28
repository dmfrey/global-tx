package io.pivotal.distributedtx.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.distributedtx.model.Address;
import io.pivotal.distributedtx.model.Customer;

@RunWith( SpringRunner.class )
@SpringBootTest
public class CustomerServiceIntegrationTest {

	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testAddCustomer() throws Exception {
	
		Customer customer = new Customer();
		customer.setName( "test" );		

		Address address = new Address();
		address.setAddress1( "address1" );
		address.setAddress2( "address2" );
		address.setCity( "city" );
		address.setState( "state" );
		address.setZip( "zip" );

		Customer created = customerService.addCustomer( customer, address );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), not( nullValue() ) );
		assertThat( "test", equalTo( created.getName() ) );
		
		Customer found = customerService.getCustomer( created.getId() );
		assertThat( found, not( nullValue() ) );
		assertThat( found.getId(), not( nullValue() ) );
		assertThat( "test", equalTo( found.getName() ) );
		
	}
	
}
