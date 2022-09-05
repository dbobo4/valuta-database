package hu.dbobo.config;

import java.sql.*;

public class DerbyConfig {
    public static final String URL = "jdbc:derby:VALUTA;create=true";
    public static final String DRIVER_CLASS_NAME = "org.apache.derby.jdbc.JDBC";


    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(DRIVER_CLASS_NAME);
        return DriverManager.getConnection(URL);
    }

    public static void initDatabase() {
        String sqlValuta = """
                CREATE TABLE valuta (
                valuta_id VARCHAR(3) NOT NULL,
                valuta_name VARCHAR(255) NOT NULL,
                                
                CONSTRAINT valuta_pk PRIMARY KEY (valuta_id)
                )
                """;

        String sqlExchangeRate = """
                CREATE TABLE exchange_rate (
                valuta_id VARCHAR(3) NOT NULL,
                chf DOUBLE NOT NULL,
                eur DOUBLE NOT NUll,
                usd DOUBLE NOT NUll,
                huf DOUBLE NOT NUll,
                                
                CONSTRAINT exchange_rate_pk PRIMARY KEY (valuta_id),
                CONSTRAINT valuta_id_fk FOREIGN KEY (valuta_id) REFERENCES VALUTA (valuta_id)
                )
                """;

        String sqlChange = """
                CREATE TABLE change_event (
                change_id INTEGER NOT NULL,
                valuta_from VARCHAR(3) NOT NULL,
                valuta_to VARCHAR(3) NOT NULL,
                amount DOUBLE NOT NULL,
                change_date DATE NOT NULL,
                                
                CONSTRAINT change_pk PRIMARY KEY (change_id),
                CONSTRAINT from_fk FOREIGN KEY (valuta_from) REFERENCES VALUTA (valuta_id),
                CONSTRAINT to_fk FOREIGN KEY (valuta_to) REFERENCES VALUTA (valuta_id)
                )
                """;

        String[] createTableSqls = {sqlValuta, sqlExchangeRate, sqlChange};
        String[] createTableNames = {"valuta", "exchange_rate", "change_event"};

        for (int i = 0; i < createTableNames.length; i++) {
            createTable(createTableSqls[i], createTableNames[i]);
        }


    }

    private static void insertValuta(String valutaId, String valutaName) {
        String sql = """
                INSERT INTO VALUTA (valuta_id, valuta_name) VALUES (?,?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, valutaId);
            preparedStatement.setString(2, valutaName);

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(String sql, String tablaNev) {
        if (!isTablePresent(tablaNev)) {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {
                statement.execute(sql);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isTablePresent(String tablaNev) {
        try (Connection connection = getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();

            try (ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"})) {
                while (resultSet.next()) {
                    String tableName = resultSet.getString("TABLE_NAME");
                    if (tableName.equalsIgnoreCase(tablaNev)) {
                        return true;
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}














