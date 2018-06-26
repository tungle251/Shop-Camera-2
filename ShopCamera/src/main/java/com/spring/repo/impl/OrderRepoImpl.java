package com.spring.repo.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import com.spring.domain.Order;
import com.spring.domain.OrderDetail;
import com.spring.repo.OrderRepo;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class OrderRepoImpl implements OrderRepo {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Order> getListOrder() {
        Session session = this.sessionFactory.openSession();
        // String sql = "Select o.id, o.code_order, o.id_user, o.name_customer, o.email,
        // o.id_process, o.active, o.address, o.phone_number, o.key_sales_off, o.total
        // from order o";
        // Query<Order> query = session.createQuery(sql);
        List<Order> list = session.createQuery("From Order").getResultList();
        // List<Order> list = (List<Order>) query.getResultList();

        return list;
    }

    @Override
    public Order getOrder(int id) {
        Session session = this.sessionFactory.openSession();
        Order o = (Order) session.get(Order.class, id);
        return o;

    }

    @Override
    public int insertOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        session.saveOrUpdate(order);
        return order.getId();

    }

    @Override
    public int insertOrder(OrderDetail order) {
        Session session = this.sessionFactory.openSession();
        session.save(order);
        return order.getId();
    }

    public void removeOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        session.getTransaction().begin();
        session.remove(order);
        session.getTransaction().commit();
    }

    @Override
    public void updateOrder(Order order) {
//        entityManager.createQuery("update Order o set o.active =:active where o.codeOrder=19").setParameter("active", 1);
        Session session = this.sessionFactory.openSession();
        session.getTransaction().begin();
        session.update(order);
        session.getTransaction().commit();
    }

    public OrderDetail getOrderDetail(int id) {
        Session session = this.sessionFactory.openSession();
        OrderDetail orderDetail = session.get(OrderDetail.class, id);
        return orderDetail;
    }

    public List<Object[]> getOrderDetailByIdOrder(int id) {
        Session session = this.sessionFactory.openSession();
        String sql = "select o.id,  o.id_order, o.id_product, o.quatity, o.unit_price  from order_detail2 o where o.id_order = :idOrder";
        List<Object[]> listOrderDetail = session.createNativeQuery(sql).setParameter("idOrder", id).getResultList();
        return listOrderDetail;
    }
    // public Iterable<Object[]> getOrderIdOrder(int id) {
    // Session session = this.sessionFactory.openSession();
    // String sql = "select o.id, o.id_order, o.id_product, o.quatity, o.unit_price
    // from order_detail2 o where o.id_order = :idOrder";
    // Iterable<Object[]> listOrderDetail =
    // session.createNativeQuery(sql).setParameter("idOrder", id).getResultList();
    // return listOrderDetail;
    // }

    @Override
    public void deleteOrder(int id) {
        Session session = this.sessionFactory.openSession();
        session.getTransaction().begin();
        List<Object[]> orderDetails = getOrderDetailByIdOrder(id);
        for (int i = 0; i < orderDetails.size(); i++) {
            session.delete(getOrderDetail((Integer) orderDetails.get(i)[0]));
        }
        Order order = session.get(Order.class, id);
        session.delete(order);
        session.getTransaction().commit();
    }

    // public int getQuantity(int idOrderDetail) {
    // Session session = this.sessionFactory.openSession();
    // String sql = "select o.quatity from order_detail2 o where o.id_product =
    // :idProduct";
    // int quantity = (int) session.createNativeQuery(sql).setParameter("idProduct",
    // idOrderDetail).getSingleResult();
    // return quantity;
    // }
    //
    // public List<Integer> getListQuantity(int idOrderDetail) {
    // Session session = this.sessionFactory.openSession();
    // String sql = "select o.quatity from order_detail2 o where o.id_order =
    // :idOrderDetail";
    // List<Integer> quantity =
    // session.createNativeQuery(sql).setParameter("idOrderDetail",
    // idOrderDetail).getResultList();
    // return quantity;
    // }
}
