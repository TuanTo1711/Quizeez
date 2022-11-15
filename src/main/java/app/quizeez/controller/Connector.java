package app.quizeez.controller;

import app.quizeez.util.ConnectionUtils;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Connector {

    public final Connection connection
            = new ConnectionUtils("sa", "123456", "QUIZEEZ")
                    .openConnection();

    public PreparedStatement prepareStatement(String query, Object... args)
            throws SQLException {
        PreparedStatement pstmt;
        if (query.trim().startsWith("{")) {
            pstmt = connection.prepareCall(query);
        } else {
            pstmt = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public void executeUpdate(String query, Object... args) throws SQLException {
        PreparedStatement stmt = prepareStatement(query, args);
        stmt.executeUpdate();
    }

    public ResultSet executeQuery(String query, Object... args) {
        try {
            PreparedStatement stmt = prepareStatement(query, args);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }
}
