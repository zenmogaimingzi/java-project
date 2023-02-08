package com.example.demo.controller;


import com.example.demo.entity.TbCustomer;
import com.example.demo.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ***
 */
@RestController
@Slf4j
@RequestMapping("/demo/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 查询所有customer
     * @return
     */
    @GetMapping("query_all")
    public List<TbCustomer> queryAll(){
        return customerService.queryAll();
    }

    /**
     * 查询所有customer
     * @return
     */
    @PostMapping("save_customer")
    public void saveCustomer (@RequestBody TbCustomer customer){
        customerService.saveCustomer(customer);
    }



    /**
     * 更新customer
     * @return
     */
    @PostMapping("update_customer")
    public TbCustomer updateCustomer (@RequestBody TbCustomer customer){
        TbCustomer dbCustomer = customerService.updateCustomer(customer);
        return dbCustomer  ;
    }
}
