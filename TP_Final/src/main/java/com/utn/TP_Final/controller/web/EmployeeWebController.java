package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.*;
import com.utn.TP_Final.exceptions.TelephoneLineAlreadyExistsException;
import com.utn.TP_Final.exceptions.TelephoneLineNotExistsException;
import com.utn.TP_Final.exceptions.UserAlreadyExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
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
    public ResponseEntity<List<User>> getPersons(@RequestHeader(HttpHeaders.AUTHORIZATION) String sessionToken)
    {

        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<User> users = userController.getAll(null); //asi esta bien pasar el param?
        return (users.size() > 0) ? ResponseEntity.ok(users) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/users/add")
    public ResponseEntity newPerson(@RequestHeader("Authorization") String sessionToken, @RequestBody User user) throws UserAlreadyExistsException
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userController.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/users/delete")
    public ResponseEntity deletePerson(@RequestHeader("Authorization")String sessionToken, @RequestBody String dni) throws UserNotExistsException
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userController.removeUser(dni);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    @GetMapping("/telephoneLines")
    public ResponseEntity<List<TelephoneLine>> getTelephoneLines(@RequestHeader("Authorization") String sessionToken)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<TelephoneLine> telephoneLines = telephoneLineController.getAll(null);
        return (telephoneLines.size() > 0) ? ResponseEntity.ok(telephoneLines) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/telephoneLines/add")
    public ResponseEntity newTelephoneLine(@RequestHeader("Authorization") String sessionToken, @RequestBody TelephoneLine telephoneLine) throws TelephoneLineAlreadyExistsException
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        telephoneLineController.addTelephoneLine(telephoneLine);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/telephoneLines/delete")
    public ResponseEntity deleteTelephoneLine(@RequestHeader("Authorization") String sessionToken, @RequestBody String lineNumber) throws TelephoneLineNotExistsException
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        telephoneLineController.removeTelephoneLine(lineNumber);
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    @PutMapping("/telephoneLines/suspend")
    public ResponseEntity suspendTelephoneLine(@RequestHeader("Authorization")String sessionToken, @RequestBody String lineNumber) throws TelephoneLineNotExistsException
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        telephoneLineController.suspendTelephoneLine(lineNumber);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/telephoneLines/active")
    public ResponseEntity activeTelephoneLine(@RequestHeader("Authorization")String sessionToken, @RequestBody String lineNumber) throws TelephoneLineNotExistsException
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        telephoneLineController.activeTelephoneLine(lineNumber);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/getCallsFromUserSimple")
    public ResponseEntity<CallsFromUserSimple> getCallsFromUserSimple(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        //Acá no hay q poner el String.valueOf()?? Intenté pero no me dejó
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        CallsFromUserSimple callsFromUserSimple = callController.getCallsFromUserSimple(dni);
        return (callsFromUserSimple != null) ? ResponseEntity.ok(callsFromUserSimple) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getCallsFromUser")
    public ResponseEntity<List<CallsFromUser>> getCallsFromUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<CallsFromUser> callsFromUsers = callController.getCallsFromUser(dni);
        return (callsFromUsers.size() > 0) ? ResponseEntity.ok(callsFromUsers) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getFeeByIdCities/{idCityFrom}/{idCityTo}")
    public ResponseEntity<FeeRequest> getFeeByIdCities(@RequestHeader("Authorization")String sessionToken, @PathVariable Integer idCityFrom, @PathVariable Integer idCityTo)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        FeeRequest feeRequest = feeController.getFeeByIdCities(idCityFrom, idCityTo);
        return (feeRequest != null) ? ResponseEntity.ok(feeRequest) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getFeeByNameCities/{cityFrom}/{cityTo}")
    public ResponseEntity<FeeRequest> getFeeByNameCities(@RequestHeader("Authorization")String sessionToken, @PathVariable String cityFrom, @PathVariable String cityTo)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        FeeRequest feeRequest = feeController.getFeeByNameCities(cityFrom, cityTo);
        return (feeRequest != null) ? ResponseEntity.ok(feeRequest) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesFromUser/{dni}")
    public ResponseEntity<List<InvoicesFromUser>> getInvoicesFromUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<InvoicesFromUser> invoicesFromUsers = invoiceController.getInvoicesFromUser(dni);
        return (invoicesFromUsers.size() > 0) ? ResponseEntity.ok(invoicesFromUsers) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesPaidFromUser/{dni}")
    public ResponseEntity<List<InvoicesFromUser>> getInvoicesPaidFromUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<InvoicesFromUser> invoicesFromUsers = invoiceController.getInvoicesPaidFromUser(dni);
        return (invoicesFromUsers.size() > 0) ? ResponseEntity.ok(invoicesFromUsers) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesNotPaidFromUser/{dni}")
    public ResponseEntity<List<InvoicesFromUser>> getInvoicesNotPaidFromUser(@RequestHeader("Authorization")String sessionToken, @PathVariable String dni)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<InvoicesFromUser> invoicesFromUsers = invoiceController.getInvoicesNotPaidFromUser(dni);
        return (invoicesFromUsers.size() > 0) ? ResponseEntity.ok(invoicesFromUsers) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesFromMonth/{monthI}")
    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromMonth(@RequestHeader("Authorization")String sessionToken, @PathVariable String monthI)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriods = invoiceController.getInvoicesFromMonth(monthI);
        return (invoicesRequestFromPeriods.size() > 0) ? ResponseEntity.ok(invoicesRequestFromPeriods) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesFromYear/{yearI}")
    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesFromYear(@RequestHeader("Authorization")String sessionToken, @PathVariable String yearI)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriods = invoiceController.getInvoicesFromYear(yearI);
        return (invoicesRequestFromPeriods.size() > 0) ? ResponseEntity.ok(invoicesRequestFromPeriods) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getInvoicesBetweenDates/{fromI}/{toI}")
    public ResponseEntity<List<InvoicesRequestFromPeriods>> getInvoicesBetweenDates(@RequestHeader("Authorization")String sessionToken, @PathVariable Date fromI, @PathVariable Date toI)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<InvoicesRequestFromPeriods> invoicesRequestFromPeriods = invoiceController.getInvoicesBetweenDates(fromI, toI);
        return (invoicesRequestFromPeriods.size() > 0) ? ResponseEntity.ok(invoicesRequestFromPeriods) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getIncome")
    public ResponseEntity<InvoiceIncome> getIncome(@RequestHeader("Authorization")String sessionToken)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        InvoiceIncome invoiceIncome = invoiceController.getIncome();
        return (invoiceIncome != null) ? ResponseEntity.ok(invoiceIncome) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getIncomeMonth/{monthI}")
    public ResponseEntity<InvoiceIncome> getIncomeMonth(@RequestHeader("Authorization")String sessionToken, @PathVariable String monthI)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        InvoiceIncome invoiceIncome = invoiceController.getIncomeMonth(monthI);
        return (invoiceIncome != null) ? ResponseEntity.ok(invoiceIncome) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/getIncomeYear/{yearI}")
    public ResponseEntity<InvoiceIncome> getIncomeYear(@RequestHeader("Authorization")String sessionToken, @PathVariable String yearI)
    {
        User currentUser = sessionManager.getLoggedUser(sessionToken);
        if(currentUser == null || currentUser.getUserType().equals("CUSTOMER"))
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        InvoiceIncome invoiceIncome = invoiceController.getIncomeYear(yearI);
        return (invoiceIncome != null) ? ResponseEntity.ok(invoiceIncome) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
