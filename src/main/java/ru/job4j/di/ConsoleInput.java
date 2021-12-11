package ru.job4j.di;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInput {
    private Scanner scanner = new Scanner(System.in);

    public String input() {
        return scanner.nextLine();
    }
}
