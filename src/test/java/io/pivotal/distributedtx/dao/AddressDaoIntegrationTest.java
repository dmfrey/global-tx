package io.pivotal.distributedtx.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.distributedtx.model.Address;
import io.pivotal.distributedtx.model.Customer;

@RunWith( SpringRunner.class )
@SpringBootTest
public class AddressDaoTest {

	@Autowired
	private AddressDao addressDao;

	@Test
	public void testAddAddress() throws SQLException {
		
		Customer customer = new Customer();
		customer.setId( 1 );
		customer.setName( "test" );		

		Address address = new Address();
		address.setAddress1( "address1" );
		address.setAddress2( "address2" );
		address.setCity( "city" );
		address.setState( "state" );
		address.setZip( "zip" );
		
		Address created = addressDao.addAddress( customer, address );
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
	public void testGetCustomer() throws SQLException {
		
		Customer customer = new Customer();
		customer.setId( 2 );
		customer.setName( "found" );
		
		Address address = new Address();
		address.setAddress1( "found address1" );
		address.setAddress2( "found address2" );
		address.setCity( "found city" );
		address.setState( "found state" );
		address.setZip( "found zip" );

		Address created = addressDao.addAddress( customer, address );
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

}
