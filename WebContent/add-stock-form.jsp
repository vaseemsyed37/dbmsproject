<!DOCTYPE html>
<html>

<head>
	<title>Add Stock</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-product-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Stock Registration</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Add Stock</h3>
		
		<form action="ProductControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="ADD_STOCK" />
			
			<table>
				<tbody>
					<tr>
						<td><label>Product Id:</label></td>
						<td><input type="text" name="prod" /></td>
					</tr>

					<tr>
						<td><label>Department Id:</label></td>
						<td><input type="text" name="dep" /></td>
					</tr>

					<tr>
						<td><label>Quantity:</label></td>
						<td><input type="text" name="quantity" /></td>
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











