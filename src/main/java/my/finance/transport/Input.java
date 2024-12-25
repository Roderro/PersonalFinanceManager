package my.finance.transport;

import java.util.Optional;
import java.util.Scanner;

public interface Input extends AutoCloseable {

    String next();

    String nextLine();

    int nextInt();

    double nextDouble();
}
