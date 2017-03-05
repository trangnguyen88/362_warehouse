package com.champion.services;
import com.champion.services.com.champion.services.model.Customer;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService()
public class CustomerManagementService {
  @WebMethod
  public Customer AddCustomer(Customer customer) {
      return customer;
  }

  @WebMethod
  public boolean RemoveCustomer(int customerId){
    return true;
  }

  @WebMethod
  public Customer ModifyCustomer(Customer customer){
    return null;
  }

  public String toString(){
    return "CustomerManagementService";
  }

}
