package com.supr.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.supr.dao.CustomerDAO;
import com.supr.entity.Customer;
import com.supr.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// now handled by Service layer
//	// inject Customer DAO to the controller
//	@Autowired
//	private CustomerDAO customerDAO;
	
	// inject the customer service
	@Autowired
	private CustomerService customerService;

	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// changed to service layer
//		// Get customers from DAO
//		List<Customer> theCustomers = customerDAO.getCustomers();
		
		// Get customers from the service layer
		List<Customer> theCustomers = customerService.getCustomers();
		
		// Add the customers to the Model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer",theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		// save the customer using the Service layer
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		
		// get the customer form the Service layer
		Customer theCustomer = customerService.getCustomer(theId);
		
		// set customer as model attribute to pre populate the form
		theModel.addAttribute("customer",theCustomer);
	
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		// delete the customer
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list/";
	}


	@PostMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
                                    Model theModel) {

        // search customers from the service
        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
                
        // add the customers to the model
        theModel.addAttribute("customers", theCustomers);

        return "list-customers";        
    }
}
