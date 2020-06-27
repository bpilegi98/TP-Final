package com.utn.TP_Final.service;

import com.utn.TP_Final.exceptions.UserAlreadyExistsException;
import com.utn.TP_Final.exceptions.UserNotExistsException;
import com.utn.TP_Final.exceptions.ValidationException;
import com.utn.TP_Final.model.User;
import com.utn.TP_Final.projections.CallsBetweenDates;
import com.utn.TP_Final.projections.InvoicesBetweenDatesUser;
import com.utn.TP_Final.projections.TopMostCalledDestinations;
import com.utn.TP_Final.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //devuelve el dni del usuario eliminado
    public String deleteUser(String dni) throws UserNotExistsException
    {
        String dniResult = userRepository.delete(dni);
        return Optional.ofNullable(dniResult).orElseThrow(()-> new UserNotExistsException());
    }

    public List<User> getAll(String dni) {
        if(isNull(dni))
        {
            return userRepository.findAll();
        }
        List<User> users = new ArrayList<User>();
        users.add(userRepository.findByDni(dni));
        return users;
    }

    public User getByDni(String dni) throws UserNotExistsException
    {
        User user = userRepository.findByDni(dni);
        return Optional.ofNullable(user).orElseThrow(()-> new UserNotExistsException());
    }

    public Optional<User> getById(Integer id) throws UserNotExistsException
    {
        Optional<User> user = userRepository.findById(id);
        return Optional.ofNullable(user).orElseThrow(()-> new UserNotExistsException());
    }

    public User getByUsername(String username) throws UserNotExistsException
    {
        User user = userRepository.findByUsername(username);
        return Optional.ofNullable(user).orElseThrow(()-> new UserNotExistsException());
    }


    //hay q chequear cual de los valores q recibe por parametro es el que puede
    // hacer saltar la excepcion y hacer if donde se hagan los orElseThrow correspondientes
    public List<CallsBetweenDates> getCallsBetweenDates(Date from, Date to, Integer idLoggedUser) throws UserNotExistsException
    {
        List<CallsBetweenDates> callsBetweenDates = userRepository.getCallsBetweenDates(from, to, idLoggedUser);
        return Optional.ofNullable(callsBetweenDates).orElseThrow(()-> new UserNotExistsException());
    }


    public List<InvoicesBetweenDatesUser> getInvoicesBetweenDates(Date from, Date to, Integer idLoggedUser) throws UserNotExistsException
    {
        List<InvoicesBetweenDatesUser> invoicesBetweenDatesUsers = userRepository.getInvoicesBetweenDates(from, to, idLoggedUser);
        return Optional.ofNullable(invoicesBetweenDatesUsers).orElseThrow(()-> new UserNotExistsException());
    }

    public List<TopMostCalledDestinations> getTopMostCalledDestinations(Integer idLoggedUser) throws UserNotExistsException
    {
        List<TopMostCalledDestinations> topMostCalledDestinations = userRepository.getTopMostCalledDestinations(idLoggedUser);
        return Optional.ofNullable(topMostCalledDestinations).orElseThrow(()-> new UserNotExistsException());
    }


    //CREAR USUARIO
    public User addUser(User newUser) throws UserAlreadyExistsException, NoSuchAlgorithmException, InvalidKeySpecException {


        newUser.setPassword(generateStrongPasswordHash(newUser.getPassword()));

        User user = userRepository.save(newUser);
        return Optional.ofNullable(user).orElseThrow(()-> new UserAlreadyExistsException());
    }

    public ResponseEntity<User> login(String username, String password) throws UserNotExistsException, InvalidKeySpecException, NoSuchAlgorithmException, ValidationException {
        User user = userRepository.findByUsername(username);
        Boolean validate = validatePassword(password,user.getPassword());

        if(validate)
            return ResponseEntity.ok(user);
        else
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    /*
    COSAS DEL PASSWORD
     */


    //HASHEO
    private static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    //DES-HASHEO

    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }


}
