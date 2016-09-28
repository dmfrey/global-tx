package io.pivotal.distributedtx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.pivotal.distributedtx.model.Address;
import io.pivotal.distributedtx.model.Customer;

@Repository
public class AddressDao {

	final String INSERT_SQL = "insert into address (address1, address2, city, state, zip, customer_id) values(?,?,?,?,?,?)";

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public AddressDao( @Qualifier( "jdbcTemplateTwo" ) final JdbcTemplate jdbcTemplateTwo ) {
		
		this.jdbcTemplate = jdbcTemplateTwo;
		
	}
	
	@Transactional( )
	public Address addAddress( final Customer customer, Address address ) {
		
		address.setCustomerId( customer.getId() );
		
		final Address newAddress = address;
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement( Connection connection ) throws SQLException {
		            
		        	PreparedStatement ps = connection.prepareStatement( INSERT_SQL, new String[] { "id" } );
		            ps.setString( 1, newAddress.getAddress1() );
		            ps.setString( 2, newAddress.getAddress2() );
		            ps.setString( 3, newAddress.getCity() );
		            ps.setString( 4, newAddress.getState() );
		            ps.setString( 5, newAddress.getZip() );
		            ps.setInt( 6, customer.getId() );
		            
		            return ps;
		        }
		    },
		    keyHolder );
		
		address.setId( keyHolder.getKey().intValue() );
		
		return address;
	}

	@Transactional( readOnly = true )
	public Address getAddress( final Integer addressId ) {
		
		return jdbcTemplate.queryForObject( "SELECT * FROM ADDRESS WHERE ID = ?", new Object[] { addressId }, rowMapper );
	}
	
	private RowMapper<Address> rowMapper = new RowMapper<Address>() {

		@Override
		public Address mapRow( ResultSet rs, int rowNum ) throws SQLException {
			
			Address address = new Address();
			address.setId( rs.getInt( 1 ) );
			address.setAddress1( rs.getString( 2 ) );
			address.setAddress2( rs.getString( 3 ) );
			address.setCity( rs.getString( 4 ) );
			address.setState( rs.getString( 5 ) );
			address.setZip( rs.getString( 6 ) );
			address.setCustomerId( rs.getInt( 7 ) );
			
			return address;
		}
		
	};
	
}
