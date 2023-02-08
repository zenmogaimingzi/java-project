package com.example.demo.repository;


import com.example.demo.entity.TbCustomer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 劳动者
 */
public interface TbCustomerRepository extends BaseRepository<TbCustomer,Long>{

    @Transactional
    @Modifying
    //@Query(value = "update tb_customer s set s.customer_name = ?1 where s.id= ?2",nativeQuery = true)
    //@Query(value = "update TbCustomer   set  customerName = ?1 where  id = ?2 ")
    @Query(value = "update tb_customer s set s.customer_name = :customerName where s.id= :id",nativeQuery = true)
    //@Query(value = "update TbCustomer   set  customerName = :customerName where  id = :id ")
    void updateCustomerName(String customerName,long id);


    TbCustomer findTbCustomerById (long id);


}
