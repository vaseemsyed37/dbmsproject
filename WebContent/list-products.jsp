<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Product Inventory App</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Product List</h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add Product -->
			
			<input type="button" value="Add Product" 
				   onclick="window.location.href='add-product-form.jsp'; return false;"
				   class="add-product-button"
			/>
			
			<table>
			
				<tr>
					<th>Product Id</th>
					<th>Product Name</th>
					<th>Price</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="tempProduct" items="${PRODUCT_LIST}">
					
					<!-- set up a link for each product -->
					<c:url var="tempLink" value="ProductControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="prod" value="${tempProduct.prod}" />
					</c:url>

					<!--  set up a link to delete a product -->
					<c:url var="deleteLink" value="ProductControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="prod" value="${tempProduct.prod}" />
					</c:url>
					
					<!--  set up a link to view stocks-->
					<c:url var="viewStockLink" value="ProductControllerServlet">
						<c:param name="command" value="VIEW_STOCK" />
						<c:param name="prod" value="${tempProduct.prod}" />
					</c:url>
															
					<tr>
						<td> ${tempProduct.prod} </td>
						<td> ${tempProduct.pname} </td>
						<td> ${tempProduct.price} </td>
						<td> 
							<a href="${tempLink}">Update</a> 
							 | 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this product?'))) return false">
							Delete</a>	
							 |
							 <a href="" onclick="window.location.href='add-stock-form.jsp'; return false;">Add Stock</a>
							 |
							 <a href="${viewStockLink}">View Stock</a>  
						</td>
					</tr>
				
				</c:forEach>
				
			</table>
		
		</div>
	
	</div>
</body>


</html>








