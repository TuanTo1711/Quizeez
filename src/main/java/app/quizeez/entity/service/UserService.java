package app.quizeez.entity.service;

import app.quizeez.controller.Connector;
import app.quizeez.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService implements Service<User> {

    private final Connector connector = new Connector();

    public List<User> selectAll() {
        final String query = "select * from [USER]";
        return select(query);
    }

    public User find(String value) {
        return selectAll().stream()
                .filter(item
                        -> item.getUsername()
                        .equalsIgnoreCase(value))
                .findAny()
                .orElse(null);
    }

    public String generateCode() {
        DecimalFormat df = new DecimalFormat("0000");
        Random ran = new Random();
        return df.format(ran.nextInt(10000));
    }
    
    @Override
    public List<User> select(String query, Object... args) {
        List<User> list = new ArrayList<>();
        try {
            ResultSet rs = connector.executeQuery(query, args);
            while (rs.next()) {
                User model = readFromResultSet(rs);
                list.add(model);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return list;
    }

    private User readFromResultSet(ResultSet rs) throws SQLException {
        User modal = new User();

        modal.setID(rs.getInt(1));
        modal.setUsername(rs.getNString(2));
        modal.setPassword(rs.getNString(3));
        modal.setEmail(rs.getNString(4));
        modal.setFullname(rs.getNString(5));
        modal.setStatus(rs.getString(6));

        return modal;
    }

    @Override
    public void insert(User modal) {
        final String insertQuery = "INSERT INTO [dbo].[USER]"
                + "VALUES(?,?,?,?,?)";

        try {
            connector.executeUpdate(insertQuery, modal.toInsertData());
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(User modal) {
        final String query = "DELETE FROM [dbo].[USER] where ID = ?, [USERNAME] = ?";
        try {

            connector.executeUpdate(query, modal.toDeleteData());
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(User modal) {
        final String query = "update [USER] "
                + "set [USERNAME] = ?, [_PASSWORD] = ?, [EMAIL] = ?, [FULLNAME] = ?, [GENDER] = ?"
                + "where ID = ?";
        try {

            connector.executeUpdate(query, modal.toUpdateData());
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
