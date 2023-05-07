<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Stock List</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>Stock List</h2>
		</div>
	</div>

	<div id="container">
	
		<div id="content">
		
			<!-- put new button: Add Product -->
			
			<!-- <input type="button" value="Add Product" 
				   onclick="window.location.href='add-product-form.jsp'; return false;"
				   class="add-product-button"
			/> -->
			
			<table>
			
				<tr>
					<th>Product Id</th>
					<th>Department Id</th>
					<th>Quantity</th>
					<!-- <th>Action</th> -->
				</tr>
				
				<c:forEach var="tempStock" items="${STOCK_LIST}">
					
					<tr>
						<td> ${tempStock.prod} </td>
						<td> ${tempStock.dep} </td>
						<td> ${tempStock.quantity} </td>
						<%-- <td> 
							<a href="${tempLink}">Update</a> 
							 | 
							<a href="${deleteLink}"
							onclick="if (!(confirm('Are you sure you want to delete this product?'))) return false">
							Delete</a>	
							 |
							 <a href="" onclick="window.location.href='add-stock-form.jsp'; return false;">Add Stock</a> 
						</td> --%>
					</tr>
				
				</c:forEach>
				
			</table>
			
			<div style="clear: both;"></div>
		
			<p>
				<a href="ProductControllerServlet">Back to List</a>
			</p>
		
		</div>
	
	</div>
</body>


</html>








