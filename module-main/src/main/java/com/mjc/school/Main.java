package com.mjc.school;

import com.mjc.school.service.exceptions.ResourceNotFoundException;
import com.mjc.school.service.exceptions.ValidatorException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Utils utils = new Utils();

        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                utils.menu();
                switch (input.nextLine()) {
                    case "1" -> utils.getAllNews();
                    case "2" -> utils.getNewsById(input);
                    case "3" -> utils.createNews(input);
                    case "4" -> utils.updateNews(input);
                    case "5" -> utils.deleteNews(input);
                    case "0" -> System.exit(0);
                    default -> System.out.println("Wrong input, please try again.");
                }
            } catch (ValidatorException e) {
                System.out.println(e.getMessage() + " Error code: " + e.getErrorCode());
            } catch (ResourceNotFoundException e) {
                System.out.println(e.getMessage() + " Error code: " + e.getErrorCode());
            }
        }
    }
}
