package com.telecom.app;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BillingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String simNumber = req.getParameter("simNumber");

        int amount = 199; // fixed bill amount

        resp.setContentType("application/json");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE sim_activation SET bill_amount=? WHERE sim_number=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, amount);
            ps.setString(2, simNumber);
            ps.executeUpdate();

            resp.getWriter().println(
                "{ \"simNumber\":\"" + simNumber +
                "\", \"billAmount\":\"" + amount + "\" }"
            );

        } catch (Exception e) {
            resp.getWriter().println("{ \"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\" }");
        }
    }
}
