package com.digitallab.academy.shopping.controller;

import com.digitallab.academy.shopping.entity.Invoice;
import com.digitallab.academy.shopping.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping("/shopping")
public class InvoiceRestController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/getAllInvoices")
    public ResponseEntity<Invoice> getAllInvoices(){
        return new ResponseEntity(invoiceService.findInvoiceAll(), HttpStatus.OK);
    }

    @GetMapping("/getInvoice/{id}")
    public ResponseEntity getInvoice(@PathVariable Long id){
        log.info("Fetching invoice with id={}",id);
        if(invoiceService.getInvoice(id) == null){
            log.error("Not found Invoice");
            return ResponseEntity.notFound().build();
        }else{
            log.info("Found Invoice");
            return ResponseEntity.ok(invoiceService.getInvoice(id));
        }
        //return new ResponseEntity(invoiceService.getInvoice(id),HttpStatus.OK);
    }

    @PostMapping("/createInvoice")
    public ResponseEntity createInvoice(@RequestBody Invoice invoice, BindingResult result){
        log.info("Creating Invoice : {}", invoice);
        if (result.hasErrors()){
            ErrorMessage errorMessage = new ErrorMessage();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage.formatMessage(result));
        }
        Invoice invoiceDB = invoiceService.createInvoice (invoice);

        return  ResponseEntity.status( HttpStatus.CREATED).body(invoiceDB);
        //return ResponseEntity.ok(invoiceService.createInvoice(invoice));
    }

    @DeleteMapping("/deleteInvoice/{id}")
    public ResponseEntity deleteInvoice(@PathVariable Long id){
        log.info("Fetching & Deleting Invoice with id {}", id);

        Invoice invoice = invoiceService.getInvoice(id);
        if (invoice == null) {
            log.error("Unable to delete. Invoice with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        invoice = invoiceService.deleteInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }
    @PutMapping("/updateInvoice")
    public ResponseEntity updateInvoice(@RequestBody Invoice invoice){
        log.info("Updating Invoice with id {}", invoice.getId());
        Invoice currentInvoice=invoiceService.updateInvoice(invoice);

        if (currentInvoice == null) {
            log.error("Unable to update. Invoice with id {} not found.", invoice.getId());
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(currentInvoice);
    }
}
