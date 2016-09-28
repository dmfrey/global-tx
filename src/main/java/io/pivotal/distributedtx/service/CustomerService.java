package io.pivotal.distributedtx.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.pivotal.distributedtx.dao.AddressDao;
import io.pivotal.distributedtx.dao.CustomerDao;
import io.pivotal.distributedtx.model.Address;
import io.pivotal.distributedtx.model.Customer;

@Service
@Transactional
public class CustomerService {

	private static final Logger log = LoggerFactory.getLogger( CustomerService.class );
	
	private CustomerDao customerDao;
	private AddressDao addressDao;
	
	@Autowired
	public CustomerService( final CustomerDao customerDao, final AddressDao addressDao ) {
		
		this.customerDao = customerDao;
		this.addressDao = addressDao;
		
	}
	
	public Customer addCustomer( Customer customer, Address address ) {
	
		try {
			
			Customer createdCustomer = customerDao.addCustomer( customer );
			if( null != createdCustomer ) {
				log.info( "addCustomer : createdCustomer=" + createdCustomer.toString() );
				
				Address createdAddress = addressDao.addAddress( createdCustomer, address );
				if( null != createdAddress ) {
					
					log.info( "addCustomer : createdAddress=" + createdAddress.toString() );
				
				}
				
			}
			
			return createdCustomer;
			
		} catch( SQLException e ) {
			log.error( "addCustomer : error", e );
		}
		
		throw new InvalidDataAccessApiUsageException( "could not create customer!" );
	}
	
	@Transactional( readOnly = true )
	public Customer getCustomer( final Integer customerId ) {
		
		try {
		
			return customerDao.getCustomer( customerId );
			
		} catch( SQLException e ) {
			
			throw new IncorrectResultSizeDataAccessException( "customer not found", 1, e );
		}

	}
	
	@Transactional( readOnly = true )
	public Customer getCustomer( final String name ) {
		
		try {
		
			return customerDao.getCustomer( name );
			
		} catch( SQLException e ) {
			
			throw new IncorrectResultSizeDataAccessException( "customer not found", 1, e );
		}

	}

	@Transactional( readOnly = true )
	public Address getAddress( final Integer addressId ) {
		
		try {
		
			return addressDao.getAddress( addressId );
			
		} catch( SQLException e ) {
			
			throw new IncorrectResultSizeDataAccessException( "address not found", 1, e );
		}

	}

}
