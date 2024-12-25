import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBWarden {

    public static void main(String[] args) throws SQLException {
        org.h2.tools.Server.createWebServer().start();;
    }
}
