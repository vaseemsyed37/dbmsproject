<!DOCTYPE html>
<html>

<head>
	<title>Add Product</title>

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
		<h3>Add Product</h3>
		
		<form action="ProductControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Product Id:</label></td>
						<td><input type="text" name="prod" /></td>
					</tr>

					<tr>
						<td><label>Product Name:</label></td>
						<td><input type="text" name="pname" /></td>
					</tr>

					<tr>
						<td><label>Price:</label></td>
						<td><input type="text" name="price" /></td>
					</tr>
					
					
					
				</tbody>
			</table>
			Add Stock
			<table>
				<tbody>
					<tr>
						<td><label>Product Id:</label></td>
						<td><input type="text" name="stock.prod" /></td>
					</tr>

					<tr>
						<td><label>Department Id:</label></td>
						<td><input type="text" name="stock.dep" /></td>
					</tr>

					<tr>
						<td><label>Quantity:</label></td>
						<td><input type="text" name="stock.quantity" /></td>
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











