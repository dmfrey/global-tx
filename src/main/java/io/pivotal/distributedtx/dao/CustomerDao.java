package io.pivotal.distributedtx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.pivotal.distributedtx.model.Customer;

@Repository
public class CustomerDao {

	final String INSERT_SQL = "insert into customer (name) values(?)";
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CustomerDao( final JdbcTemplate jdbcTemplateOne ) {
		
		this.jdbcTemplate = jdbcTemplateOne;
		
	}
	
	public Customer addCustomer( Customer customer ) {
	
		final Customer newCustomer = customer;
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement( Connection connection ) throws SQLException {
		            
		        	PreparedStatement ps = connection.prepareStatement( INSERT_SQL, new String[] { "id" } );
		            ps.setString( 1, newCustomer.getName() );
		            
		            return ps;
		        }
		    },
		    keyHolder );
		
		customer.setId( keyHolder.getKey().intValue() );
		
		return customer;
	}
	
	@Transactional( readOnly = true )
	public Customer getCustomer( final Integer customerId ) {
		
		return jdbcTemplate.queryForObject( "SELECT * FROM CUSTOMER WHERE ID = ?", new Object[] { customerId }, rowMapper );
	}

	@Transactional( readOnly = true )
	public Customer getCustomer( final String name ) {
		
		return jdbcTemplate.queryForObject( "SELECT * FROM CUSTOMER WHERE NAME LIKE ?", new Object[] { "%" + name + "%" }, rowMapper );
	}

	private RowMapper<Customer> rowMapper = new RowMapper<Customer>() {
		
		@Override
		public Customer mapRow( ResultSet rs, int rowNum ) throws SQLException {
			
			Customer customer = new Customer();
			customer.setId( rs.getInt( 1 ) );
			customer.setName( rs.getString( 2 ) );
			
			return customer;
		}

	};
	
}
