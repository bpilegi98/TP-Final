package com.utn.TP_Final.controller.web;


import com.utn.TP_Final.controller.CallController;
import com.utn.TP_Final.controller.InvoiceController;
import com.utn.TP_Final.controller.UserController;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.projections.InvoicesBetweenDatesUser;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import com.utn.TP_Final.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerWebController {

    private final CallController callController;
    private final InvoiceController invoiceController;
    private final UserController userController;
    private final SessionManager sessionManager;

    @Autowired
    public CustomerWebController(CallController callController, InvoiceController invoiceController, UserController userController, SessionManager sessionManager) {
        this.callController = callController;
        this.invoiceController = invoiceController;
        this.userController = userController;
        this.sessionManager = sessionManager;
    }



    @GetMapping("/getCallsBetweenDates")
    public ResponseEntity<List<CallsBetweenDates>> getCallsBetweenDates(@RequestHeader("Authorization") String sessionToken,
                                                                        @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                                                        @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date to,
                                                                        Integer idLoggedUser) throws ValidationException, UserNotExistsException
    {

            User currentUser = sessionManager.getLoggedUser(sessionToken);

            ResponseEntity<List<CallsBetweenDates>> callsBetweenDates = userController.getCallsBetweenDates(from, to, currentUser.getId());
            return (callsBetweenDates != null) ? callsBetweenDates : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
       }

    @GetMapping("/getInvoicesBetweenDates")
    public ResponseEntity<List<InvoicesBetweenDatesUser>> getInvoicesBetweenDates(@RequestHeader("Authorization")String sessionToken,
                                                                                  @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                                                                  @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date to, Integer idLoggedUser) throws ValidationException, UserNotExistsException
    {
            User currentUser = sessionManager.getLoggedUser(sessionToken);

            ResponseEntity<List<InvoicesBetweenDatesUser>> invoicesBetweenDateUsers = userController.getInvoicesBetweenDates(from, to, currentUser.getId());
            return (invoicesBetweenDateUsers != null) ? invoicesBetweenDateUsers : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    @GetMapping("/getTopMostCalledDestinations")
    public ResponseEntity<List<TopMostCalledDestinations>> getTopMostCalledDestinations(@RequestHeader("Authorization") String sessionToken, Integer idLoggedUser) throws ValidationException, UserNotExistsException
    {
            User currentUser = sessionManager.getLoggedUser(sessionToken);

            ResponseEntity<List<TopMostCalledDestinations>> topMostCalledDestinations = userController.getTopMostCalledDestinations(currentUser.getId());
            return (topMostCalledDestinations != null) ? topMostCalledDestinations : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}


