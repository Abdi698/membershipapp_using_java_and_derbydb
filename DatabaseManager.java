
package membershipapp;

import java.sql.*;

public class DatabaseManager {
    public static Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection("jdbc:derby://localhost:1527/MembershipDB", "app", "app");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void insertMember(String fn, String ln, String email, String phone, String add1, String add2,
                                    String country, String gender, String membership, String sports, String reason, boolean accepted) {
        try (Connection conn = getConnection()) {
            if (conn == null) return;
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO members (first_name, last_name, email, phone, address1, address2, country, gender, membership_type, sports, reason, terms_accepted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, fn);
            ps.setString(2, ln);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, add1);
            ps.setString(6, add2);
            ps.setString(7, country);
            ps.setString(8, gender);
            ps.setString(9, membership);
            ps.setString(10, sports);
            ps.setString(11, reason);
            ps.setBoolean(12, accepted);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
