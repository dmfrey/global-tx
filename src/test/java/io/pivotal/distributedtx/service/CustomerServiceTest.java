package io.pivotal.distributedtx.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import io.pivotal.distributedtx.dao.AddressDao;
import io.pivotal.distributedtx.dao.CustomerDao;
import io.pivotal.distributedtx.model.Address;
import io.pivotal.distributedtx.model.Customer;

public class CustomerServiceTest {

	@Rule    
	public ExpectedException exception = ExpectedException.none();

	@Mock
	private CustomerDao customerDao;
	
	@Mock
	private AddressDao addressDao;

	private CustomerService customerService;

	@Before
	public void setup() throws Exception {
		
		MockitoAnnotations.initMocks( this );
		
		customerService = new CustomerService( customerDao, addressDao );
	}
	
	@Test
	public void testAddCustomer() throws Exception {
	
		Customer customer = new Customer();
		customer.setId( 1 );
		customer.setName( "test" );		

		given( this.customerDao.addCustomer( any( Customer.class ) ) ).willReturn( customer );
		given( this.addressDao.addAddress( any( Customer.class ), any( Address.class ) ) ).willReturn( new Address() );
		
		Customer created = customerService.addCustomer( customer, new Address() );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), not( nullValue() ) );
		assertThat( 1, equalTo( created.getId() ) );
		assertThat( "test", equalTo( created.getName() ) );

		verify( customerDao ).addCustomer( customer );
		verify( addressDao ).addAddress( customer, new Address() );
		
	}
	
	@Test
	public void testAddCustomerDataAccessException() throws Exception {
		
		given( this.customerDao.addCustomer( any( Customer.class ) ) ).willThrow( new InvalidDataAccessApiUsageException( "could not add customer" ) );

		exception.expect( DataAccessException.class );
		customerService.addCustomer( new Customer(), new Address() );
		
		verify( customerService ).addCustomer( new Customer(), new Address() );

	}

	@Test
	public void testGetCustomer() throws Exception {
		
		Customer customer = new Customer();
		customer.setId( 2 );
		customer.setName( "found" );
		
		given( this.customerDao.getCustomer( any( Integer.class ) ) ).willReturn( customer );

		Customer found = customerService.getCustomer( 2 );
		assertThat( found, not( nullValue() ) );
		assertThat( found.getId(), not( nullValue() ) );
		assertThat( "found", equalTo( found.getName() ) );
		
		verify( customerDao ).getCustomer( 2 );

	}

	@Test
	public void testGetCustomerDataAccessException() throws Exception {
		
		given( this.customerDao.getCustomer( any( Integer.class ) ) ).willThrow( new InvalidDataAccessApiUsageException( "could not add customer" ) );

		exception.expect( DataAccessException.class );
		customerService.getCustomer( 1 );
		
	}

}
