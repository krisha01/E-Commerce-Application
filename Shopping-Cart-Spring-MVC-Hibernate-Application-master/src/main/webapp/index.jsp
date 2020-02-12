
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Advert Application Lab8</title>
</head>
<body>

	<center><h1>Welcome to our Online Store</h1>
	<h5>Feel free to look around</h5><br></center>
	<h3>Please login to continue ahead or if you are a new user kindly Sign Up by pressing the button below</h3>
	<a href="user/register.htm"><input type="submit" value="Sign Up"></a><br/><br><br>

	<h2>Login</h2>
	<form action="user/login" method="post">
	
		<table>
		
		<div>
    		<label for="name">User Name:</label>
		    <td><input name="username" size="30" required="required" /></td>
		</div>
  		<div>
    		<label for="password">Password :</label>		    
    		<td><input type="password" name="password" size="30" required="required"/></td>
		  </div>
		<div style="text-align: center;">
		    <td colspan="2"><input type="submit" value="Login" /></td>
		</div>
				
		</table>

	</form>


</body>
</html>

