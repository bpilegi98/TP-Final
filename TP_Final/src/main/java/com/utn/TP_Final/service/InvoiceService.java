package com.utn.TP_Final.service;


import com.utn.TP_Final.model.Invoice;
import com.utn.TP_Final.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public List<Invoice> getAll(Date from, Date to)
    {
        if(isNull(from) || isNull(to))
        {
            return invoiceRepository.findAll();
        }
        return invoiceRepository.findBetweenDates(from, to);
    }
}
