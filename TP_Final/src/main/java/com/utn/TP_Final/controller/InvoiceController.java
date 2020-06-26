package com.utn.TP_Final.controller;


import com.utn.TP_Final.exceptions.DateNotExistsException;
import com.utn.TP_Final.exceptions.InvoiceNotExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Invoice;
import com.utn.TP_Final.projections.InvoiceIncome;
import com.utn.TP_Final.projections.InvoicesFromUser;
import com.utn.TP_Final.projections.InvoicesRequestFromPeriods;
import com.utn.TP_Final.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;


@RestController("")
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/")
    public Invoice addInvoice(@RequestBody Invoice newInvoice)
    {
        return invoiceService.addInvoice(newInvoice);
    }

    @PostMapping("/delete/{id}")
    public Invoice deleteInvoice(Integer id) throws InvoiceNotExistsException {
        return invoiceService.deleteInvoice(id);
    }

    @GetMapping("/")
    public List<Invoice> getAll()
    {
        return invoiceService.getAll();
    }

    @GetMapping("/user/{dni}")
    public List<InvoicesFromUser> getInvoicesFromUser(@PathVariable String dni) throws UserNotExistsException, ValidationException
    {
        return invoiceService.getInvoicesFromUser(dni);
    }

    @GetMapping("/paid/{dni}")
    public List<InvoicesFromUser> getInvoicesPaidFromUser(@PathVariable String dni) throws UserNotExistsException, ValidationException
    {
        return invoiceService.getInvoicesPaidFromUser(dni);
    }

    @GetMapping("/notPaid/{dni}")
    public List<InvoicesFromUser> getInvoicesNotPaidFromUser(@PathVariable String dni) throws UserNotExistsException, ValidationException
    {
        return invoiceService.getInvoicesNotPaidFromUser(dni);
    }

    @GetMapping("/month/{monthI}")
    public List<InvoicesRequestFromPeriods> getInvoicesFromMonth(@PathVariable String monthI) throws DateNotExistsException, ValidationException
    {
        return invoiceService.getInvoicesFromMonth(monthI);
    }

    @GetMapping("/year/{yearI}")
    public List<InvoicesRequestFromPeriods> getInvoicesFromYear(@PathVariable String yearI) throws DateNotExistsException, ValidationException
    {
        return invoiceService.getInvoicesFromYear(yearI);
    }

    @GetMapping("/betweenDates/{fromI}/{toI}")
    public List<InvoicesRequestFromPeriods> getInvoicesBetweenDates(@PathVariable Date fromI, @PathVariable Date toI) throws DateNotExistsException, ValidationException
    {
        return invoiceService.getInvoicesBetweenDates(fromI, toI);
    }

    @GetMapping("/income")
    public InvoiceIncome getIncome()
    {
        return invoiceService.getIncome();
    }

    @GetMapping("/incomeMonth/{monthI}")
    public InvoiceIncome getIncomeMonth(@PathVariable String monthI) throws DateNotExistsException, ValidationException
    {
        return invoiceService.getIncomeMonth(monthI);
    }

    @GetMapping("/incomeYear/{yearI}")
    public InvoiceIncome getIncomeYear(@PathVariable String yearI) throws DateNotExistsException, ValidationException
    {
        return invoiceService.getIncomeYear(yearI);
    }
}
