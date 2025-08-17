<%-- 
    Document   : index
    Created on : 15 Aug, 2025, 10:48:26 PM
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Banking Application</title>
    <%@ include file="header.jsp" %>

     <link rel="stylesheet" href="css/style.css">
    <script>
        function showFields() {
            const action = document.getElementById("action").value;
            document.getElementById("nameField").style.display = action === "create" ? "block" : "none";
            document.getElementById("amountField").style.display = (action === "create" || action === "deposit" || action === "withdraw") ? "block" : "none";
        }
    </script>
</head>
<body>
    <h2>Banking Application</h2>
    <form action="account" method="post">
        <label>Action:</label>
        <select name="action" id="action" onchange="showFields()">
            <option value="create">Create Account</option>
            <option value="deposit">Deposit</option>
            <option value="withdraw">Withdraw</option>
            <option value="balance">Check Balance</option>
        </select><br><br>

        <div>
            <label>Account No:</label>
            <input type="text" name="accountNo" required>
        </div><br>

        <div id="nameField">
            <label>Name:</label>
            <input type="text" name="name">
        </div><br>

        <div id="amountField">
            <label>Amount:</label>
            <input type="number" step="0.01" name="amount">
        </div><br>
 

        <input type="submit" value="Submit">
    </form>
</body>
</html>
