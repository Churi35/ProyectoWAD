/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import conexion.ConexionDB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HistorialCompras extends HttpServlet {

    ConexionDB conexionDB = new ConexionDB();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("User.ID");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Historial de Compras</title>");
            // Agregar enlaces a los estilos de Bootstrap (asegúrate de tener acceso a ellos)
            out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">");
            out.println("</head>");
            out.println("<body class=\"container\">");

            if (userID != null) {
                out.println("<h1 class=\"mt-4\">Historial de Compras para el Usuario " + userID + "</h1>");
                mostrarHistorialCompras(out, userID);
                out.println("<a class=\"btn btn-primary mt-4\" href=\"http://localhost:8081/Pasteleria/Inicio\">Retroceder</a>");
            } else {
                out.println("<p class=\"mt-4\">Debes iniciar sesión para ver el historial de compras.</p>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    private void mostrarHistorialCompras(PrintWriter out, Integer userID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = conexionDB.getConnection();
            String query = "SELECT * FROM historial_compras WHERE User_ID = ? ORDER BY Fecha_Compra DESC";
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            // Utilizar clases de Bootstrap para la tabla
            out.println("<table class=\"table table-bordered mt-4\">");
            out.println("<thead class=\"thead-dark\"><tr><th>Fecha de Compra</th><th>Productos</th><th>Total</th></tr></thead>");
            out.println("<tbody>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("Fecha_Compra") + "</td>");
                out.println("<td>" + rs.getString("Compra_Productos") + "</td>");
                out.println("<td>" + rs.getDouble("Compra_Total") + "</td>");
                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");

        } catch (SQLException ex) {
            out.println("<p>Error al recuperar el historial de compras: " + ex.getMessage() + "</p>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Historial de Compras";
    }
}