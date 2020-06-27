package com.utn.TP_Final.controller;

import com.utn.TP_Final.exceptions.DateNotExistsException;
import com.utn.TP_Final.exceptions.InvoiceNotExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.Invoice;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.InvoiceIncome;
import com.utn.TP_Final.projections.InvoicesFromUser;
import com.utn.TP_Final.projections.InvoicesRequestFromPeriods;
import com.utn.TP_Final.service.InvoiceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class InvoiceControllerTest {

    @Autowired
    InvoiceController invoiceController;

    @Mock
    InvoiceService invoiceService;

    @Before
    public void setUp()
    {
        initMocks(this);
        invoiceController = new InvoiceController(invoiceService);
    }

    @Test
    public void addInvoiceTest()
    {
        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, null, null);
        when(invoiceService.addInvoice(invoice)).thenReturn(invoice);
        Invoice invoiceResult = invoiceController.addInvoice(invoice);
        assertEquals(invoice, invoiceResult);
    }

    @Test
    public void getAllTest()
    {
        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, null, null);
        List<Invoice> invoices = new ArrayList<Invoice>();
        invoices.add(invoice);
        when(invoiceService.getAll()).thenReturn(invoices);
        List<Invoice> invoicesResult = invoiceController.getAll();
        assertEquals(invoices, invoicesResult);
    }

    @Test
    public void getAllEmptyTest()
    {
        List<Invoice> invoices = new ArrayList<Invoice>();
        when(invoiceService.getAll()).thenReturn(invoices);
        List<Invoice> invoicesResult = invoiceController.getAll();
        assertEquals(invoices, invoicesResult);
    }

    @Test
    public void deleteInvoiceOk() throws InvoiceNotExistsException
    {
        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, null, null);
        when(invoiceService.deleteInvoice(1)).thenReturn(invoice);
        Invoice invoiceResult = invoiceController.deleteInvoice(1);
        assertEquals(invoice, invoiceResult);
    }

    @Test(expected = InvoiceNotExistsException.class)
    public void deleteInvoiceNotExists() throws InvoiceNotExistsException
    {
        when(invoiceService.deleteInvoice(1)).thenReturn(null);
        invoiceController.deleteInvoice(1);
    }

    @Test
    public void getInvoicesFromUserOk() throws UserNotExistsException, ValidationException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        user.setTelephoneLines(telephoneLines);

        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, telephoneLine1, user);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoicesFromUser invoicesFromUser = factory.createProjection(InvoicesFromUser.class);
        invoicesFromUser.setDni(user.getDni());
        invoicesFromUser.setDate_creation(invoice.getDateCreation());
        invoicesFromUser.setDate_expiration(invoice.getDateExpiration());
        invoicesFromUser.setFirstname(user.getFirstname());
        invoicesFromUser.setLastname(user.getLastname());
        invoicesFromUser.setLine_number(telephoneLine1.getLineNumber());
        invoicesFromUser.setPaid(invoice.isPaid());
        invoicesFromUser.setTotal_cost(invoice.getTotalCost());
        invoicesFromUser.setTotal_price(invoice.getTotalPrice());

        List<InvoicesFromUser> invoicesFromUserList = new ArrayList<InvoicesFromUser>();
        invoicesFromUserList.add(invoicesFromUser);

        when(invoiceService.getInvoicesFromUser(user.getDni())).thenReturn(invoicesFromUserList);

        List<InvoicesFromUser> invoicesFromUserResult = invoiceController.getInvoicesFromUser(user.getDni());

        assertEquals(invoicesFromUserList, invoicesFromUserResult);
    }

    @Test(expected = UserNotExistsException.class)
    public void getInvoicesFromUserNotExists() throws UserNotExistsException, ValidationException {
        when(invoiceService.getInvoicesFromUser("41307541")).thenReturn(null);
        invoiceController.getInvoicesFromUser("41307541");
    }

    @Test
    public void getInvoicesPaidFromUserOk() throws UserNotExistsException, ValidationException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        user.setTelephoneLines(telephoneLines);

        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), true, telephoneLine1, user);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoicesFromUser invoicesFromUser = factory.createProjection(InvoicesFromUser.class);
        invoicesFromUser.setDni(user.getDni());
        invoicesFromUser.setDate_creation(invoice.getDateCreation());
        invoicesFromUser.setDate_expiration(invoice.getDateExpiration());
        invoicesFromUser.setFirstname(user.getFirstname());
        invoicesFromUser.setLastname(user.getLastname());
        invoicesFromUser.setLine_number(telephoneLine1.getLineNumber());
        invoicesFromUser.setPaid(invoice.isPaid());
        invoicesFromUser.setTotal_cost(invoice.getTotalCost());
        invoicesFromUser.setTotal_price(invoice.getTotalPrice());

        List<InvoicesFromUser> invoicesFromUserList = new ArrayList<InvoicesFromUser>();
        invoicesFromUserList.add(invoicesFromUser);

        when(invoiceService.getInvoicesPaidFromUser(user.getDni())).thenReturn(invoicesFromUserList);

        List<InvoicesFromUser> invoicesFromUserResult = invoiceController.getInvoicesPaidFromUser(user.getDni());

        assertEquals(true, invoicesFromUserResult.get(0).getPaid());
    }

    @Test(expected = UserNotExistsException.class)
    public void getInvoicesPaidFromUserNotExists() throws UserNotExistsException, ValidationException {
        when(invoiceService.getInvoicesPaidFromUser("41307541")).thenReturn(null);
        invoiceController.getInvoicesPaidFromUser("41307541");
    }

    @Test
    public void getInvoicesNotPaidFromUserOk() throws UserNotExistsException, ValidationException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        user.setTelephoneLines(telephoneLines);

        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, telephoneLine1, user);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoicesFromUser invoicesFromUser = factory.createProjection(InvoicesFromUser.class);
        invoicesFromUser.setDni(user.getDni());
        invoicesFromUser.setDate_creation(invoice.getDateCreation());
        invoicesFromUser.setDate_expiration(invoice.getDateExpiration());
        invoicesFromUser.setFirstname(user.getFirstname());
        invoicesFromUser.setLastname(user.getLastname());
        invoicesFromUser.setLine_number(telephoneLine1.getLineNumber());
        invoicesFromUser.setPaid(invoice.isPaid());
        invoicesFromUser.setTotal_cost(invoice.getTotalCost());
        invoicesFromUser.setTotal_price(invoice.getTotalPrice());

        List<InvoicesFromUser> invoicesFromUserList = new ArrayList<InvoicesFromUser>();
        invoicesFromUserList.add(invoicesFromUser);

        when(invoiceService.getInvoicesNotPaidFromUser(user.getDni())).thenReturn(invoicesFromUserList);

        List<InvoicesFromUser> invoicesFromUserResult = invoiceController.getInvoicesNotPaidFromUser(user.getDni());

        assertEquals(false, invoicesFromUserResult.get(0).getPaid());
    }

    @Test(expected = UserNotExistsException.class)
    public void getInvoicesNotPaidFromUserNotExists() throws UserNotExistsException, ValidationException {
        when(invoiceService.getInvoicesNotPaidFromUser("41307541")).thenReturn(null);
        invoiceController.getInvoicesNotPaidFromUser("41307541");
    }

    @Test
    public void getInvoicesFromMonthOk() throws DateNotExistsException, ValidationException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        user.setTelephoneLines(telephoneLines);

        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, telephoneLine1, user);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoicesRequestFromPeriods invoicesRequestFromPeriods = factory.createProjection(InvoicesRequestFromPeriods.class);
        invoicesRequestFromPeriods.setDate_creation(invoice.getDateCreation());
        invoicesRequestFromPeriods.setDate_expiration(invoice.getDateExpiration());
        invoicesRequestFromPeriods.setLine_number(telephoneLine1.getLineNumber());
        invoicesRequestFromPeriods.setPaid(invoice.isPaid());
        invoicesRequestFromPeriods.setTotal_cost(invoice.getTotalCost());
        invoicesRequestFromPeriods.setTotal_price(invoice.getTotalPrice());

        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriodsList = new ArrayList<InvoicesRequestFromPeriods>();
        invoicesRequestFromPeriodsList.add(invoicesRequestFromPeriods);

        when(invoiceService.getInvoicesFromMonth("06")).thenReturn(invoicesRequestFromPeriodsList);

        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriodsResult = invoiceController.getInvoicesFromMonth("06");

        assertEquals(invoicesRequestFromPeriodsList, invoicesRequestFromPeriodsResult);
    }

    @Test
    public void getInvoicesFromMonthEmptyTest()
    {
        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriods = new ArrayList<InvoicesRequestFromPeriods>();
        when(invoiceService.getInvoicesFromMonth("06")).thenReturn(invoicesRequestFromPeriods);
        invoiceService.getInvoicesFromMonth("06");
    }

    @Test
    public void getInvoicesFromYearOk() throws DateNotExistsException, ValidationException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        user.setTelephoneLines(telephoneLines);

        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, telephoneLine1, user);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoicesRequestFromPeriods invoicesRequestFromPeriods = factory.createProjection(InvoicesRequestFromPeriods.class);
        invoicesRequestFromPeriods.setDate_creation(invoice.getDateCreation());
        invoicesRequestFromPeriods.setDate_expiration(invoice.getDateExpiration());
        invoicesRequestFromPeriods.setLine_number(telephoneLine1.getLineNumber());
        invoicesRequestFromPeriods.setPaid(invoice.isPaid());
        invoicesRequestFromPeriods.setTotal_cost(invoice.getTotalCost());
        invoicesRequestFromPeriods.setTotal_price(invoice.getTotalPrice());

        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriodsList = new ArrayList<InvoicesRequestFromPeriods>();
        invoicesRequestFromPeriodsList.add(invoicesRequestFromPeriods);

        when(invoiceService.getInvoicesFromYear("2020")).thenReturn(invoicesRequestFromPeriodsList);

        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriodsResult = invoiceController.getInvoicesFromYear("2020");

        assertEquals(invoicesRequestFromPeriodsList, invoicesRequestFromPeriodsResult);
    }

    @Test
    public void getInvoicesFromYearEmptyTest()
    {
        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriods = new ArrayList<InvoicesRequestFromPeriods>();
        when(invoiceService.getInvoicesFromYear("2020")).thenReturn(invoicesRequestFromPeriods);
        invoiceService.getInvoicesFromYear("2020");
    }

    @Test
    public void getInvoicesBetweenDatesOk() throws DateNotExistsException, ValidationException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        user.setTelephoneLines(telephoneLines);

        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, telephoneLine1, user);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoicesRequestFromPeriods invoicesRequestFromPeriods = factory.createProjection(InvoicesRequestFromPeriods.class);
        invoicesRequestFromPeriods.setDate_creation(invoice.getDateCreation());
        invoicesRequestFromPeriods.setDate_expiration(invoice.getDateExpiration());
        invoicesRequestFromPeriods.setLine_number(telephoneLine1.getLineNumber());
        invoicesRequestFromPeriods.setPaid(invoice.isPaid());
        invoicesRequestFromPeriods.setTotal_cost(invoice.getTotalCost());
        invoicesRequestFromPeriods.setTotal_price(invoice.getTotalPrice());

        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriodsList = new ArrayList<InvoicesRequestFromPeriods>();
        invoicesRequestFromPeriodsList.add(invoicesRequestFromPeriods);

        when(invoiceService.getInvoicesBetweenDates(Date.valueOf("2020-06-06"), Date.valueOf("2020-06-28"))).thenReturn(invoicesRequestFromPeriodsList);

        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriodsResult = invoiceController.getInvoicesBetweenDates(Date.valueOf("2020-06-06"), Date.valueOf("2020-06-28"));

        assertEquals(invoicesRequestFromPeriodsList, invoicesRequestFromPeriodsResult);
    }

    @Test
    public void getInvoicesBetweenDatesEmpty()
    {
        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriods = new ArrayList<InvoicesRequestFromPeriods>();
        when(invoiceService.getInvoicesBetweenDates(Date.valueOf("2020-06-06"), Date.valueOf("2020-06-28"))).thenReturn(invoicesRequestFromPeriods);
        invoiceService.getInvoicesBetweenDates(Date.valueOf("2020-06-06"), Date.valueOf("2020-06-28"));
    }

    @Test
    public void getIncomeTest()
    {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        user.setTelephoneLines(telephoneLines);

        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), true, telephoneLine1, user);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoiceIncome invoiceIncome = factory.createProjection(InvoiceIncome.class);
        invoiceIncome.setIncome(3);

        when(invoiceService.getIncome()).thenReturn(invoiceIncome);

        InvoiceIncome invoiceIncomeResult = invoiceController.getIncome();

        assertEquals(invoiceIncome, invoiceIncomeResult);
    }

    @Test
    public void getIncomeMonthTest() throws DateNotExistsException, ValidationException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        user.setTelephoneLines(telephoneLines);

        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), true, telephoneLine1, user);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoiceIncome invoiceIncome = factory.createProjection(InvoiceIncome.class);
        invoiceIncome.setIncome(3);

        when(invoiceService.getIncomeMonth("06")).thenReturn(invoiceIncome);

        InvoiceIncome invoiceIncomeResult = invoiceController.getIncomeMonth("06");

        assertEquals(invoiceIncome, invoiceIncomeResult);
    }

    @Test
    public void getIncomeYearTest() throws DateNotExistsException, ValidationException {
        User user = new User(1, "Bianca", "Pilegi", "41307541", "bpilegi98", "1234", null, true, null, null, null);

        TelephoneLine telephoneLine1 = new TelephoneLine(1, "2235678987", null, null, user);

        List<TelephoneLine> telephoneLines = new ArrayList<TelephoneLine>();
        telephoneLines.add(telephoneLine1);
        user.setTelephoneLines(telephoneLines);

        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), true, telephoneLine1, user);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoiceIncome invoiceIncome = factory.createProjection(InvoiceIncome.class);
        invoiceIncome.setIncome(3);

        when(invoiceService.getIncomeYear("2020")).thenReturn(invoiceIncome);

        InvoiceIncome invoiceIncomeResult = invoiceController.getIncomeYear("2020");

        assertEquals(invoiceIncome, invoiceIncomeResult);
    }
}