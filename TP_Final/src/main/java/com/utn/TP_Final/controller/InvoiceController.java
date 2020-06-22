package com.utn.TP_Final.controller;


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
    public void addInvoice(@RequestBody Invoice newInvoice)
    {
        invoiceService.addInvoice(newInvoice);
    }

    @GetMapping("/getInvoicesFromUser/{dni}")
    public List<InvoicesFromUser> getInvoicesFromUser(@PathVariable String dni)
    {
        return invoiceService.getInvoicesFromUser(dni);
    }

    @GetMapping("/getInvoicesPaidFromUser/{dni}")
    public List<InvoicesFromUser> getInvoicesPaidFromUser(@PathVariable String dni)
    {
        return invoiceService.getInvoicesPaidFromUser(dni);
    }

    @GetMapping("/getInvoicesNotPaidFromUser/{dni}")
    public List<InvoicesFromUser> getInvoicesNotPaidFromUser(@PathVariable String dni)
    {
        return invoiceService.getInvoicesNotPaidFromUser(dni);
    }

    @GetMapping("/getInvoicesFromMonth/{monthI}")
    public List<InvoicesRequestFromPeriods> getInvoicesFromMonth(@PathVariable String monthI)
    {
        return invoiceService.getInvoicesFromMonth(monthI);
    }

    @GetMapping("/getInvoicesFromYear/{yearI}")
    public List<InvoicesRequestFromPeriods> getInvoicesFromYear(@PathVariable String yearI)
    {
        return invoiceService.getInvoicesFromYear(yearI);
    }

    @GetMapping("/getInvoicesBetweenDates/{fromI}/{toI}")
    public List<InvoicesRequestFromPeriods> getInvoicesBetweenDates(@PathVariable Date fromI, @PathVariable Date toI)
    {
        return invoiceService.getInvoicesBetweenDates(fromI, toI);
    }

    @GetMapping("/getIncome")
    public InvoiceIncome getIncome()
    {
        return invoiceService.getIncome();
    }

    @GetMapping("/getIncomeMonth/{monthI}")
    public InvoiceIncome getIncomeMonth(@PathVariable String monthI)
    {
        return invoiceService.getIncomeMonth(monthI);
    }

    @GetMapping("/getIncomeYear/{yearI}")
    public InvoiceIncome getIncomeYear(@PathVariable String yearI)
    {
        return invoiceService.getIncomeYear(yearI);
    }
}
