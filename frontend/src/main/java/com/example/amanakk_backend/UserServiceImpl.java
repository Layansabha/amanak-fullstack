package com.example.amanakk_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAccountsRepository userAccountsRepository;

    @Autowired
    private IdNumbersRepository idNumbersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Optional<UserAccounts> findUserByPhoneOrId(String identifier) {
        return userAccountsRepository.findByPhoneNumberOrNationalId(identifier, identifier);
    }

    @Override
    public void sendOtp(String phoneNumber) {
        // Replace with actual OTP sending logic
        System.out.println("Sending OTP to: " + phoneNumber); // Placeholder
    }

    @Override
    public void registerNewUser(UserAccountsRequest request) throws Exception {
        Optional<IdNumbers> verifiedUser = idNumbersRepository.findByIdNumberAndNationalId(
                request.getIdNumber(), request.getNationalId());

        if (verifiedUser.isPresent()) {
            IdNumbers idNumbers = verifiedUser.get();
            UserAccounts newUser = new UserAccounts();
            newUser.setIdNumber(idNumbers.getIdNumber());
            newUser.setNationalId(idNumbers.getNationalId());
            newUser.setFullName(idNumbers.getFullName());
            newUser.setBirthdate(idNumbers.getBirthdate());
            newUser.setPhoneNumber(request.getPhoneNumber());
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));  // Hash password

            userAccountsRepository.save(newUser);
        } else {
            throw new Exception("ID verification failed, user not found.");
        }
    }

    @Override
    public boolean verifyIdNumber(IdNumbersRequest request) throws ParseException {
        Date birthdate = dateFormat.parse(request.getBirthdate());

        Optional<IdNumbers> verifiedUser = idNumbersRepository.findByIdNumberAndNationalIdAndBirthdate(
                request.getIdNumber(), request.getNationalId(), birthdate);

        return verifiedUser.isPresent();
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest request) {
        System.out.println("[LoginService] authenticateUser called with identifier=" + request.getIdOrPhone());
        Optional<UserAccounts> optionalUser = userAccountsRepository.findByPhoneNumberOrNationalId(
                request.getIdOrPhone(), request.getIdOrPhone());

        if (optionalUser.isPresent()) {
            UserAccounts user = optionalUser.get();
            boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());

            if (passwordMatches) {
                String nationalId = user.getNationalId();
                String idNumber = user.getIdNumber();
                String fullName = user.getFullName();
                System.out.println("[LoginService] login successful for identifier=" + request.getIdOrPhone() + ", fullName=" + fullName + ", nationalId=" + nationalId + ", idNumber=" + idNumber);
                return new LoginResponse(true, "Login successful", nationalId, idNumber, fullName, user.getPhoneNumber(), user.getEmail());
            } else {
                System.out.println("[LoginService] incorrect password for identifier=" + request.getIdOrPhone());
                return new LoginResponse(false, "Incorrect password");
            }
        }

        System.out.println("[LoginService] user not found for identifier=" + request.getIdOrPhone());
        return new LoginResponse(false, "User not found");
    }
    @Override
    public UserAccounts updateUser(Long userId, UserAccounts updatedUser) {
        return userAccountsRepository.findById(userId).map(user -> {
            user.setFullName(updatedUser.getFullName());
            user.setNationalId(updatedUser.getNationalId());
            user.setBirthdate(updatedUser.getBirthdate());
            user.setEmail(updatedUser.getEmail());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            return userAccountsRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }

}
