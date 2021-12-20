package com.digitallab.academy.shopping.entity;

import com.digitallab.academy.shopping.model.Product;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Data
@Table(name = "tbl_invoice_items")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "La cantidad debe ser mayor a cero")
    private Double quantity;
    //No va a ser registrado en la BD, pero si lo utilizaremos JSON
    @Transient
    private Double subtotal;
    private Double price;
    //Porque no se va a guardar en la base de datos @Transient
    @Transient
    private Product product;
    @Column(name = "product_id")
    private Long productId;

    public Double getSubTotal(){
        if(this.price>0 && this.quantity>0){
            return this.price * this.quantity;
        }else {
            return (double)0;
        }
    }
}
