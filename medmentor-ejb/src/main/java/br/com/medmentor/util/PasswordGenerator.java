package br.com.medmentor.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGenerator {

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGIT_CHARS = "0123456789";
    //private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    private static final String SPECIAL_CHARS = "@#$&"; 
    
    private static final String ALL_CHARS = LOWERCASE_CHARS + UPPERCASE_CHARS + DIGIT_CHARS + SPECIAL_CHARS;

    private static final int PASSWORD_LENGTH = 8;
    
    private static final int MAX_ATTEMPTS = 100; 

    public String generatePassword(String login, List<String> existingPasswords) {
        SecureRandom random = new SecureRandom();
        Set<String> existingPasswordsSet = new HashSet<>(existingPasswords); 

        String generatedPassword = "";
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            List<Character> passwordChars = new ArrayList<>(PASSWORD_LENGTH);
            passwordChars.add(getRandomChar(LOWERCASE_CHARS, random));
            passwordChars.add(getRandomChar(UPPERCASE_CHARS, random));
            passwordChars.add(getRandomChar(DIGIT_CHARS, random));
            passwordChars.add(getRandomChar(SPECIAL_CHARS, random));

            for (int i = 4; i < PASSWORD_LENGTH; i++) {
                passwordChars.add(getRandomChar(ALL_CHARS, random));
            }

            Collections.shuffle(passwordChars, random);

            generatedPassword = passwordChars.stream()
                                             .map(Object::toString)
                                             .collect(Collectors.joining());

            if (!existingPasswordsSet.contains(generatedPassword)) {
                return generatedPassword; 
            }

            attempts++;
        }

        System.err.println("N�o foi poss�vel gerar uma senha �nica para o login '" + login + "' ap�s " + MAX_ATTEMPTS + " tentativas.");
        return ""; 
    }

    private char getRandomChar(String charSet, SecureRandom random) {
        int randomIndex = random.nextInt(charSet.length());
        return charSet.charAt(randomIndex);
    }
    
    public String hashPassword(String plainPassword, int costFactor) {
        String salt = BCrypt.gensalt(costFactor);
        return BCrypt.hashpw(plainPassword, salt);
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }    
}