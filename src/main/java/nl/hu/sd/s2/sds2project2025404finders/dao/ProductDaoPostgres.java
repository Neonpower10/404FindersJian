package nl.hu.sd.s2.sds2project2025404finders.dao;

import nl.hu.sd.s2.sds2project2025404finders.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoPostgres implements ProductDAO{
    private Connection connection = null;

    public ProductDaoPostgres(Connection inConnection)
            throws SQLException {

        this.connection = inConnection;
    }
    @Override
    public boolean save(Product inProduct) throws SQLException {
        //TODO make save happen
        return false;
    }

    @Override
    public boolean update(Product inProduct) throws SQLException {
        //TODO make update happen
        return false;
    }

    @Override
    public boolean delete(String productName) throws SQLException {
        String q = "DELETE FROM product WHERE productname = ?";
        PreparedStatement ps = connection.prepareStatement(q);
        ps.setString(1, productName);
        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Product with name: " + productName + " has been deleted");
            return true;
        } else {
            System.out.println("Product with id: " + productName + " can't be found");
            return false;
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String q = "SELECT * FROM product";
        List<Product> products = new ArrayList<>();
        PreparedStatement pst = connection.prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                product.setProductName(rs.getString("productname"));
                product.setPricePerDay(rs.getBigDecimal("priceperday"));
                product.setProductDescription(rs.getString("productdescription"));
                product.setProductStock(rs.getInt("productstock"));
                product.setProductImageUrl(rs.getString("productimageurl"));

                products.add(product);
            }
        return products;
    }

    @Override
    public Product findByName(String productName) throws SQLException {
        String q = "SELECT * FROM product WHERE productname = ?";
        PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, productName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Product product = new Product();

                product.setProductName(rs.getString("productname"));
                product.setPricePerDay(rs.getBigDecimal("priceperday"));
                product.setProductDescription(rs.getString("productdescription"));
                product.setProductStock(rs.getInt("productstock"));
                product.setProductImageUrl(rs.getString("productimageurl"));

                return product;
            }
        return null;
    }
}
