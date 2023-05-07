package com.inventory.servlet;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.inventory.dao.ProductDAO;
import com.inventory.dto.Product;
import com.inventory.dto.Stock;

/**
 * Servlet implementation class ProductControllerServlet
 */
@WebServlet("/ProductControllerServlet")
public class ProductControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductDAO productDAO;

	@Resource(name = "jdbc/inventory")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		// create our product db util ... and pass in the conn pool / datasource
		try {
			productDAO = new ProductDAO(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// read the "command" parameter
			String theCommand = request.getParameter("command");

			// if the command is missing, then default to listing products
			if (theCommand == null) {
				theCommand = "LIST";
			}

			// route to the appropriate method
			switch (theCommand) {
			case "LIST":
				listProducts(request, response);
				break;
				
			case "ADD_STOCK":
				addStock(request, response);
				break;
				
			case "VIEW_STOCK":
				listStocks(request, response);
				break;

			case "ADD":
				addProduct(request, response);
				break;

			case "LOAD":
				loadProduct(request, response);
				break;

			case "UPDATE":
				updateProduct(request, response);
				break;

			case "DELETE":
				deleteProduct(request, response);
				break;

			default:
				listProducts(request, response);
			}
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	private void listStocks(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productId = request.getParameter("prod");
		// get products from db
		List<Stock> stockList = productDAO.getStocks(productId);

		// add products to the request
		request.setAttribute("STOCK_LIST", stockList);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-stocks.jsp");
		dispatcher.forward(request, response);
		
	}

	private void addStock(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String productId = request.getParameter("prod");
		String departmentId = request.getParameter("dep");
		Integer quantity = Integer.valueOf(request.getParameter("quantity"));
		
		// create a new Product object
		Stock theStock = new Stock(productId, departmentId, quantity);
		
		// add the product to the database
		productDAO.addStock(theStock);
				
		// send back to main page (the product list)
		listProducts(request, response);
		
	}

	private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1 The product p1 is deleted from Product and Stock.
		// read product Id from form data
		String theProductId = request.getParameter("prod");

		// delete Product and Stock from database
		productDAO.deleteProduct(theProductId);

		// send them back to "list Products" page
		listProducts(request, response);
	}

	private void loadProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read product id from form data
		String theProductId = request.getParameter("prod");
		
		// get product from database (db util)
		Product theProduct = productDAO.getProduct(theProductId);
		
		// place product in the request attribute
		request.setAttribute("THE_PRODUCT", theProduct);
		
		// send to jsp page: update-product-form.jsp
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-product-form.jsp");
		dispatcher.forward(request, response);	
	}

	private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// We add a product (p100, cd, 5) in Product and (p100, d2, 50) in Stock.
		
		// read product info from form data
		String productId = request.getParameter("prod");
		String productName = request.getParameter("pname");
		Integer price = Integer.valueOf(request.getParameter("price"));
		
		// create a new Product object
		Product theProduct = new Product(productId, productName, price);
		
		if (null != request.getParameter("stock.prod") && !request.getParameter("stock.prod").isEmpty()) {
			String stockProdId = request.getParameter("stock.prod");
			String depId = request.getParameter("stock.dep");
			Integer quantity = Integer.valueOf(request.getParameter("stock.quantity"));
			
			// create a new Stock object
			Stock stock = new Stock(stockProdId, depId, quantity);
			theProduct.setStock(stock);
		}
		
		// add the products to the database
		productDAO.addProduct(theProduct);
				
		// send back to main page (the product list)
		listProducts(request, response);

	}

	private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// The product p1 changes its name to pp1 in Product and Stock.
		// read product info from form data
		String productId = request.getParameter("prod");
		String productName = request.getParameter("pname");
		Integer price = Integer.parseInt(request.getParameter("price"));

		// create a new Product object
		Product theProduct = new Product(productId, productName, price);

		// perform update on database
		productDAO.updateProduct(theProduct);

		// send them back to the "list products" page
		listProducts(request, response);
	}

	private void listProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get products from db
		List<Product> productList = productDAO.getProducts();

		// add products to the request
		request.setAttribute("PRODUCT_LIST", productList);

		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-products.jsp");
		dispatcher.forward(request, response);
	}

}
