package my.finance.transport;



public interface Input extends AutoCloseable {

    String next();

    String nextLine();

    int nextInt();

    double nextDouble();
}
