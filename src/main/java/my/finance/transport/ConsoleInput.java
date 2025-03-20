package my.finance.transport;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {
    private final Scanner scanner;

    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    public void close() {
        scanner.close();
    }


    @Override
    public String next() {
        String inputString = scanner.next();
        scanner.nextLine();
        return inputString;
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public int nextInt() {
        int inputInt = scanner.nextInt();
        scanner.nextLine();
        return inputInt;
    }

    @Override
    public double nextDouble() {
        double inputInt = scanner.nextDouble();
        scanner.nextLine();
        return inputInt;
    }
}
