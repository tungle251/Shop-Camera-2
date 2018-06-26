package com.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_detail2")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "id_order")
    private int idOder;
    @Column(name = "id_product")
    private int idProduct;
    @Column(name = "quatity")
    private int quatity;
    @Column(name = "unit_price")
    private int unitPrice;

    public OrderDetail() {
    }

    public OrderDetail(int idOder, int idProduct, int quatity, int unitPrice) {
        super();
        this.idOder = idOder;
        this.idProduct = idProduct;
        this.quatity = quatity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOder() {
        return idOder;
    }

    public void setIdOder(int idOder) {
        this.idOder = idOder;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail [id=" + id + ", idOder=" + idOder + ", idProduct=" + idProduct + ", quatity=" + quatity
                + ", unitPrice=" + unitPrice + "]";
    }

}
