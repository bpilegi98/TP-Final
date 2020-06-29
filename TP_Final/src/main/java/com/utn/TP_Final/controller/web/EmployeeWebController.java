package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.*;
import com.utn.TP_Final.dto.CallsUserDto;
import com.utn.TP_Final.exceptions.*;
import com.utn.TP_Final.model.Call;
import com.utn.TP_Final.model.Fee;
import com.utn.TP_Final.model.TelephoneLine;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.*;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/backoffice")
public class EmployeeWebController {

    private final UserController userController;
    private final TelephoneLineController telephoneLineController;
    private final CallController callController;
    private final FeeController feeController;
    private final InvoiceController invoiceController;
    private final SessionManager sessionManager;

    @Autowired
    public EmployeeWebController(UserController userController, TelephoneLineController telephoneLineController, CallController callController, FeeController feeController, InvoiceController invoiceController, SessionManager sessionManager) {
        this.userController = userController;
        this.telephoneLineController = telephoneLineController;
        this.callController = callController;
        this.feeController = feeController;
        this.invoiceController = invoiceController;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessionToken)
    {
        ResponseEntity<List<User>> users = userController.getAll(null);
        return (!users.getBody().isEmpty()) ? users : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/users")
    public ResponseEntity newUser(@RequestHeader("Authorization") String sessionToken, @RequestBody User user) throws ValidationException, InvalidKeySpecException, NoSuchAlgorithmException, UserAlreadyExistsException
    {
            return userController.addUser(user);
    }


    @PutMapping("/users/active/{dni}")
    public ResponseEntity<User> activeUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni) throws UserNotExistsException
    {
        return userController.activeUser(dni);
    }

