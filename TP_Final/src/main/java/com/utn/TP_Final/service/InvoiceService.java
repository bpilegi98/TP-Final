package com.utn.TP_Final.service;


import com.utn.TP_Final.model.Invoice;
import com.utn.TP_Final.projections.InvoiceIncome;
import com.utn.TP_Final.projections.InvoicesFromUser;
import com.utn.TP_Final.projections.InvoicesRequestFromPeriods;
import com.utn.TP_Final.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void addInvoice(Invoice newInvoice){
        invoiceRepository.save(newInvoice);
    }

    public void deleteInvoice(Invoice invoice)
    {
        invoiceRepository.delete(invoice);
    }

    public List<InvoicesFromUser> getInvoicesFromUser(String dni)
    {
        return invoiceRepository.getInvoicesFromUser(dni);
    }

    public List<InvoicesFromUser> getInvoicesPaidFromUser(String dni)
    {
        return invoiceRepository.getInvoicesPaidFromUser(dni);
    }

    public List<InvoicesFromUser> getInvoicesNotPaidFromUser(String dni)
    {
        return invoiceRepository.getInvoicesNotPaidFromUser(dni);
    }

    public List<InvoicesRequestFromPeriods> getInvoicesFromMonth(String monthI)
    {
        return invoiceRepository.getInvoicesFromMonth(monthI);
    }

    public List<InvoicesRequestFromPeriods> getInvoicesFromYear(String yearI)
    {
        return invoiceRepository.getInvoicesFromYear(yearI);
    }

    public List<InvoicesRequestFromPeriods> getInvoicesBetweenDates(Date fromI, Date toI)
    {
        return invoiceRepository.getInvoicesBetweenDates(fromI, toI);
    }

    public InvoiceIncome getIncome()
    {
        return invoiceRepository.getIncome();
    }

    public InvoiceIncome getIncomeMonth(String monthI)
    {
        return invoiceRepository.getIncomeMonth(monthI);
    }

    public InvoiceIncome getIncomeYear(String year)
    {
        return invoiceRepository.getIncomeYear(year);
    }
}
