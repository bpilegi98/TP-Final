package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.ValidationException;
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


@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository)
    {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice addInvoice(Invoice newInvoice)
    {
        return invoiceRepository.save(newInvoice);
    }

    public Invoice deleteInvoice(Integer id) throws ValidationException
    {
        Invoice invoice = invoiceRepository.delete(id);
        return Optional.ofNullable(invoice).orElseThrow(()-> new ValidationException("Couldn't delete, that invoice doesn't exists."));
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

    public List<InvoicesRequestFromPeriods> getInvoicesFromMonth(String month,String year)
    {
        return invoiceRepository.getInvoicesFromMonth(month,year);
    }

    public List<InvoicesRequestFromPeriods> getInvoicesFromYear(String year)
    {
        return invoiceRepository.getInvoicesFromYear(year);
    }

    public List<InvoicesRequestFromPeriods> getInvoicesBetweenDates(Date from, Date to)
    {
        return invoiceRepository.getInvoicesBetweenDates(from, to);
    }

    public InvoiceIncome getIncome()
    {
        return invoiceRepository.getIncome();
    }

    public InvoiceIncome getIncomeMonth(String month,String year)
    {
        return invoiceRepository.getIncomeMonth(month,year);
    }

    public InvoiceIncome getIncomeYear(String year)
    {
        return invoiceRepository.getIncomeYear(year);
    }
}