    @PutMapping("/users/suspend/{dni}")
    public ResponseEntity<User> suspendUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni) throws UserNotExistsException
    {
        return userController.suspendUser(dni);
    }

    @GetMapping("/telephoneLines")
    public ResponseEntity<List<TelephoneLine>> getTelephoneLines(@RequestHeader("Authorization") String sessionToken)
    {
        ResponseEntity<List<TelephoneLine>> telephoneLines = telephoneLineController.getAll(null);
        return (!telephoneLines.getBody().isEmpty()) ? telephoneLines : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/telephoneLines")
    public ResponseEntity<TelephoneLine> newTelephoneLine(@RequestHeader("Authorization") String sessionToken, @RequestBody TelephoneLine telephoneLine) throws ValidationException
    {
            return telephoneLineController.addTelephoneLine(telephoneLine);
    }

    @PutMapping("/telephoneLines/suspend/{lineNumber}")
    public ResponseEntity<TelephoneLine> suspendTelephoneLine(@RequestHeader("Authorization")String sessionToken, @PathVariable String lineNumber) throws ValidationException
    {
        return telephoneLineController.suspendTelephoneLine(lineNumber);
    }

    @PutMapping("/telephoneLines/active/{lineNumber}")
    public ResponseEntity<TelephoneLine> activeTelephoneLine(@RequestHeader("Authorization")String sessionToken, @PathVariable String lineNumber) throws ValidationException
    {
        return telephoneLineController.activeTelephoneLine(lineNumber);
    }

    @GetMapping("/calls/simple/{dni}")
    public ResponseEntity<CallsFromUserSimple> getCallsFromUserSimple(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni) throws UserNotExistsException
    {
        ResponseEntity<CallsFromUserSimple> callsFromUserSimple = callController.getCallsFromUserSimple(dni);
        return (callsFromUserSimple != null) ? callsFromUserSimple : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/calls", params = "dni")
    public ResponseEntity<List<Call>> getCallsFromUser(@RequestHeader("Authorization")String sessionToken, @RequestParam String dni) throws UserNotExistsException
    {
        ResponseEntity<List<Call>> callsFromUsersList = callController.getCallsFromUser(dni);
        return (!callsFromUsersList.getBody().isEmpty()) ? callsFromUsersList : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/calls")
    public ResponseEntity<List<Call>> getCalls(@RequestHeader("Authorization")String sessionToken){
        ResponseEntity<List<Call>> calls = callController.getAll();
        return (!calls.getBody().isEmpty()) ? calls : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping(value = "/fees",params = {"idCityFrom","idCityTo"})
    public ResponseEntity<FeeRequest> getFeeByIdCities(@RequestHeader("Authorization")String sessionToken, @RequestParam Integer idCityFrom, @RequestParam Integer idCityTo) throws ValidationException
    {
        ResponseEntity<FeeRequest> feeRequest = feeController.getFeeByIdCities(idCityFrom, idCityTo);
        return (feeRequest != null) ? feeRequest : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/fees",params = {"cityFrom","cityTo"})
    public ResponseEntity<FeeRequest> getFeeByNameCities(@RequestHeader("Authorization")String sessionToken, @RequestParam String cityFrom, @RequestParam String cityTo) throws ValidationException
    {
        ResponseEntity<FeeRequest> feeRequest = feeController.getFeeByNameCities(cityFrom, cityTo);
        return (feeRequest != null) ? feeRequest : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/invoices", params = "dni")
    public ResponseEntity<List<InvoicesFromUser>> getInvoicesFromUser(@RequestHeader("Authorization")String sessionToken, @RequestParam String dni) throws ValidationException, UserNotExistsException
    {
        ResponseEntity<List<InvoicesFromUser>> invoicesFromUsers = invoiceController.getInvoicesFromUser(dni);
        return (!invoicesFromUsers.getBody().isEmpty()) ? invoicesFromUsers : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/invoices/paid")
    public ResponseEntity<List<InvoicesFromUser>> getInvoicesPaidFromUser(@RequestHeader("Authorization")String sessionToken, @RequestParam String dni) throws ValidationException, UserNotExistsException
    {
        ResponseEntity<List<InvoicesFromUser>> invoicesFromUsers = invoiceController.getInvoicesPaidFromUser(dni);
        return (!invoicesFromUsers.getBody().isEmpty()) ? invoicesFromUsers : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/invoices/notPaid")
    public ResponseEntity<List<InvoicesFromUser>> getInvoicesNotPaidFromUser(@RequestHeader("Authorization")String sessionToken, @RequestParam String dni) throws ValidationException, UserNotExistsException
    {
        ResponseEntity<List<InvoicesFromUser>> invoicesFromUsers = invoiceController.getInvoicesNotPaidFromUser(dni);
        return (!invoicesFromUsers.getBody().isEmpty()) ? invoicesFromUsers : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/invoices",params = {"month","year"})
    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromMonth(@RequestHeader("Authorization")String sessionToken, @RequestParam String month, @RequestParam String year) throws ValidationException
    {
        ResponseEntity<List<InvoicesRequestFromPeriods>> invoicesRequestFromPeriods = invoiceController.getInvoicesFromMonth(month,year);
        return (!invoicesRequestFromPeriods.getBody().isEmpty()) ? invoicesRequestFromPeriods : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/invoices",params = "year")
    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromYear(@RequestHeader("Authorization")String sessionToken, @RequestParam String year) throws ValidationException
    {
        ResponseEntity<List<InvoicesRequestFromPeriods>> invoicesRequestFromPeriods = invoiceController.getInvoicesFromYear(year);
        return (!invoicesRequestFromPeriods.getBody().isEmpty()) ? invoicesRequestFromPeriods : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/invoices",params = {"from","to"})
    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesBetweenDates(@RequestHeader("Authorization")String sessionToken, @RequestParam Date from, @RequestParam Date to) throws ValidationException
    {
        ResponseEntity<List<InvoicesRequestFromPeriods>> invoicesRequestFromPeriods = invoiceController.getInvoicesBetweenDates(from, to);
        return (!invoicesRequestFromPeriods.getBody().isEmpty()) ? invoicesRequestFromPeriods : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/income")
    public ResponseEntity<InvoiceIncome> getIncome(@RequestHeader("Authorization")String sessionToken)
    {
        ResponseEntity<InvoiceIncome> invoiceIncome = invoiceController.getIncome();
        return (invoiceIncome != null) ? invoiceIncome : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/income",params = {"month","year"})
    public ResponseEntity<InvoiceIncome> getIncomeMonth(@RequestHeader("Authorization")String sessionToken, @RequestParam String month,@RequestParam String year) throws ValidationException
    {
        ResponseEntity<InvoiceIncome> invoiceIncome = invoiceController.getIncomeMonth(month,year);
        return (invoiceIncome != null) ? invoiceIncome : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/income",params = "year")
    public ResponseEntity<InvoiceIncome> getIncomeYear(@RequestHeader("Authorization")String sessionToken, @RequestParam String year) throws ValidationException
    {
        ResponseEntity<InvoiceIncome> invoiceIncome = invoiceController.getIncomeYear(year);
        return (invoiceIncome != null) ? invoiceIncome : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
