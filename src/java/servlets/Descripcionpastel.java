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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Uriel Perez Cubias
 */
public class Descripcionpastel extends HttpServlet {
    
    ConexionDB coneccion = new ConexionDB();
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    private String header;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Integer ID = (Integer) sesion.getAttribute("User.ID");
        if(ID==null)
            header = Constantes.getHeaderSin();
        else
            header = Constantes.getHeaderCon();
        byte pastel = Byte.parseByte(request.getParameter("id"));
        String query = "select * from pasteles where `Pastel_ID`= "+pastel;
        String result = "";
        
        try
        {
            con=coneccion.getConnection();
            ps=con.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                result += "<div class=\"container p-4\">\n" +
    "            <div class=\"row\">\n" +
    "                <div class=\"col-md-4\">\n" +
    "                    <div class=\"card card-body\">\n" +
    "                        <img src=\"."+rs.getString("Imagen_Pastel")+"\">" +
    "                    </div>\n" +
    "                </div>\n" +
    "                <div class=\"col-md-3\">\n" +
    "                    <table class=\"table\">\n" +
    "                        <tr>\n" +
    "                            <td>\n" +
    "                                <br>\n" +
    "                                   <p class=\"text-center\">"+rs.getString("Titulo_Pastel")+"</p>" +
    "                                <br>\n" +
    "                            </td>\n" +
    "                        </tr>\n" +
    "                        <tr>\n" +
    "                            <td>\n" +
    "                                <br>\n" +
    "                                   <p class=\"text-center\">"+rs.getString("Descripcion_Pastel")+"</p>"+
    "                                <br>\n" +
    "                            </td>\n" +
    "                        </tr>\n" +
    "                        <tr>\n" +
    "                            <td>\n" +
    "                                <br>\n" +
    "                            <div class=\"text-center\">\n" +
    "                                <form action=\"/Pasteleria/AgregarCarrito\">\n" +
    "                                    <input name=\"pastel\" type=\"hidden\" value=\""+rs.getInt("Pastel_ID")+"\">\n"+
    "                                    <input name=\"origen\" type=\"hidden\" value=\"2\">\n"+
    "                                    <p>Costo: $"+rs.getInt("Costo_u_Pastel")+"</p>\n"+
    "                                    <input name=\"cantidad\" type=\"number\" class=\"form-contro\" value=\"0\" min=\"0\" max=\""+rs.getInt("Stock_Pastel")+"\">\n";
                if(ID==null)
                        result+= "<a href=\"/Pasteleria/Login\" class=\"btn btn-primary\">Agregar a Carrito</a>";
                    else
                        result+=
    "                                    <button type=\"submit\" class=\"btn btn-primary\" name=\"Agreagar\">Agregar a Carrito</button> \n";
                result+=
    "                                </form>\n"+
    "                                <br>\n" +
    "                            </td>\n" +
    "                        </tr>\n" +
    "                    </table>\n" +
    "                </div>\n" +
    "            </div>\n" +
    "        </div>";
            }
            
            
            
        }catch (Exception e) {
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Descripcion del pastel</title>");            
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">");
            out.println("</head>");
            out.println("<body>");
            out.println(header);
            out.println("<br><br>");
            out.println(result);
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
