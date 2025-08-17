package com.example.banking;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String accountNo = request.getParameter("accountNo");
        String amountStr = request.getParameter("amount");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bankdb", "root", ""
            );

            if ("create".equals(action)) {
                double initialAmount = Double.parseDouble(amountStr);
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO accounts (account_no, name, balance) VALUES (?, ?, ?)"
                );
                ps.setString(1, accountNo);
                ps.setString(2, name);
                ps.setDouble(3, initialAmount);
                ps.executeUpdate();
                out.println("<h3>Account created successfully with balance: " + initialAmount + "</h3>");
            } 
            else if ("deposit".equals(action)) {
                double amount = Double.parseDouble(amountStr);
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE accounts SET balance = balance + ? WHERE account_no = ?"
                );
                ps.setDouble(1, amount);
                ps.setString(2, accountNo);
                ps.executeUpdate();
                out.println("<h3>Amount deposited successfully!</h3>");
            } 
            else if ("withdraw".equals(action)) {
                double amount = Double.parseDouble(amountStr);
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE accounts SET balance = balance - ? WHERE account_no = ? AND balance >= ?"
                );
                ps.setDouble(1, amount);
                ps.setString(2, accountNo);
                ps.setDouble(3, amount);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    out.println("<h3>Amount withdrawn successfully!</h3>");
                } else {
                    out.println("<h3>Insufficient balance or account not found!</h3>");
                }
            } 
            else if ("balance".equals(action)) {
                PreparedStatement ps = con.prepareStatement(
                    "SELECT balance FROM accounts WHERE account_no = ?"
                );
                ps.setString(1, accountNo);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    out.println("<h3>Balance: " + rs.getDouble(1) + "</h3>");
                } else {
                    out.println("<h3>Account not found!</h3>");
                }
            }
            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
