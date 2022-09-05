package hu.dbobo.model.valuta;

import hu.dbobo.config.DerbyConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValutaManager {

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DerbyConfig.DRIVER_CLASS_NAME);
        return DriverManager.getConnection(DerbyConfig.URL);
    }

    public List<Valuta> findAll() {
        String sql = """
                SELECT
                    v.valuta_id,
                    v.valuta_name,
                    e.chf,
                    e.eur,
                    e.usd,
                    e.huf
                FROM valuta v
                JOIN exchange_rate e
                ON v.valuta_id = e.valuta_id
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<Valuta> valutaList = new ArrayList<>();

            while (resultSet.next()) {
                Valuta valuta = new Valuta(
                        ValutaId.valueOf(resultSet.getString("valuta_id")),
                        resultSet.getString("valuta_name"),
                        resultSet.getDouble("chf"),
                        resultSet.getDouble("eur"),
                        resultSet.getDouble("usd"),
                        resultSet.getDouble("huf")
                );
                valutaList.add(valuta);
            }

            return valutaList;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
