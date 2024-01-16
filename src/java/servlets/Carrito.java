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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Uriel Perez Cubias
 */
public class Carrito extends HttpServlet {
    
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
        HashMap<Byte,Integer> Carrito = (HashMap<Byte,Integer>) sesion.getAttribute("Carrito");
        Integer ID = (Integer) sesion.getAttribute("User.ID");
        double total = 0;
        header = Constantes.getHeaderCon();
        String query = "select * from pasteles";
        String result = "";
        
        if(!Carrito.isEmpty())
            try {
                con=coneccion.getConnection();
                ps=con.prepareStatement(query);
                rs=ps.executeQuery();
                while(rs.next())
                {
                    for(byte i : Carrito.keySet())
                    {
                        if(rs.getInt("Pastel_ID")==i)
                        {
                            result+="<tr>\n"+
                                    "   <td>"+
                                    "       <div class=\"card card-body\">\n"+
                                    "           <a href=\"/Pasteleria/Descripcionpastel?id="+i+"\"><img src=\"."+rs.getString("Imagen_Pastel")+"\"></a>\n" +
                                    "       </div>"+
                                    "   </td>"+
                                    "   <td><p class=\"text-center\">"+rs.getString("Titulo_Pastel")+"</p></td>"+
                                    "   <td><p class=\"text-center\">"+rs.getInt("Costo_u_Pastel")+"</p></td>"+
                                    "   <td><p class=\"text-center\">"+Carrito.get(i)+"</p></td>"+
                                    "   <td><p class=\"text-center\">"+Carrito.get(i)*rs.getInt("Costo_u_Pastel")+"</p></td>"+
                                    "   <td>"+
                                    "       <form action=\"/Pasteleria/AgregarCarrito\">\n" +
                                    "           <input name=\"pastel\" type=\"hidden\" value=\""+rs.getInt("Pastel_ID")+"\">\n"+
                                    "           <input name=\"origen\" type=\"hidden\" value=\"1\">\n"+
                                    "           <input name=\"cantidad\" type=\"number\" class=\"form-contro\" value=\"0\" min=\""+((Carrito.get(i)*-1)+1)+"\" max=\""+rs.getInt("Stock_Pastel")+"\">\n"+
                                    "           <button type=\"submit\" class=\"btn btn-primary\" name=\"Agreagar\">Agregar/Quitar</button> \n"+
                                    "           <a href=\"/Pasteleria/AgregarCarrito?pastel="+rs.getInt("Pastel_ID")+"&cantidad=0&origen=1&peticion=true\" class=\"btn btn-danger\">Eliminar</a> \n"+
                                    "       </form>\n" +
                                    "   </td>"+
                                    "</tr>";
                            total += Carrito.get(i)*rs.getInt("Costo_u_Pastel");
                            break;
                        }
                    }


                }
            } catch (SQLException ex) {
            }
        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Carrito</title>");
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">");            
            out.println("<link href=\"./CSS/Iniciostyle.css\" rel=\"stylesheet\"");
            out.println("</head>");
            out.println("<body>");
            out.println(header);
            out.println("<br>");
            out.println("<div class=\"col-md-6\">\n" +
"            <table class=\"table\">\n"+
            "<thead>\n"+
                "<tr>\n"+
                    "<th><p class=\"text-center\">Pastel</p></th>\n"+
                    "<th><p class=\"text-center\">Nombre</p></th>\n"+
                    "<th><p class=\"text-center\">Costo Unitario</p></th>\n"+
                    "<th><p class=\"text-center\">Cantidad</p></th>\n"+
                    "<th><p class=\"text-center\">Costo</p></th>\n"+
                    "<th><p class=\"text-center\">Acciones</p></th>\n"+
                "</tr>\n"+
            "</thead>");
            out.println(result);
            out.println("<tfoot>\n"+
                    "<tr>"+
                    "<th></th>"+
                    "<th></th>"+
                    "<th></th>"+
                    "<th>Total</th>"+
                    "<th>"+total+"</th>"+
                    "<th>");
            if(total != 0)
                out.println("<a href=\"/Pasteleria/FormularioPagar\" class=\"btn btn-primary\">Comprar</a>");
            out.println(
                    "</th>"+
                    "</tr>"+
                    "</table>\n" +
"            </div>");
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
