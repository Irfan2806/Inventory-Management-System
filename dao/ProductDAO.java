package dao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.DBConnection;
import java.sql.*;

public class ProductDAO {

    public void addProduct(String name, String category, double price, int quantity) {
        String sql = "INSERT INTO products(name, category, price, quantity) VALUES(?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);

            ps.executeUpdate();
            System.out.println("Product added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewProducts() {
        String sql = "SELECT * FROM products";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\nID | Name | Category | Price | Quantity");
            System.out.println("-------------------------------------------");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + "  |  " +
                        rs.getString("name") + "  |  " +
                        rs.getString("category") + "  |  " +
                        rs.getDouble("price") + "  |  " +
                        rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStock(int id, int quantity) {
        String sql = "UPDATE products SET quantity = quantity + ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Stock updated");
            else
                System.out.println("Product not found");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Product deleted");
            else
                System.out.println("Product not found");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public void lowStockAlert() throws Exception {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM products WHERE quantity < 5");

            ResultSet rs = ps.executeQuery();

            System.out.println("Low Stock Products:");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        }
    
}