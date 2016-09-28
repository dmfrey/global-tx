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

import io.pivotal.distributedtx.model.Address;
import io.pivotal.distributedtx.model.Customer;

public class AddressDaoTest {

	@Rule    
	public ExpectedException exception = ExpectedException.none();

	@Mock
	private AddressDao addressDao;

	@Before
	public void setup() throws Exception {
	
		MockitoAnnotations.initMocks( this );
		
	}
	
	@Test
	public void testAddAddress() throws Exception {
		
		Address address = new Address();
		address.setId( 1 );
		address.setAddress1( "address1" );
		address.setAddress2( "address2" );
		address.setCity( "city" );
		address.setState( "state" );
		address.setZip( "zip" );
		address.setCustomerId( 1 );
		
		given( this.addressDao.addAddress( any( Customer.class ), any( Address.class ) ) ).willReturn( address );

		Address created = addressDao.addAddress( new Customer(), address );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), not( nullValue() ) );
		assertThat( "address1", equalTo( created.getAddress1() ) );
		assertThat( "address2", equalTo( created.getAddress2() ) );
		assertThat( "city", equalTo( created.getCity() ) );
		assertThat( "state", equalTo( created.getState() ) );
		assertThat( "zip", equalTo( created.getZip() ) );
		assertThat( created.getCustomerId(), not( nullValue() ) );
		assertThat( 1, equalTo( created.getCustomerId() ) );
		
	}
	
	@Test
	public void testAddAddressDataAccessException() throws Exception {
		
		given( this.addressDao.addAddress( any( Customer.class ), any( Address.class ) ) ).willThrow( new InvalidDataAccessApiUsageException( "could not add customer" ) );

		exception.expect( DataAccessException.class );
		addressDao.addAddress( new Customer(), new Address() );
		
	}

	@Test
	public void testGetCustomer() throws Exception {
		
		Address address = new Address();
		address.setId( 1 );
		address.setAddress1( "found address1" );
		address.setAddress2( "found address2" );
		address.setCity( "found city" );
		address.setState( "found state" );
		address.setZip( "found zip" );
		address.setCustomerId( 2 );
		
		given( this.addressDao.addAddress( any( Customer.class ), any( Address.class ) ) ).willReturn( address );
		given( this.addressDao.getAddress( any( Integer.class ) ) ).willReturn( address );

		Address created = addressDao.addAddress( new Customer(), address );
		Address found = addressDao.getAddress( created.getId() );
		assertThat( found, not( nullValue() ) );
		assertThat( found.getId(), not( nullValue() ) );
		assertThat( "found address1", equalTo( found.getAddress1() ) );
		assertThat( "found address2", equalTo( found.getAddress2() ) );
		assertThat( "found city", equalTo( found.getCity() ) );
		assertThat( "found state", equalTo( found.getState() ) );
		assertThat( "found zip", equalTo( found.getZip() ) );
		assertThat( found.getCustomerId(), not( nullValue() ) );
		assertThat( 2, equalTo( found.getCustomerId() ) );
		
	}

	@Test
	public void testGetAddressDataAccessException() throws Exception {
		
		given( this.addressDao.getAddress( any( Integer.class ) ) ).willThrow( new InvalidDataAccessApiUsageException( "could not add customer" ) );

		exception.expect( DataAccessException.class );
		addressDao.getAddress( 1 );
		
	}

}
