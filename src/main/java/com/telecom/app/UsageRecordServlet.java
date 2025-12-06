package com.telecom.app;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UsageRecordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String simNumber = req.getParameter("simNumber");
        String dataUsed = req.getParameter("dataUsed");
        String callMinutes = req.getParameter("callMinutes");

        resp.setContentType("application/json");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE sim_activation SET data_used=?, call_minutes=? WHERE sim_number=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, dataUsed);
            ps.setString(2, callMinutes);
            ps.setString(3, simNumber);
            ps.executeUpdate();

            resp.getWriter().println(
                "{ \"status\":\"RECORDED\", \"simNumber\":\"" + simNumber +
                "\", \"dataUsed\":\"" + dataUsed +
                "\", \"callMinutes\":\"" + callMinutes + "\" }"
            );

        } catch (Exception e) {
            resp.getWriter().println("{ \"status\":\"ERROR\", \"message\":\"" + e.getMessage() + "\" }");
        }
    }
}
