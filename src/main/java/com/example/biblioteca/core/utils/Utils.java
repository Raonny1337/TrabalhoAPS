package com.example.biblioteca.core.utils;

import java.util.Scanner;

public class Utils {

    public static int getValidIntegerInput(String prompt, Scanner scanner) {
        int validInput = -1;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print(prompt);
                validInput = Integer.parseInt(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.println("Número inválido");
            }
        }
        return validInput;
    }
}
