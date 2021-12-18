package com.digitallab.academy.shopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "tbl_invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "number_invoice")
    private String numberInvoice;
    private String description;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;
    private String state;
    @Valid
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceItem> items;

    public Invoice(){
        items= new ArrayList<>();
    }

    //Se crea la fecha antes de insertar en la BD para eso @PrePersist
    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }

}
