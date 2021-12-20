package com.digitallab.academy.shopping.service;

import com.digitallab.academy.shopping.client.CustomerClient;
import com.digitallab.academy.shopping.client.ProductClient;
import com.digitallab.academy.shopping.entity.Invoice;
import com.digitallab.academy.shopping.entity.InvoiceItem;
import com.digitallab.academy.shopping.model.Customer;
import com.digitallab.academy.shopping.model.Product;
import com.digitallab.academy.shopping.repository.InvoiceItemRepository;
import com.digitallab.academy.shopping.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    InvoiceItemRepository invoiceItemRepository;

    @Autowired
    CustomerClient customerClient;

    @Autowired
    ProductClient productClient;


    @Override
    public List<Invoice> findInvoiceAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        Invoice invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());
        if(invoiceDB != null){
            return invoiceDB;
        }
        invoice.setState("Created");
        invoiceDB = invoiceRepository.save(invoice);
        invoiceDB.getItems().forEach( invoiceItem -> {
            productClient.updateStock(invoiceItem.getProductId(),invoiceItem.getQuantity()*-1);
        });
        return invoiceDB;
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if(invoiceDB == null){
            return null;
        }
        invoiceDB.setState("Deleted");
        return invoiceRepository.save(invoiceDB);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        Invoice invoiceDB = getInvoice(invoice.getId());
        if(invoiceDB == null){
            return null;
        }
        invoiceDB.setCustomerId(invoice.getCustomerId());
        invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
        invoiceDB.setDescription(invoice.getDescription());
        invoiceDB.getItems().clear();
        invoiceDB.setItems(invoice.getItems());
        return invoiceRepository.save(invoiceDB);
    }

    //Método que trae el cliente del microservicio service-customer
    //Ademas trae los productos del microservicio service-product
    //E integra a la factura(Invoice) con esos datos
    @Override
    public Invoice getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if(invoice != null){
            Customer customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
           invoice.setCustomer(customer);
           List<InvoiceItem> itemList = invoice.getItems().stream().map(invoiceItem -> {
               Product product = productClient.getProduct(invoiceItem.getProductId()).getBody();
               invoiceItem.setProduct(product);
               return invoiceItem;
           }).collect(Collectors.toList());
           invoice.setItems(itemList);
        }
        return invoice;
    }


}
