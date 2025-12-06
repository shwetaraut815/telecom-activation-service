package com.telecom.app;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActivateSimServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String customerId = req.getParameter("customerId");
        String simNumber = req.getParameter("simNumber");

        resp.setContentType("application/json");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO sim_activation (customer_id, sim_number) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, customerId);
            ps.setString(2, simNumber);
            ps.executeUpdate();

            resp.getWriter().println(
                "{ \"status\":\"SUCCESS\", \"message\":\"SIM activated\", " +
                "\"customerId\":\"" + customerId + "\", " +
                "\"simNumber\":\"" + simNumber + "\" }"
            );

        } catch (Exception e) {
            resp.getWriter().println("{ \"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\" }");
        }
    }
}
