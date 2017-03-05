package com.champion.services;
import com.champion.services.com.champion.services.model.Vendor;
import com.champion.dao.VendorDAO;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.List;


@WebService()
public class VendorManagementService {
  @WebMethod
  public Vendor createVendor(Vendor vendor) {
    return new VendorDAO().createVendor(vendor);
  }
  @WebMethod
  public boolean deleteVendor(Vendor vendor){
    return new VendorDAO().deleteVendor(vendor);
  }
  @WebMethod
  public Vendor editVendor(Vendor vendor){
    return new VendorDAO().editVendor(vendor);
  }
  @WebMethod
  public List<Vendor> getVendors(){
    return new VendorDAO().getVendors(0);
  }

  @WebMethod
  public Vendor getVendor(Integer vendorId){
    List<Vendor> vendors = new VendorDAO().getVendors(vendorId);
    if(vendors.size() == 1)
      return vendors.get(0);
    return null;
  }

  public String toString(){
    return "VendorManagementService";
  }
}
