package hu.dbobo.model.changeevent;

import java.sql.*;

public class ChangeEventManager {
    public static final String URL = "jdbc:derby:VALUTA;create=true";
    public static final String DRIVER_CLASS_NAME = "org.apache.derby.jdbc.JDBC";

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
        return DriverManager.getConnection(URL);
    }

    public void insertEvent(ChangeEvent changeEvent) {
        String sql = """
                INSERT INTO change_event (change_id, valuta_from, valuta_to, amount, change_date)
                VALUES (?,?,?,?,?)
                """;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, changeEvent.getChange_id());
            preparedStatement.setString(2, changeEvent.getValuta_from().toString());
            preparedStatement.setString(3, changeEvent.getValuta_to().toString());
            preparedStatement.setDouble(4, changeEvent.getAmount());
            preparedStatement.setDate(5, Date.valueOf(changeEvent.getChange_date()));

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
