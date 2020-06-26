package com.utn.TP_Final.service;


import com.utn.TP_Final.exceptions.InvoiceNotExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.model.Invoice;
import com.utn.TP_Final.projections.InvoiceIncome;
import com.utn.TP_Final.projections.InvoicesFromUser;
import com.utn.TP_Final.projections.InvoicesRequestFromPeriods;
import com.utn.TP_Final.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.tomcat.jni.Mmap.delete;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice addInvoice(Invoice newInvoice)
    {
        return invoiceRepository.save(newInvoice);
    }

    public Invoice deleteInvoice(Integer id) throws InvoiceNotExistsException
    {
        Invoice invoice = invoiceRepository.delete(id);
        return Optional.ofNullable(invoice).orElseThrow(()-> new InvoiceNotExistsException());
    }

    public List<Invoice> getAll()
    {
        return invoiceRepository.findAll();
    }

    public List<InvoicesFromUser> getInvoicesFromUser(String dni) throws UserNotExistsException
    {
        List<InvoicesFromUser> invoicesFromUsers = invoiceRepository.getInvoicesFromUser(dni);
        return Optional.ofNullable(invoicesFromUsers).orElseThrow(()-> new UserNotExistsException());
    }

    public List<InvoicesFromUser> getInvoicesPaidFromUser(String dni) throws UserNotExistsException
    {
        List<InvoicesFromUser> invoicesFromUsers = invoiceRepository.getInvoicesPaidFromUser(dni);
        return Optional.ofNullable(invoicesFromUsers).orElseThrow(()-> new UserNotExistsException());
    }

    public List<InvoicesFromUser> getInvoicesNotPaidFromUser(String dni) throws UserNotExistsException
    {
        List<InvoicesFromUser> invoicesFromUsers = invoiceRepository.getInvoicesNotPaidFromUser(dni);
        return Optional.ofNullable(invoicesFromUsers).orElseThrow(()-> new UserNotExistsException());
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
