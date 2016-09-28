package io.pivotal.distributedtx.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import io.pivotal.distributedtx.model.Customer;

public class CustomerDaoTest {

	@Rule    
	public ExpectedException exception = ExpectedException.none();

	@Mock
	private CustomerDao customerDao;

	@Before
	public void setup() throws Exception {
	
		MockitoAnnotations.initMocks( this );
		
	}
	
	@Test
	public void testAddCustomer() throws Exception {
		
		Customer customer = new Customer();
		customer.setId( 1 );
		customer.setName( "test" );
		
		given( this.customerDao.addCustomer( any( Customer.class ) ) ).willReturn( customer );

		Customer created = customerDao.addCustomer( customer );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), not( nullValue() ) );
		assertThat( "test", equalTo( created.getName() ) );
		
	}
	
	@Test
	public void testAddCustomerDataAccessException() throws Exception {
		
		given( this.customerDao.addCustomer( any( Customer.class ) ) ).willThrow( new InvalidDataAccessApiUsageException( "could not add customer" ) );

		exception.expect( DataAccessException.class );
		customerDao.addCustomer( new Customer() );
		
	}

	@Test
	public void testGetCustomer() throws Exception {
		
		Customer customer = new Customer();
		customer.setId( 2 );
		customer.setName( "found" );
		
		given( this.customerDao.addCustomer( any( Customer.class ) ) ).willReturn( customer );
		given( this.customerDao.getCustomer( any( Integer.class ) ) ).willReturn( customer );

		Customer created = customerDao.addCustomer( customer );
		Customer found = customerDao.getCustomer( created.getId() );
		assertThat( found, not( nullValue() ) );
		assertThat( found.getId(), not( nullValue() ) );
		assertThat( "found", equalTo( found.getName() ) );
		
	}

	@Test
	public void testGetCustomerDataAccessException() throws Exception {
		
		given( this.customerDao.getCustomer( any( Integer.class ) ) ).willThrow( new InvalidDataAccessApiUsageException( "could not add customer" ) );

		exception.expect( DataAccessException.class );
		customerDao.getCustomer( 1 );
		
	}

}
