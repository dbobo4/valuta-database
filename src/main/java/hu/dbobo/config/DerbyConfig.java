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

        insertValuta("CHF", "Swiss Franc");
        insertValuta("EUR", "Euro");
        insertValuta("USD", "United States Dollar");
        insertValuta("HUF", "Hungarian Forint");

        insertExchangeRate("CHF", 1, 1.03, 1.02, 414.88);
        insertExchangeRate("EUR", 0.97, 1, 0.99, 404.13);
        insertExchangeRate("USD", 0.98, 1.01, 1, 406.86);
        insertExchangeRate("HUF", 0.0024, 0.0025, 0.0025, 1);

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

    private static void insertExchangeRate(String valutaId, double rate1, double rate2, double rate3, double rate4) {
        String sql = """
                INSERT INTO exchange_rate (valuta_id,chf,eur,usd,huf)
                VALUES (?,?,?,?,?)
                """;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, valutaId);
            preparedStatement.setDouble(2, rate1);
            preparedStatement.setDouble(3, rate2);
            preparedStatement.setDouble(4, rate3);
            preparedStatement.setDouble(5, rate4);

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














