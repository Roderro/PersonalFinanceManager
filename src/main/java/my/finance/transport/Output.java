package my.finance.transport;

public interface Output {

    void print(String str);

    void println(String str);

    void error(String str);

    void printf(String format, Object... args);

}
