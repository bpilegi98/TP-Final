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


    public ResponseEntity<Invoice> addInvoice(Invoice newInvoice)
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


    public ResponseEntity<List<InvoicesFromUser>> getInvoicesFromUser(String dni) throws UserNotExistsException
    {
        return ResponseEntity.ok(invoiceService.getInvoicesFromUser(dni));
    }


    public ResponseEntity<List<InvoicesFromUser>> getInvoicesPaidFromUser(String dni) throws UserNotExistsException
    {
        return ResponseEntity.ok(invoiceService.getInvoicesPaidFromUser(dni));
    }


    public ResponseEntity<List<InvoicesFromUser>> getInvoicesNotPaidFromUser(String dni) throws UserNotExistsException
    {
        return ResponseEntity.ok(invoiceService.getInvoicesNotPaidFromUser(dni));
    }


    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromMonth(String month, String year )
    {
        return ResponseEntity.ok(invoiceService.getInvoicesFromMonth(month,year));
    }


    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromYear(String yearI)
    {
        return ResponseEntity.ok(invoiceService.getInvoicesFromYear(yearI));
    }


    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesBetweenDates(Date fromI, Date toI)
    {
        return ResponseEntity.ok(invoiceService.getInvoicesBetweenDates(fromI, toI));
    }


    public ResponseEntity<InvoiceIncome> getIncome()
    {
        return ResponseEntity.ok(invoiceService.getIncome());
    }


    public ResponseEntity<InvoiceIncome> getIncomeMonth( String month, String year)
    {
        return ResponseEntity.ok(invoiceService.getIncomeMonth(month,"2020"));
    }


    public ResponseEntity<InvoiceIncome> getIncomeYear(String year)
    {
        return ResponseEntity.ok(invoiceService.getIncomeYear(year));
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
