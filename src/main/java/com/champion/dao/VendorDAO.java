package com.champion.dao;

import com.champion.services.com.champion.services.model.Vendor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendorDAO extends BaseDAO {

    public List<Vendor> getVendors(int vendorId){
        String sql = "select * from warehouse.vendor";
        if(vendorId > 0)
            sql = sql + " where vendor_id = " + vendorId;
        List<Vendor> vendors = new ArrayList<Vendor>();
        ResultSet records = getResultSet(sql);
        try {
            while(records.next()){
                Vendor vendor = new Vendor();
                vendor.setVendorId(records.getInt("vendor_id"));
                vendor.setVendorName(records.getString("vendor_name"));
                vendor.setVendorAddress(records.getString("vendor_address"));
                vendor.setVendorContact(records.getString("vendor_contact"));
                vendor.setVendorTelephone(records.getString("vendor_telephone"));
                vendor.setVendorFax(records.getString("vendor_fax"));
                vendors.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendors;
    }

    public Vendor createVendor(Vendor vendor){
        String idSql = "select isnull(max(vendor_id)) then 1 else max(vendor_id) + 1 end as 'id' from warehouse.vendor";
        int id = executeScalar(idSql);

        String sql = "insert into warehouse.vendor(vendor_id,vendor_name,vendor_address,vendor_contact,vendor_telephone,vendor_fax) values (" + id + ",'" + vendor.getVendorName() + "','" + vendor.getVendorAddress() + "','" + vendor.getVendorContact() + "','" + vendor.getVendorTelephone() + "','" + vendor.getVendorFax() + "')";
        int result = executeUpdate(sql);
        if(result == 1){
            List<Vendor> vendors = getVendors(id);
            if(vendors.size() == 1)
                return vendors.get(0);
        }
        return null;
    }

    public Vendor editVendor(Vendor vendor){
        String sql = "update warehouse.vendor set vendor_name = '" + vendor.getVendorName() + "',vendor_address = '" + vendor.getVendorAddress() + "',vendor_contact = '" + vendor.getVendorContact() + "',vendor_telephone = '" + vendor.getVendorTelephone() + "',vendor_fax = '" + vendor.getVendorFax() + "' where vendor_id = " + vendor.getVendorId();
        int result = executeUpdate(sql);
        if(result == 1){
            List<Vendor> vendors = getVendors(vendor.getVendorId());
            if(vendors.size() == 1)
                return vendors.get(0);
        }
        return null;
    }

    public boolean deleteVendor(Vendor vendor){
        String sql = "delete from warehouse.vendor where vendor_id = " + vendor.getVendorId();
        if(executeUpdate(sql) == 1)
            return true;
        return false;
    }


}
