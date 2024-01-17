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
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RealizarCompra extends HttpServlet {

    ConexionDB conexionDB = new ConexionDB();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        HashMap<Byte, Integer> carrito = (HashMap<Byte, Integer>) sesion.getAttribute("Carrito");
        Integer userID = (Integer) sesion.getAttribute("User.ID");
        String pedido = "";
        double total = 0;
        String result = "";

        try {
            con = conexionDB.getConnection();
            for (byte i : carrito.keySet()) {
                // Obtener información del pastel del carrito
                String query = "SELECT * FROM pasteles WHERE Pastel_ID = ?";
                ps = con.prepareStatement(query);
                ps.setInt(1, i);
                rs = ps.executeQuery();

                if (rs.next()) {
                    // Verificar si hay suficiente stock
                    int stockActual = rs.getInt("Stock_Pastel");
                    int cantidadComprada = carrito.get(i);

                    if (stockActual >= cantidadComprada) {
                        // Actualizar el stock del pastel
                        String updateStockQuery = "UPDATE pasteles SET Stock_Pastel = ? WHERE Pastel_ID = ?";
                        ps = con.prepareStatement(updateStockQuery);
                        ps.setInt(1, stockActual - cantidadComprada);
                        ps.setInt(2, i);
                        ps.executeUpdate();

                        // Actualizar la información del pedido
                        pedido += String.valueOf(i) + "," + cantidadComprada + "_";
                        total += cantidadComprada * rs.getDouble("Costo_u_Pastel");
                    } else {
                        result = "No hay suficiente stock para el pastel con ID " + i;
                    }
                }
            }

            // Si el pedido se procesó correctamente, guardar en historial_compras
            if (result.isEmpty()) {
                String insertCompraQuery = "INSERT INTO historial_compras (User_ID, Fecha_Compra, Compra_Productos, Compra_Total) VALUES (?, NOW(), ?, ?)";
                ps = con.prepareStatement(insertCompraQuery);
                ps.setInt(1, userID);
                ps.setString(2, pedido);
                ps.setDouble(3, total);
                ps.executeUpdate();

                // También puedes guardar en la tabla pedidos si lo deseas
                String insertPedidoQuery = "INSERT INTO pedidos (User_ID, Fecha_Pedido, Pedido_Productos, Pedido_Total) VALUES (?, NOW(), ?, ?)";
                ps = con.prepareStatement(insertPedidoQuery);
                ps.setInt(1, userID);
                ps.setString(2, pedido);
                ps.setDouble(3, total);
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            result = ex.getMessage();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Compra</title>");
            // Agregar enlaces a los estilos de Bootstrap (asegúrate de tener acceso a ellos)
            out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class=\"container mt-5\">");

            if (result.isEmpty()) {
                sesion.setAttribute("Carrito", new HashMap<>()); // Limpiar el carrito después de la compra
                // Mensaje de éxito
                out.println("<div class=\"alert alert-success\" role=\"alert\">Compra realizada con éxito.</div>");
                // Redirección a la página de inicio
                out.println("<script>setTimeout(function(){window.location.href='http://localhost:8081/Pasteleria/Inicio'}, 2000);</script>");
            } else {
                // Mensaje de error
                out.println("<div class=\"alert alert-danger\" role=\"alert\">Error al realizar la compra: " + result + "</div>");
            }

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
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
        return "Realizar Compra";
    }
}
