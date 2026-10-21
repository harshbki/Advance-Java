import java.sql.*;

public class JdbcDemo {

    public static void main(String[] args) throws Exception {

        // 1. Load MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. Connect to MySQL
        String url = "jdbc:mysql://localhost:3306/college";
        String user = "root";
        String password = "root";

        try (Connection con =
                 DriverManager.getConnection(url, user, password)) {

            System.out.println("Connected to MySQL!");

            // 3. Insert record
            String sql =
                "INSERT INTO student (id, name, branch) VALUES (?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, 101);
                ps.setString(2, "Rahul");
                ps.setString(3, "CSE");

                ps.executeUpdate();

                System.out.println("Record Inserted");
            }

            // 4. Read records
            String query = "SELECT * FROM student";

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(query)) {

                while (rs.next()) {
                    System.out.println(
                        rs.getInt("id") + " " +
                        rs.getString("name") + " " +
                        rs.getString("branch")
                    );
                }
            }
        }
    }
}