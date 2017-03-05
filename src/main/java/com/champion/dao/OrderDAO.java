package com.champion.dao;

import com.champion.services.com.champion.services.model.Order;
import com.champion.services.com.champion.services.model.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class OrderDAO extends BaseDAO {
    public List<Order> getOrders(Integer id){
        String sql = "select o.*,od.* from warehouse.order o , warehouse.order_details od where o.order_id = od.order_id";
        if(id > 0)
            sql = sql + " and o.order_id = " + id;
        ResultSet records = getResultSet(sql);
        List<Order> orders = new ArrayList<Order>();
        if(records == null)
            return orders;
        try{
            while(records.next()){
                Integer orderId = records.getInt("order_id");
                Order order = find(orders,orderId);
                if(order == null){ //Fill the order first if does not exist
                    order = new Order();
                    order.setOrderId(orderId);
                    order.setEmployeeId(records.getInt("employee_id"));
                    order.setOrderDate(records.getDate("order_date"));
                    order.setExpectedDate(records.getDate("expected_date"));
                    order.setDeliveredDate(records.getDate("delivered_date"));
                    order.setTotalAmount(records.getDouble("total_amount"));
                    order.setVendorId(records.getInt("vendor_id"));
                    order.setOrderDetails(new ArrayList<OrderDetail>());
                    orders.add(order);
                }
                //Now fill the order details
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailId(records.getInt("order_detail_id"));
                orderDetail.setOrderedQuantity(records.getInt("quantity"));
                orderDetail.setPrice(records.getInt("price"));
                orderDetail.setProductId(records.getInt("product_id"));
                order.getOrderDetails().add(orderDetail);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
            return orders;
        }

    }

    public Order createOrder(Order order){
        int id = 0;
        try {
            connection.setAutoCommit(false); //begin transaction
            if(order.getOrderId() == null || order.getOrderId() == 0){
                String idSql = "select case when isnull(max(order_id)) then 1 else max(order_id) + 1 end as 'id' from warehouse.order";
                id = executeScalar(idSql);
            }else{
                id = order.getOrderId();
            }
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
            String sql = "insert into warehouse.order (order_id,vendor_id,total_amount,order_date,expected_date,delivered_date,employee_id) values (" + id + "," + order.getVendorId() + "," + order.getTotalAmount() + ",'" + DATE_FORMAT.format(order.getOrderDate()) + "','" + DATE_FORMAT.format(order.getExpectedDate()) + "',null," + order.getEmployeeId() + ")";
            int result = executeUpdate(sql);
            if(result == 1){
                for(OrderDetail orderDetail : order.getOrderDetails()){
                    int orderdetailid =  0;
                    if(orderDetail.getOrderDetailId() == null || orderDetail.getOrderDetailId() == 0)
                        orderdetailid = executeScalar("select case when isnull(max(order_detail_id)) then 1 else max(order_detail_id) + 1 end as 'id' from warehouse.order_details");
                    else
                        orderdetailid = orderDetail.getOrderDetailId();
                    sql = "insert into warehouse.order_details (order_id,order_detail_id,product_id,quantity,price) values(" + id + "," + orderdetailid + "," + orderDetail.getProductId() + "," + orderDetail.getOrderedQuantity() + "," + orderDetail.getPrice() + ")";
                    result = executeUpdate(sql);
                    if(result == 0){
                        connection.rollback();
                        return null;
                    }
                }
                connection.commit();
                List<Order> oders = getOrders(id);
                if(oders.size() == 1)
                    return oders.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean deleteOrder(Order order){
        //Delete child table first
        String sql = "delete from warehouse.order_details where order_id = " + order.getOrderId();
        int result = executeUpdate(sql);
        if(result == 1){
            sql = "delete from warehouse.order where order_id = " + order.getOrderId();
            result = executeUpdate(sql);
            if(result == 1)
                return true;
        }
        return false;
    }

    public Order editOrder(Order  order) throws Exception {
        if(order.getOrderId() == null || order.getOrderId() == 0)
            throw new Exception("Order Id cannot be null or 0 while editing");
        boolean result = deleteOrder(order);
        Order newOrder = null;
        if(result){
            newOrder = createOrder(order);
            if(newOrder != null)
                return newOrder;
        }
        return null;
    }

    private Order find(List<Order> orders,Integer orderId){
        for(Order order : orders){
            if(order.getOrderId() == orderId)
                return order;
        }
        return null;
    }

}


