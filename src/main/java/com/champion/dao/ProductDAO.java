package com.champion.dao;

import com.champion.services.com.champion.services.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends BaseDAO {
    public List<Product> getProducts(int productId){
        String sql = "Select * from warehouse.product_master";
        if(productId > 0)
            sql = sql + " where product_id = " + productId;
        ResultSet records = getResultSet(sql);
        List<Product> products = new ArrayList<Product>();
        try{
            while(records.next()){
                Product product = new Product();
                product.setProductId(productId);
                product.setProductDescription(records.getString("product_description"));
                product.setProductName(records.getString("product_name"));
                product.setVendorId(records.getInt("vendor_id"));
                product.setQuantity(records.getInt("quantity"));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return products;
        }
    }

    public Product createProduct(Product product){
        String idSql = "select isnull(max(product_id)) then 1 else max(product_id) + 1 end as 'id' from warehouse.product_master";
        int id = executeScalar(idSql);
        String sql = "insert into warehouse.product_master(product_id,product_name,vendor_id,product_description,quantity) values(" + id + ",'" + product.getProductName() + "'," + product.getVendorId() + "," + product.getProductDescription() + "," + product.getQuantity() + ")";
        int result = executeUpdate(sql);
        if(result == 1){
            List<Product> products = getProducts(id);
            if(products.size() == 1)
                return products.get(0);
        }
        return null;
    }

    public boolean deleteProduct(Product product){
        String sql = "delete from warehouse.product_master where product_id = " + product.getProductId();
        if(executeUpdate(sql) == 1)
            return true;
        return false;
    }

    public Product editProduct(Product product) throws Exception {
        if(product.getProductId() == null || product.getProductId() == 0)
            throw new Exception("Product id cannot be null or 0 while editing");
        if(product.getVendorId() == null || product.getVendorId() == 0)
            throw new Exception("Vendor Id cannot be null/0 for a product");
        String sql = "update warehouse.product_master set product_name = '" + product.getProductName() + "',vendor_id = " + product.getVendorId() + ",product_description = '" + product.getProductDescription() + "',quantity = " + product.getQuantity();
        int result = executeUpdate(sql);
        if(result == 1){
            List<Product> products = getProducts(product.getProductId());
            if(products.size() == 1)
                return products.get(0);
        }
        return null;
    }
}
