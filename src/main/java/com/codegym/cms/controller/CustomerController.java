package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.repository.ICustomerRepository;
import com.codegym.cms.service.ICustomerService;
import com.codegym.cms.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IProvinceService provinceService;
    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("/customers")
    public ModelAndView listCustomers() {
        Iterable<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer != null) {
            ModelAndView modelAndView = new ModelAndView("customer/edit");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("error404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/edit");
        modelAndView.addObject("customer", customer);
        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        ModelAndView modelAndView;
        if (customer != null) {
            modelAndView = new ModelAndView("customer/delete");
            modelAndView.addObject("customer", customer.get());

        } else {
            modelAndView = new ModelAndView("error404");
        }
        return modelAndView;
    }

//    @PostMapping("/delete-customer")
//    public String deleteCustomer(@ModelAttribute("customer") Customer customer) {
//        customerService.remove(customer.getId());
//        return "redirect:/customers";
//    }

    @PostMapping("/delete-customer")
    public ModelAndView deleteCustomer(Customer customer) {
        customerService.remove(customer.getId());
        return new ModelAndView("redirect:/customers");
    }
}
