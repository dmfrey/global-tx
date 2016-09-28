package io.pivotal.distributedtx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
		Customer createdCustomer = customerDao.addCustomer( customer );
		if( null != createdCustomer ) {
			log.info( "addCustomer : createdCustomer=" + createdCustomer.toString() );
				
			Address createdAddress = addressDao.addAddress( createdCustomer, address );
			if( null != createdAddress ) {
					
				log.info( "addCustomer : createdAddress=" + createdAddress.toString() );
				
			}
				
		}
			
		return createdCustomer;
	}
	
	@Transactional( readOnly = true )
	public Customer getCustomer( final Integer customerId ) {
		
		return customerDao.getCustomer( customerId );
	}
	
	@Transactional( readOnly = true )
	public Customer getCustomer( final String name ) {

		return customerDao.getCustomer( name );
	}

	@Transactional( readOnly = true )
	public Address getAddress( final Integer addressId ) {

		return addressDao.getAddress( addressId );
	}

}
