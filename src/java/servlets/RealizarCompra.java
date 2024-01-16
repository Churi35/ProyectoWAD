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
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Uriel Perez Cubias
 */
public class RealizarCompra extends HttpServlet {
    
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
        ArrayList<String> querys = new ArrayList<>();
        Integer ID = (Integer) sesion.getAttribute("User.ID");
        String pedido = "";
        double total = 0;
        String query = "select * from pasteles";
        String result = "";
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
                            querys.add("UPDATE `pasteles` SET `Stock_Pastel` = '"+(rs.getInt("Stock_Pastel")-Carrito.get(i))+"' WHERE `pasteles`.`Pastel_ID` = "+rs.getInt("Pastel_ID"));
                            pedido+=String.valueOf(i)+","+Carrito.get(i)+"_";
                            total += Carrito.get(i)*rs.getInt("Costo_u_Pastel");
                            break;
                        }
                    }
                }
                
                query = "INSERT INTO `pedidos` (`User_ID`,`Pedido_Productos`,`Pedido_Total`) VALUES ('"+ID+"','"+pedido+"','"+(int)total+"');";
                con=coneccion.getConnection();
                ps=con.prepareStatement(query);
                ps.execute();
                
                for(String queryU : querys)
                {
                    con=coneccion.getConnection();
                    ps=con.prepareStatement(queryU);
                    ps.execute();
                }
            } catch (SQLException ex) {
                result += ex;
            }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RealizarCompra</title>");      
            if(result.equals(""))
                out.print("<meta http-equiv=\"Refresh\" content=\"0; url='http://localhost:8081/Pasteleria/Inicio'\"/>");
            out.println("</head>");
            out.println("<body>");
            if(result.equals(""))
                out.println("Exito al realizar la compra");
            else
                out.println("Error al realizar la compra: "+result);
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
