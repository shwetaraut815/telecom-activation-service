package com.telecom.app;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AssignPlanServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String simNumber = req.getParameter("simNumber");
        String plan = req.getParameter("plan");

        resp.setContentType("application/json");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE sim_activation SET plan=? WHERE sim_number=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, plan);
            ps.setString(2, simNumber);
            ps.executeUpdate();

            resp.getWriter().println(
                "{ \"status\":\"SUCCESS\", \"simNumber\":\"" + simNumber +
                "\", \"assignedPlan\":\"" + plan + "\" }"
            );

        } catch (Exception e) {
            resp.getWriter().println("{ \"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\" }");
        }
    }
}
