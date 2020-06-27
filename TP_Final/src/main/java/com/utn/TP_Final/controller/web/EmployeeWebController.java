package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.*;
import com.utn.TP_Final.exceptions.*;
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
        return (users != null) ? users : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/users")
    public ResponseEntity newPerson(@RequestHeader("Authorization") String sessionToken, @RequestBody User user) throws ValidationException, InvalidKeySpecException, NoSuchAlgorithmException, UserAlreadyExistsException
    {
            return userController.addUser(user);
    }

    @PostMapping("/users/delete")
    public ResponseEntity deletePerson(@RequestHeader("Authorization")String sessionToken, @RequestBody String dni) throws ValidationException, UserNotExistsException
    {
            return userController.removeUser(dni);
    }

    @GetMapping("/telephoneLines")
    public ResponseEntity<List<TelephoneLine>> getTelephoneLines(@RequestHeader("Authorization") String sessionToken)
    {
        ResponseEntity<List<TelephoneLine>> telephoneLines = telephoneLineController.getAll(null);
        return (telephoneLines != null) ? telephoneLines : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/telephoneLines")
    public ResponseEntity newTelephoneLine(@RequestHeader("Authorization") String sessionToken, @RequestBody TelephoneLine telephoneLine) throws ValidationException
    {
            return telephoneLineController.addTelephoneLine(telephoneLine);
    }

    @PostMapping("/telephoneLines/delete")
    public ResponseEntity deleteTelephoneLine(@RequestHeader("Authorization") String sessionToken, @RequestBody String lineNumber) throws ValidationException
    {
            return telephoneLineController.removeTelephoneLine(lineNumber);
    }

    @PutMapping("/telephoneLines/suspend")
    public ResponseEntity suspendTelephoneLine(@RequestHeader("Authorization")String sessionToken, @RequestBody String lineNumber) throws ValidationException
    {
        return telephoneLineController.suspendTelephoneLine(lineNumber);
    }

    @PutMapping("/telephoneLines/active")
    public ResponseEntity activeTelephoneLine(@RequestHeader("Authorization")String sessionToken, @RequestBody String lineNumber) throws ValidationException
    {
        return telephoneLineController.activeTelephoneLine(lineNumber);
    }

    @GetMapping("/getCallsFromUserSimple/{dni}")
    public ResponseEntity<CallsFromUserSimple> getCallsFromUserSimple(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni) throws UserNotExistsException
    {
        ResponseEntity<CallsFromUserSimple> callsFromUserSimple = callController.getCallsFromUserSimple(dni);
        return (callsFromUserSimple != null) ? callsFromUserSimple : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getCallsFromUser/{dni}")
    public ResponseEntity<List<CallsFromUser>> getCallsFromUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni) throws UserNotExistsException
    {
        ResponseEntity<List<CallsFromUser>> callsFromUsers = callController.getCallsFromUser(dni);
        return (callsFromUsers != null) ? callsFromUsers : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getFeeByIdCities/{idCityFrom}/{idCityTo}")
    public ResponseEntity<FeeRequest> getFeeByIdCities(@RequestHeader("Authorization")String sessionToken, @PathVariable Integer idCityFrom, @PathVariable Integer idCityTo) throws ValidationException
    {
        ResponseEntity<FeeRequest> feeRequest = feeController.getFeeByIdCities(idCityFrom, idCityTo);
        return (feeRequest != null) ? feeRequest : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getFeeByNameCities/{cityFrom}/{cityTo}")
    public ResponseEntity<FeeRequest> getFeeByNameCities(@RequestHeader("Authorization")String sessionToken, @PathVariable String cityFrom, @PathVariable String cityTo) throws ValidationException
    {
        ResponseEntity<FeeRequest> feeRequest = feeController.getFeeByNameCities(cityFrom, cityTo);
        return (feeRequest != null) ? feeRequest : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesFromUser/{dni}")
    public ResponseEntity<List<InvoicesFromUser>> getInvoicesFromUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni) throws ValidationException, UserNotExistsException
    {
        ResponseEntity<List<InvoicesFromUser>> invoicesFromUsers = invoiceController.getInvoicesFromUser(dni);
        return (invoicesFromUsers != null) ? invoicesFromUsers : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesPaidFromUser/{dni}")
    public ResponseEntity<List<InvoicesFromUser>> getInvoicesPaidFromUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni) throws ValidationException, UserNotExistsException
    {
        ResponseEntity<List<InvoicesFromUser>> invoicesFromUsers = invoiceController.getInvoicesPaidFromUser(dni);
        return (invoicesFromUsers != null) ? invoicesFromUsers : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesNotPaidFromUser/{dni}")
    public ResponseEntity<List<InvoicesFromUser>> getInvoicesNotPaidFromUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni) throws ValidationException, UserNotExistsException
    {
        ResponseEntity<List<InvoicesFromUser>> invoicesFromUsers = invoiceController.getInvoicesNotPaidFromUser(dni);
        return (invoicesFromUsers != null) ? invoicesFromUsers : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesFromMonth/{monthI}")
    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromMonth(@RequestHeader("Authorization")String sessionToken, @PathVariable String monthI) throws ValidationException
    {
        ResponseEntity<List<InvoicesRequestFromPeriods>> invoicesRequestFromPeriods = invoiceController.getInvoicesFromMonth(monthI);
        return (invoicesRequestFromPeriods != null) ? invoicesRequestFromPeriods : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesFromYear/{yearI}")
    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromYear(@RequestHeader("Authorization")String sessionToken, @PathVariable String yearI) throws ValidationException
    {
        ResponseEntity<List<InvoicesRequestFromPeriods>> invoicesRequestFromPeriods = invoiceController.getInvoicesFromYear(yearI);
        return (invoicesRequestFromPeriods != null) ? invoicesRequestFromPeriods : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesBetweenDates/{fromI}/{toI}")
    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesBetweenDates(@RequestHeader("Authorization")String sessionToken, @PathVariable Date fromI, @PathVariable Date toI) throws ValidationException
    {
        ResponseEntity<List<InvoicesRequestFromPeriods>> invoicesRequestFromPeriods = invoiceController.getInvoicesBetweenDates(fromI, toI);
        return (invoicesRequestFromPeriods != null) ? invoicesRequestFromPeriods : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getIncome")
    public ResponseEntity<InvoiceIncome> getIncome(@RequestHeader("Authorization")String sessionToken)
    {
        ResponseEntity<InvoiceIncome> invoiceIncome = invoiceController.getIncome();
        return (invoiceIncome != null) ? invoiceIncome : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getIncomeMonth/{monthI}")
    public ResponseEntity<InvoiceIncome> getIncomeMonth(@RequestHeader("Authorization")String sessionToken, @PathVariable String monthI) throws ValidationException
    {
        ResponseEntity<InvoiceIncome> invoiceIncome = invoiceController.getIncomeMonth(monthI);
        return (invoiceIncome != null) ? invoiceIncome : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getIncomeYear/{yearI}")
    public ResponseEntity<InvoiceIncome> getIncomeYear(@RequestHeader("Authorization")String sessionToken, @PathVariable String yearI) throws ValidationException
    {
        ResponseEntity<InvoiceIncome> invoiceIncome = invoiceController.getIncomeYear(yearI);
        return (invoiceIncome != null) ? invoiceIncome : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
