package com.supr.dao;	

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.supr.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	
	// inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	 
	@Override
//	@Transactional       // now handled by the service layer
	public List<Customer> getCustomers() {		
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
																									
		// exectue query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		// retrieve list of customers (return the results)		
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

				// get the current hibernate session
				Session currentSession = sessionFactory.getCurrentSession();
				
				// save the Customer
				currentSession.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomer(int theId) {


		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieve the data from database using the Primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId){

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}

}




