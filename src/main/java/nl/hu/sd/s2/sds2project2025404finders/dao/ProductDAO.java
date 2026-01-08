package nl.hu.sd.s2.sds2project2025404finders.dao;

import nl.hu.sd.s2.sds2project2025404finders.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product inProduct) throws SQLException;
    boolean update(Product inProduct) throws SQLException;
    boolean delete(String productName) throws SQLException;
    List<Product> findAll() throws SQLException;
    Product findByName(String productName) throws SQLException;
}
