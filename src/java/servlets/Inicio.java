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
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Inicio extends HttpServlet {

    ConexionDB coneccion = new ConexionDB();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    private String header;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Inicio de sesión general
        HttpSession sesion = request.getSession();
        Integer ID = (Integer) sesion.getAttribute("User.ID");
        if (ID == null) {
            header = Constantes.getHeaderSin();
        } else {
            header = Constantes.getHeaderCon();
        }

        response.setContentType("text/html;charset=UTF-8");
        String query = "select * from pasteles";
        String result = "";
        try {
            byte contador = 0;
            con = coneccion.getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            result += "<tr>\n";
            while (rs.next()) {
                if (rs.getInt("Stock_Pastel") != 0) {
                    result += "<td>\n";
                    result += "<div class=\"card card-body\">\n"
                            + "                            <p class=\"text-center\">" + rs.getString("Titulo_Pastel")
                            + "</p>" + "                            <a href=\"/Pasteleria/Descripcionpastel?id="
                            + rs.getInt("Pastel_ID") + "\"><img src=\"." + rs.getString("Imagen_Pastel")
                            + "\"></a>\n" + "                            <div class=\"text-center\">\n"
                            + "                                <form action=\"/Pasteleria/AgregarCarrito\">\n"
                            + "                                    <input name=\"pastel\" type=\"hidden\" value=\""
                            + rs.getInt("Pastel_ID") + "\">\n" + "                                    <input name=\"origen\" type=\"hidden\" value=\"0\">\n"
                            + "                                    <p>Costo: $" + rs.getInt("Costo_u_Pastel") + "</p>\n"
                            + "                                    <input name=\"cantidad\" type=\"number\" class=\"form-control\" value=\"0\" min=\"0\" max=\""
                            + rs.getInt("Stock_Pastel") + "\">\n";
                    if (ID == null)
                        result += "<a href=\"/Pasteleria/Login\" class=\"btn btn-primary\">Agregar a Carrito</a>";
                    else
                        result += "                                    <button type=\"submit\" class=\"btn btn-primary\" name=\"Agreagar\">Agregar a Carrito</button> \n";
                    result += "                                </form>\n" + "                            </div>\n"
                            + "                        </div>\n" + "                       </td>\n";
                    contador++;
                }

                if (contador == 4)
                    result += "</tr>\n" + "<tr>";
            }
            result += "</tr>\n";
        } catch (Exception e) {
        }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use the following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Inicio</title>");
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">");
            out.println("<link href=\"./CSS/Iniciostyle.css\" rel=\"stylesheet\"");
            out.println("</head>");
            out.println("<body>");
            out.println(header);
            out.println("<br>");
            out.println("<div class=\"col-md-4\">\n" + "            <table class=\"table\">");

            // Mostrar el tiempo de sesión si el usuario está autenticado
            Long loginTime = (Long) sesion.getAttribute("loginTime");
            if (loginTime != null) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - loginTime;

                // Convertir milisegundos a minutos y segundos
                long minutes = (elapsedTime / (1000 * 60)) % 60;
                long seconds = (elapsedTime / 1000) % 60;

                out.println("<p>Tiempo conectado: " + minutes + " minutos y " + seconds + " segundos</p>");
            } else {
                out.println("<p>No has iniciado sesión.</p>");
            }

            out.println(result);
            out.println("</table>\n" + "            </div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        return "Short description";
    }
    // </editor-fold>

}
