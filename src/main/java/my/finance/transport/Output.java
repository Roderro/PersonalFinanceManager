package my.finance.transport;

import java.io.PrintStream;

public interface Output {

    public static void main(String[] args) {
        Output out = new StandardOutput();
        out.println();
    }

    void print(String str);

    default void println(){
        println("");
    }
    void println(String str);

    void error(String str);

    void printf(String format, Object... args);

}
