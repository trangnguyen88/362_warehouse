package com.champion.services;
import com.champion.services.com.champion.services.model.Product;
import com.champion.dao.ProductDAO;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.List;


@WebService()
public class ProductManagementService {
  @WebMethod
  public Product addProduct(Product product) {
    return new ProductDAO().createProduct(product);
  }
  @WebMethod
  public boolean deleteProduct(Product product){
    return new ProductDAO().deleteProduct(product);
  }
  @WebMethod
  public Product editProduct(Product product) throws Exception {
    return new ProductDAO().editProduct(product);
  }
  @WebMethod
  public List<Product> getProducts(){
    return new ProductDAO().getProducts(0);
  }

  public String toString(){
    return "ProductManagementService";
  }
}
