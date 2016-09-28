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

import io.pivotal.distributedtx.model.Customer;

@RunWith( SpringRunner.class )
@SpringBootTest
public class CustomerDaoTest {

	@Autowired
	private CustomerDao customerDao;

	@Test
	public void testAddCustomer() throws SQLException {
		
		Customer customer = new Customer();
		customer.setName( "test" );
		
		Customer created = customerDao.addCustomer( customer );
		assertThat( created, not( nullValue() ) );
		assertThat( created.getId(), not( nullValue() ) );
		assertThat( "test", equalTo( created.getName() ) );
		
	}
	
	@Test
	public void testGetCustomer() throws SQLException {
		
		Customer customer = new Customer();
		customer.setName( "found" );
		
		Customer created = customerDao.addCustomer( customer );
		Customer found = customerDao.getCustomer( created.getId() );
		assertThat( found, not( nullValue() ) );
		assertThat( found.getId(), not( nullValue() ) );
		assertThat( "found", equalTo( found.getName() ) );
		
	}

}
