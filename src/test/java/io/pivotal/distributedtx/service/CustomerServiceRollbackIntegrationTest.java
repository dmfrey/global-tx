package io.pivotal.distributedtx.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.distributedtx.dao.AddressDao;
import io.pivotal.distributedtx.model.Address;
import io.pivotal.distributedtx.model.Customer;

@RunWith( SpringRunner.class )
@SpringBootTest( webEnvironment = WebEnvironment.NONE )
@DirtiesContext
public class CustomerServiceRollbackIntegrationTest {

	@Rule    
	public ExpectedException exception = ExpectedException.none();

	@MockBean
	private AddressDao addressDao;

	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testAddCustomerFailedToCreateCustomer() throws Exception {

		given( this.addressDao.addAddress( any( Customer.class ), any( Address.class ) ) ).willThrow( new InvalidDataAccessApiUsageException( "should start rollback" ) );

		exception.expect( InvalidDataAccessApiUsageException.class );		
		customerService.addCustomer( new Customer(), new Address() );
		
		verify( addressDao ).addAddress( new Customer(), new Address() );
		
	}
	
	@Test
	public void testAddCustomerFailedToLookupCustomer() throws Exception {

		given( this.addressDao.addAddress( any( Customer.class ), any( Address.class ) ) ).willThrow( new InvalidDataAccessApiUsageException( "should start rollback" ) );

		try {
		
			customerService.addCustomer( new Customer(), new Address() );
		
		} catch( InvalidDataAccessApiUsageException e ) { }
		
		exception.expect( EmptyResultDataAccessException.class );		
		customerService.getCustomer( "not found" );

		verify( addressDao ).addAddress( new Customer(), new Address() );

	}

}
