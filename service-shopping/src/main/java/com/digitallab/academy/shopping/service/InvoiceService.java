package com.digitallab.academy.shopping.service;

import com.digitallab.academy.shopping.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    public List<Invoice> findInvoiceAll();
    public Invoice createInvoice(Invoice invoice);
    public Invoice deleteInvoice(Invoice invoice);
    public Invoice updateInvoice(Invoice invoice);
    public Invoice getInvoice(Long id);
}
