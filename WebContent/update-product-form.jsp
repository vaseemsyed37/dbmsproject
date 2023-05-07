<!DOCTYPE html>
<html>

<head>
	<title>Update Product</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-product-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Product Registration</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update Product</h3>
		
		<form action="ProductControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />

			<input type="hidden" name="prod" value="${THE_PRODUCT.prod}" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Product Id:</label></td>
						<td><input type="text" name="prod" 
								   value="${THE_PRODUCT.prod}" /></td>
					</tr>

					<tr>
						<td><label>Product Name:</label></td>
						<td><input type="text" name="pname" 
								   value="${THE_PRODUCT.pname}" /></td>
					</tr>

					<tr>
						<td><label>Price:</label></td>
						<td><input type="text" name="price" 
								   value="${THE_PRODUCT.price}" /></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="ProductControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>











