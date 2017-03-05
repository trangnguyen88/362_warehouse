package com.champion.services;
import com.champion.services.com.champion.services.model.Order;
import com.champion.services.com.champion.services.model.Vendor;
import com.champion.dao.OrderDAO;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.List;

@WebService()
public class OrderManagementService {
  @WebMethod
  public Order createOrder(Order order) {
    return new OrderDAO().createOrder(order);
  }
  @WebMethod
  public boolean deleteOrder(Order order){
    return new OrderDAO().deleteOrder(order);
  }
  @WebMethod
  public Order editOrder(Order order) throws Exception {
    return new OrderDAO().editOrder(order);
  }
  @WebMethod
  public List<Order> getOrders(){
    return new OrderDAO().getOrders(0);
  }
  public Order getOrder(Integer orderId)
  {
    List<Order> orders = new OrderDAO().getOrders(orderId);
    if(orders.size() == 1)
      return orders.get(0);
    return null;
  }

  public String toString(){
    return "OrderManagementService";
  }
}
