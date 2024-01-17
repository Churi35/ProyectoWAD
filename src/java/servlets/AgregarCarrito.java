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
public class AgregarCarrito extends HttpServlet {
    
    ConexionDB coneccion = new ConexionDB();
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

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
        byte pastel = Byte.parseByte(request.getParameter("pastel"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        byte origen =Byte.parseByte(request.getParameter("origen"));
        int max = -1;
        String query = "select * from pasteles where `Pastel_ID`= "+pastel;
        if(request.getParameter("peticion")!=null)
        {
            Carrito.remove(pastel);
        }
        else
        {
            try
            {
                con=coneccion.getConnection();
                ps=con.prepareStatement(query);
                rs=ps.executeQuery();
                while(rs.next())
                    max=rs.getInt("Stock_Pastel");
            } catch (SQLException ex) {
                Logger.getLogger(AgregarCarrito.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(cantidad !=0)
            {
                if(Carrito.containsKey(pastel))
                    if(Carrito.get(pastel)+cantidad>max)
                        Carrito.put(pastel, max);
                    else
                        Carrito.put(pastel, Carrito.get(pastel)+cantidad);
                else
                    Carrito.put(pastel, cantidad);

            } 
        }
        
        
        sesion.setAttribute("Carrito", Carrito);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>AgregarCarrito</title>");
            switch (origen) {
                case 0:
                    out.println("<meta http-equiv=\"Refresh\" content=\"0; url='http://localhost:8081/Pasteleria/Inicio'\"/>");
                break;
                case 1:
                    out.println("<meta http-equiv=\"Refresh\" content=\"0; url='http://localhost:8081/Pasteleria/Carrito'\"/>");
                break;
                default:
                    out.println("<meta http-equiv=\"Refresh\" content=\"0; url='http://localhost:8081/Pasteleria/Descripcionpastel?id="+pastel+"'\"/>");
                break;
            }
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Agregando producto</h1>");
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
