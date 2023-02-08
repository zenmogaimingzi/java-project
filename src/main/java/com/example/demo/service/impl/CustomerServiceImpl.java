package com.example.demo.service.impl;


import com.example.demo.entity.TbCustomer;
import com.example.demo.repository.TbCustomerRepository;
import com.example.demo.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ***
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private TbCustomerRepository customerRepository;


    @Override
    public List<TbCustomer> queryAll() {
        return customerRepository.findAll();
    }

    @Override
    public void saveCustomer(TbCustomer customer) {
        customer.setCreateTime(new Date());
        customerRepository.save(customer);
    }

    @Override
    public TbCustomer updateCustomer(TbCustomer customer) {

        customerRepository.updateCustomerName(customer.getCustomerName(), customer.getId());
        return  customerRepository.findTbCustomerById(customer.getId());
    }
}
