package br.com.medmentor.util;

import java.text.Normalizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.stream.Collectors;

public class LoginGenerator {

    private static final int MAX_LOGIN_LENGTH = 20;

    public String generateLogin(String fullName, List<String> existingLogins) {
        Set<String> existingLoginsSet = new HashSet<>(existingLogins);

        String cleanName = Normalizer.normalize(fullName.trim().toLowerCase(), Normalizer.Form.NFD)
        		.replaceAll("\\p{InCombiningDiacriticalMarks}+", "") 
                                     .replaceAll("[^a-z\s]", ""); 

        String[] parts = cleanName.split("\s+");
        List<String> validParts = Arrays.stream(parts)
                                        .filter(s -> !s.isEmpty() && !isPreposition(s)) 
                                        .collect(Collectors.toList());

        if (validParts.isEmpty()) {
            return "";
        } 

        String firstName = validParts.get(0);
        String lastName = (validParts.size() > 1) ? validParts.get(validParts.size() - 1) : "";
        String middleInitial = "";
        if (validParts.size() > 2) { 
            if (!validParts.get(1).isEmpty()) {
                middleInitial = String.valueOf(validParts.get(1).charAt(0));
            }
        }

        if (lastName.isEmpty()) {
            String candidate = truncate(firstName, MAX_LOGIN_LENGTH);
            if (!existingLoginsSet.contains(candidate)) {
                return candidate;
            }
            return ""; 
        }

        List<String> candidatePatterns = new ArrayList<>();

        candidatePatterns.add(createLoginBase(firstName, lastName));
        candidatePatterns.add(createLoginBase(firstName, String.valueOf(lastName.charAt(0))));
        if (firstName.length() > 0) 
            candidatePatterns.add(createLoginBase(String.valueOf(firstName.charAt(0)), lastName));
        if (firstName.length() > 0 && lastName.length() > 0)
            candidatePatterns.add(createLoginBase(String.valueOf(firstName.charAt(0)), String.valueOf(lastName.charAt(0))));

        if (!middleInitial.isEmpty()) {
            candidatePatterns.add(createLoginBase(firstName + middleInitial, String.valueOf(lastName.charAt(0))));
            if (firstName.length() > 0)
                candidatePatterns.add(createLoginBase(String.valueOf(firstName.charAt(0)) + middleInitial, String.valueOf(lastName.charAt(0))));
            candidatePatterns.add(createLoginBase(firstName, middleInitial + lastName));
            if (firstName.length() > 0)
                candidatePatterns.add(createLoginBase(String.valueOf(firstName.charAt(0)), middleInitial + lastName));
        }

        for (String pattern : candidatePatterns) {
            if (pattern.isEmpty() || pattern.equals(".")) continue; 

            String candidate = truncate(pattern, MAX_LOGIN_LENGTH);
            if (!candidate.isEmpty() && !existingLoginsSet.contains(candidate)) {
                return candidate; 
            }
        }

        return ""; 
    }

    private boolean isPreposition(String s) {
        return Arrays.asList("da", "de", "do", "das", "dos", "e", "com", "sem", "por", "em", "para", "a", "o", "as", "os", "ao", "aos", "�", "�s", "dum", "duma", "duns", "dumas", "pelo", "pela", "pelos", "pelas", "no", "na", "nos", "nas").contains(s);
    }

    private String createLoginBase(String part1, String part2) {
        if (part1 == null || part1.isEmpty() || part2 == null || part2.isEmpty()) {
            return "";
        }
        return part1 + "." + part2;
    }

    private String truncate(String text, int maxLength) {
        if (text == null) {
            return "";
        }
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength);
    }
}
