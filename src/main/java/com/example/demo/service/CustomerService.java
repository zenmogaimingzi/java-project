package com.example.demo.service;


import com.example.demo.entity.TbCustomer;

import java.util.List;

/**
 * @author pi_guai
 */
public interface CustomerService {


    /**
     * 查询所有用户
     * @return
     */
    List<TbCustomer> queryAll();

    void saveCustomer(TbCustomer customer);

    TbCustomer updateCustomer(TbCustomer customer);
}
