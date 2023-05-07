package com.inventory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.inventory.dto.Product;
import com.inventory.dto.Stock;

public class ProductDAO {
	private DataSource dataSource;

	public ProductDAO(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public List<Product> getProducts() throws Exception {
		List<Product> productList = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// For atomicity
			myConn.setAutoCommit(false);

			// For isolation
			myConn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// create sql statement
			String sql = "select * from product";

			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				String prod = myRs.getString("prod");
				String prodName = myRs.getString("pname");
				Integer price = myRs.getInt("price");

				// create new product object
				Product product = new Product(prod, prodName, price);

				// add it to the list of products
				productList.add(product);
			}

			return productList;
		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			myConn.rollback();
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
		return productList;
	}

	public void deleteProduct(String productId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get connection to database
			myConn = dataSource.getConnection();

			// For atomicity
			myConn.setAutoCommit(false);

			// For isolation
			myConn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// create sql to delete stock (child table)
			String sql = "delete from stock where prod=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			// set params
			myStmt.setString(1, productId);

			// execute sql statement
			myStmt.execute();

			// create sql to delete product (parent table)
			sql = "delete from product where prod=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			// set params
			myStmt.setString(1, productId);

			// execute sql statement
			myStmt.execute();

			// Commit the transaction For atomocity, consistency, durability
			myConn.commit();

		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			myConn.rollback();
		} finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}
	}

	public void updateProduct(Product theProduct) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();

			// For atomicity
			myConn.setAutoCommit(false);

			// For isolation
			myConn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// create SQL update statement
			String sql = "update product " + "set pname=?, price=?" + " where prod=?";

			// prepare statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theProduct.getPname());
			myStmt.setInt(2, Integer.valueOf(theProduct.getPrice()));
			myStmt.setString(3, theProduct.getProd());

			// execute SQL statement
			myStmt.execute();

			// Commit the transaction For atomocity, consistency, durability
			myConn.commit();
		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			myConn.rollback();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void addProduct(Product theProduct) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// For atomicity
			myConn.setAutoCommit(false);
	
			// For isolation
			myConn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			// Maybe a table student1 exist, maybe not
			// create table student(id integer, name varchar(10), primary key(Id))
			// Either the 2 following inserts are executed, or none of them are. This is
			
			String sql = "select * from product where prod=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, theProduct.getProd());

			// execute query
			ResultSet resultSet = myStmt.executeQuery();
			// if product with the product Id not exists then only do the insertion - consistency, isolation.
			if (!resultSet.next()) {
				// create sql for Product insert (parent)
				sql = "insert into product " + "(prod, pname, price) " + "values (?, ?, ?)";

				myStmt = myConn.prepareStatement(sql);

				// set the param values for the product
				myStmt.setString(1, theProduct.getProd());
				myStmt.setString(2, theProduct.getPname());
				myStmt.setInt(3, Integer.valueOf(theProduct.getPrice()));

				// execute sql insert
				myStmt.execute();

				// create sql for Stock insert (child)
				sql = "insert into stock " + "(prod, dep, quantity) " + "values (?, ?, ?)";
				myStmt = myConn.prepareStatement(sql);

				if (null != theProduct.getStockList()) {
					for (Stock stock : theProduct.getStockList()) {
						// set the param values for the product
						myStmt.setString(1, stock.getProd());
						myStmt.setString(2, stock.getDep());
						myStmt.setInt(3, Integer.valueOf(stock.getQuantity()));
						// execute sql insert
						myStmt.execute();
					}
				}
				
				if (null != theProduct.getStock()) {
					// set the param values for the product
					myStmt.setString(1, theProduct.getStock().getProd());
					myStmt.setString(2, theProduct.getStock().getDep());
					myStmt.setInt(3, Integer.valueOf(theProduct.getStock().getQuantity()));
					// execute sql insert
					myStmt.execute();
				}
			} else {
				// do the product updation
				
				// create SQL update statement
				sql = "update product " + "set pname=?, price=?" + " where prod=?";

				// prepare statement
				myStmt = myConn.prepareStatement(sql);

				// set params
				myStmt.setString(1, theProduct.getPname());
				myStmt.setInt(2, Integer.valueOf(theProduct.getPrice()));
				myStmt.setString(3, theProduct.getProd());

				// execute SQL statement
				myStmt.execute();
			}
			
			// Commit the transaction For atomocity, consistency, durability
			myConn.commit();
			
		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			myConn.rollback();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}

	}
	

	public Product getProduct(String theProductId) throws Exception {
		Product theProduct = null;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {

			// get connection to database
			myConn = dataSource.getConnection();
			
			// For atomicity
			myConn.setAutoCommit(false);

			// For isolation
			myConn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// create sql to get selected product
			String sql = "select * from product where prod=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theProductId);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			if (myRs.next()) {
				String prod = myRs.getString("prod");
				String pName = myRs.getString("pname");
				Integer price = Integer.valueOf(myRs.getString("price"));

				// use the productId during construction
				theProduct = new Product(prod, pName, price);
			} else {
				throw new Exception("Could not find product id: " + theProductId);
			}
			
			sql = "select * from stock where prod=?";

			// create prepared statement
			myStmt = myConn.prepareStatement(sql);

			// set params
			myStmt.setString(1, theProductId);

			// execute statement
			myRs = myStmt.executeQuery();

			// retrieve data from result set row
			List<Stock> stockList = new ArrayList<>();
			theProduct.setStockList(stockList);
			if (myRs.next()) {
				String prod = myRs.getString("prod");
				String dep = myRs.getString("dep");
				Integer price = Integer.valueOf(myRs.getString("quantity"));

				// use the productId during construction
				Stock stock = new Stock(prod, dep, price);
				stockList.add(stock);
			}
			return theProduct;
		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			throw e;
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}
	
	public void addStock(Stock theStock) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// For atomicity
			myConn.setAutoCommit(false);
	
			// For isolation
			myConn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// create sql for Stock insert (child)
			String sql = "insert into stock " + "(prod, dep, quantity) " + "values (?, ?, ?)";

			myStmt = myConn.prepareStatement(sql);

			// set the param values for the product
			myStmt.setString(1, theStock.getProd());
			myStmt.setString(2, theStock.getDep());
			myStmt.setInt(3, theStock.getQuantity());

			// execute sql insert
			myStmt.execute();

			// Commit the transaction For atomocity, consistency, durability
			myConn.commit();
		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			myConn.rollback();
		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
		
	}
	
	public List<Stock> getStocks(String productId) throws Exception {
		List<Stock> stockList = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();

			// For atomicity
			myConn.setAutoCommit(false);

			// For isolation
			myConn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			// create sql statement
			String sql = "select * from stock where prod=?";

			myStmt = myConn.prepareStatement(sql);

			// set the param values for the product
			myStmt.setString(1, productId);

			// execute query
			myRs = myStmt.executeQuery();

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				String prod = myRs.getString("prod");
				String depId = myRs.getString("dep");
				Integer quantity = myRs.getInt("quantity");

				// create new Stock object
				Stock stock = new Stock(prod, depId, quantity);

				// add it to the list of Stocks
				stockList.add(stock);
			}

			return stockList;
		} catch (SQLException e) {
			System.out.println("An exception was thrown");
			// For atomicity
			myConn.rollback();
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
		return stockList;
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	

	
}
