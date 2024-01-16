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
public class Login extends HttpServlet {
    
    ConexionDB coneccion = new ConexionDB();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    private String header = Constantes.getHeaderSin();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param Procedencia Saber si la peticion viene desde get o desde post para hacer la validacion
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response , boolean Procedencia)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        ArrayList<String> salidas = new ArrayList<>();
        boolean validacion = false;
        if(Procedencia)
        {
            String Correo = request.getParameter("correo");
            String Contra = request.getParameter("contra");
            
            Integer ID = -1;
            if(Correo != null)
            {
                if(Correo.equals(""))
                    salidas.add("Debe ingresar un correo ");
                else if(!Correo.contains("@"))
                    salidas.add("Correo invalido ");
            }
            else
                salidas.add("Algo salio mal en el correo ");
            
            if(Contra != null)
            {
                if(Contra.equals(""))
                    salidas.add("Debe ingresar una contraseña ");
            }
            else
                salidas.add("Algo salio mal en la contraseña ");
            
            if(salidas.isEmpty())
            {
                String query = "select * from users where `User_Correo` = '"+Correo+"'";
                
                try{
                    con=coneccion.getConnection();
                    ps=con.prepareStatement(query);
                    rs=ps.executeQuery();
                    while(rs.next()){
                        if(Contra.equals(rs.getString("User_Contraseña")))
                        {
                            validacion = true;
                            ID = rs.getInt("User_Id");
                        }
                            
                    }
                }catch (Exception e) {
                        
                }
                
                if(validacion)
                {
                    sesion.setAttribute("User.ID", ID.intValue());
                    sesion.setAttribute("Carrito", new HashMap<Byte,Integer>());
                }
                else
                    salidas.add("Correo y/o contraseña incorrecta");
            }
        }
            
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("    <head>");
            out.println("<title>Login</title>");
            out.println("        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">");
            out.println("        <meta http-equiv=\"Content-type\" content=\"text/html ; charset=utf-8\"/>");
            if(validacion)
                out.println("<meta http-equiv=\"Refresh\" content=\"0; url='http://localhost:8081/Pasteleria/Inicio'\"/>");
            out.println("    </head>");
            out.println("    <body>");
            out.println(header);
            out.println("<br>");
            out.println("<br>");
            out.println("<br>");
            out.println("        <div class=\"col-md-6 col-sm-12 mx-auto\">");
            out.println("            <div class=\"card card-body\">");
            out.println("                <div class=\"login-form\">");
            out.println("                <form action=\"/Pasteleria/Login\" method=\"post\">");
            if(!salidas.isEmpty())
            {
                out.println("<div class=\"alert alert-danger\">");
                for(int i = 0; i < salidas.size(); i++)
                {
                    out.println("<li>"+salidas.get(i)+"</li>");
                }
                out.println("</div>");
                out.println("<br>");
            }
            out.println("                    <div class=\"form-group\">");
            out.println("                        <label>Correo</label>");
            out.println("                        <input name=\"correo\" type=\"text\" class=\"form-control\" placeholder=\"algo@algo\">");
            out.println("                    </div>");
            out.println("                    <br>");
            out.println("                    <div class=\"form-group\">");
            out.println("                        <label>Contraseña</label>");
            out.println("                        <input name=\"contra\" type=\"password\" class=\"form-control\" placeholder=\"Contraseña\">");
            out.println("                    </div>");
            out.println("<br>");
            out.println("                    <button type=\"submit\" name=\"Entrar\" class=\"btn btn-primary\">Entrar</button>   ");
            out.println("                    <a href=\"/Pasteleria/Registro\" class=\"btn btn-info\">Registrar</a>");
            out.println("                </form>");
            out.println("                </div>");
            out.println("            </div>");
            out.println("         </div>");
            
            out.println("    </body>");
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
        processRequest(request, response, false);
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
        processRequest(request, response, true);
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
