package com.utn.TP_Final.controller;


import com.utn.TP_Final.model.Invoice;
import com.utn.TP_Final.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("")
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/")
    public void addInvoice(@RequestBody Invoice newInvoice)
    {
        invoiceService.addInvoice(newInvoice);
    }

   /* @GetMapping("/")
    public List<Invoice> getAll(@RequestBody(required = false)Date from, Date to)
    {
        return invoiceService.getAll(from, to);
    }*/
}
