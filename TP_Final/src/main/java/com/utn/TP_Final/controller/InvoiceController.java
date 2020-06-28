package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.model.Invoice;
import com.utn.TP_Final.projections.InvoiceIncome;
import com.utn.TP_Final.projections.InvoicesFromUser;
import com.utn.TP_Final.projections.InvoicesRequestFromPeriods;
import com.utn.TP_Final.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.List;


@Controller
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService)
    {
        this.invoiceService = invoiceService;
    }


    public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice newInvoice)
    {
        return ResponseEntity.created(getUri(invoiceService.addInvoice(newInvoice))).build();
    }


    public ResponseEntity<Invoice> deleteInvoice(Integer id) throws ValidationException
    {
        return ResponseEntity.ok(invoiceService.deleteInvoice(id));
    }


    public ResponseEntity<List<Invoice>> getAll()
    {
        return ResponseEntity.ok(invoiceService.getAll());
    }


    public ResponseEntity<List<InvoicesFromUser>> getInvoicesFromUser(@PathVariable String dni) throws UserNotExistsException
    {
        return ResponseEntity.ok(invoiceService.getInvoicesFromUser(dni));
    }


    public ResponseEntity<List<InvoicesFromUser>> getInvoicesPaidFromUser(@PathVariable String dni) throws UserNotExistsException
    {
        return ResponseEntity.ok(invoiceService.getInvoicesPaidFromUser(dni));
    }


    public ResponseEntity<List<InvoicesFromUser>> getInvoicesNotPaidFromUser(@PathVariable String dni) throws UserNotExistsException
    {
        return ResponseEntity.ok(invoiceService.getInvoicesNotPaidFromUser(dni));
    }


    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromMonth(@PathVariable String monthI)
    {
        return ResponseEntity.ok(invoiceService.getInvoicesFromMonth(monthI));
    }


    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromYear(@PathVariable String yearI)
    {
        return ResponseEntity.ok(invoiceService.getInvoicesFromYear(yearI));
    }


    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesBetweenDates(@PathVariable Date fromI, @PathVariable Date toI)
    {
        return ResponseEntity.ok(invoiceService.getInvoicesBetweenDates(fromI, toI));
    }


    public ResponseEntity<InvoiceIncome> getIncome()
    {
        return ResponseEntity.ok(invoiceService.getIncome());
    }


    public ResponseEntity<InvoiceIncome> getIncomeMonth(@PathVariable String monthI)
    {
        return ResponseEntity.ok(invoiceService.getIncomeMonth(monthI));
    }


    public ResponseEntity<InvoiceIncome> getIncomeYear(@PathVariable String yearI)
    {
        return ResponseEntity.ok(invoiceService.getIncomeYear(yearI));
    }

    private URI getUri(Invoice invoice)
    {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/")
                .buildAndExpand(invoice.getId())
                .toUri();
    }
}
