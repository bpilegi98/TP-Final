package com.utn.TP_Final.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
        ResponseEntity<Invoice> invoiceResult = invoiceController.addInvoice(invoice);
        assertEquals(HttpStatus.CREATED, invoiceResult.getStatusCode());
    }

    @Test
    public void getAllTest()
    {
        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, null, null);
        List<Invoice> invoices = new ArrayList<Invoice>();
        invoices.add(invoice);
        when(invoiceService.getAll()).thenReturn(invoices);
        ResponseEntity<List<Invoice>> invoicesResult = invoiceController.getAll();
        assertEquals(HttpStatus.OK, invoicesResult.getStatusCode());
    }

    @Test
    public void getAllEmptyTest()
    {
        List<Invoice> invoices = new ArrayList<Invoice>();
        when(invoiceService.getAll()).thenReturn(invoices);
        ResponseEntity<List<Invoice>> invoicesResult = invoiceController.getAll();
        assertEquals(HttpStatus.NO_CONTENT, invoicesResult.getStatusCode());
    }

    @Test
    public void deleteInvoiceOk() throws ValidationException
    {
        Invoice invoice = new Invoice(1, 5, 2, Date.valueOf("2020-06-20"), Date.valueOf("2020-07-20"), false, null, null);
        when(invoiceService.deleteInvoice(1)).thenReturn(invoice);
        ResponseEntity<Invoice> invoiceResult = invoiceController.deleteInvoice(1);
        assertEquals(HttpStatus.OK, invoiceResult.getStatusCode());
    }

    @Test(expected = ValidationException.class)
    public void deleteInvoiceNotExists() throws ValidationException
    {
        when(invoiceService.deleteInvoice(1)).thenReturn(null);
        invoiceController.deleteInvoice(1);
    }

    @Test
    public void getInvoicesFromUserOk() throws UserNotExistsException{
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

        ResponseEntity<List<InvoicesFromUser>> invoicesFromUserResult = invoiceController.getInvoicesFromUser(user.getDni());

        assertEquals(HttpStatus.OK, invoicesFromUserResult.getStatusCode());
    }

    @Test(expected = UserNotExistsException.class)
    public void getInvoicesFromUserNotExists() throws UserNotExistsException {
        when(invoiceService.getInvoicesFromUser("41307541")).thenReturn(null);
        invoiceController.getInvoicesFromUser("41307541");
    }

    @Test
    public void getInvoicesPaidFromUserOk() throws UserNotExistsException{
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

        ResponseEntity<List<InvoicesFromUser>> invoicesFromUserResult = invoiceController.getInvoicesPaidFromUser(user.getDni());

        assertEquals(HttpStatus.OK, invoicesFromUserResult.getStatusCode());
    }

    @Test(expected = UserNotExistsException.class)
    public void getInvoicesPaidFromUserNotExists() throws UserNotExistsException{
        when(invoiceService.getInvoicesPaidFromUser("41307541")).thenReturn(null);
        invoiceController.getInvoicesPaidFromUser("41307541");
    }

    @Test
    public void getInvoicesNotPaidFromUserOk() throws UserNotExistsException{
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

        ResponseEntity<List<InvoicesFromUser>> invoicesFromUserResult = invoiceController.getInvoicesNotPaidFromUser(user.getDni());

        assertEquals(HttpStatus.OK, invoicesFromUserResult.getStatusCode());
    }

    @Test(expected = UserNotExistsException.class)
    public void getInvoicesNotPaidFromUserNotExists() throws UserNotExistsException{
        when(invoiceService.getInvoicesNotPaidFromUser("41307541")).thenReturn(null);
        invoiceController.getInvoicesNotPaidFromUser("41307541");
    }

    @Test
    public void getInvoicesFromMonthOk()  {
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

        ResponseEntity<List<InvoicesRequestFromPeriods>> invoicesRequestFromPeriodsResult = invoiceController.getInvoicesFromMonth("06");

        assertEquals(HttpStatus.OK, invoicesRequestFromPeriodsResult.getStatusCode());
    }

    @Test
    public void getInvoicesFromMonthEmptyTest()
    {
        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriods = new ArrayList<InvoicesRequestFromPeriods>();
        when(invoiceService.getInvoicesFromMonth("06")).thenReturn(invoicesRequestFromPeriods);
        invoiceService.getInvoicesFromMonth("06");
    }

    @Test
    public void getInvoicesFromYearOk() {
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

        ResponseEntity<List<InvoicesRequestFromPeriods>> invoicesRequestFromPeriodsResult = invoiceController.getInvoicesFromYear("2020");

        assertEquals(HttpStatus.OK, invoicesRequestFromPeriodsResult.getStatusCode());
    }

    @Test
    public void getInvoicesFromYearEmptyTest()
    {
        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriods = new ArrayList<InvoicesRequestFromPeriods>();
        when(invoiceService.getInvoicesFromYear("2020")).thenReturn(invoicesRequestFromPeriods);
        invoiceService.getInvoicesFromYear("2020");
    }

    @Test
    public void getInvoicesBetweenDatesOk()  {
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

        ResponseEntity<List<InvoicesRequestFromPeriods>> invoicesRequestFromPeriodsResult = invoiceController.getInvoicesBetweenDates(Date.valueOf("2020-06-06"), Date.valueOf("2020-06-28"));

        assertEquals(HttpStatus.OK, invoicesRequestFromPeriodsResult.getStatusCode());
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

        ResponseEntity<InvoiceIncome> invoiceIncomeResult = invoiceController.getIncome();

        assertEquals(HttpStatus.OK, invoiceIncomeResult.getStatusCode());
    }

    @Test
    public void getIncomeMonthTest()  {
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

        ResponseEntity<InvoiceIncome> invoiceIncomeResult = invoiceController.getIncomeMonth("06");

        assertEquals(HttpStatus.OK, invoiceIncomeResult.getStatusCode());
    }

    @Test
    public void getIncomeYearTest()  {
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

        ResponseEntity<InvoiceIncome> invoiceIncomeResult = invoiceController.getIncomeYear("2020");

        assertEquals(HttpStatus.OK, invoiceIncomeResult.getStatusCode());
    }
}
