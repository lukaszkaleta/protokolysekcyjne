import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by lka on 25.11.15.
 */
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = "1";
        try
        {
            InitialContext initialContext = new InitialContext();
            response += "2";
            Context environmentContext = (Context) initialContext.lookup("java:/comp/env");
            response += "3";
            DataSource dataSource = (DataSource) environmentContext.lookup("jdbc/dissection-protocol-DS");
            response += "4";
            try {
                Connection connection = dataSource.getConnection();
                response += "5";
                response += connection.isClosed();
                connection.commit();
                response += "6";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch(NamingException error)
        {
            response += "e" + error.getMessage();
            System.out.println("Error With JNDI Lookup - " + error.getMessage());
            error.printStackTrace();
        }
        resp.getWriter().write(response);
    }
}
