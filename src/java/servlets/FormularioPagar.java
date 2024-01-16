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
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Uriel Perez Cubias
 */
public class FormularioPagar extends HttpServlet {

    private String header;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param procedencia Saber si la peticion viene desde get o desde post para hacer la validacion
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean procedencia)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession();
        Integer ID = (Integer) sesion.getAttribute("User.ID");
        ArrayList<String> salidas = new ArrayList<>();
        byte mes = -1;
        int año = -1;
        Pattern pattern = null;
        //Matcher matcher = null;pattern.matcher("Visit W3Schools!");
        boolean validacion = false;
        if(ID==null)
            header = Constantes.getHeaderSin();
        else
            header = Constantes.getHeaderCon();
        
        if(procedencia)
        {
            if(request.getParameter("Nombre")!=null)
            {
                if(request.getParameter("Nombre").equals(""))
                    salidas.add("Debe ingresar su Nombre");
            }
            else 
                salidas.add("Ocurrio un error nombre");
            
            if(request.getParameter("Direccion")!=null)
            {
                if(request.getParameter("Direccion").equals(""))
                    salidas.add("Debe ingresar su Direccion");
            }
            else 
                salidas.add("Ocurrio un error Direccion");
            
            if(request.getParameter("Ciudad")!=null)
            {
                if(request.getParameter("Ciudad").equals(""))
                    salidas.add("Debe ingresar su Ciudad");
            }
            else 
                salidas.add("Ocurrio un error Ciudad");
            
            if(request.getParameter("Estado")!=null)
            {
                if(request.getParameter("Estado").equals("SE") || request.getParameter("Estado").equals("Seleccione su estado"))
                    salidas.add("Debe ingresar su Estado");
            }
            else 
                salidas.add("Ocurrio un error nombre");
            
            if(request.getParameter("Codigo")!=null)
            {
                pattern = Pattern.compile("[0-9]{5}");
                if(request.getParameter("Codigo").equals(""))
                    salidas.add("Debe ingresar su Codigo");
                else if(!pattern.matcher(request.getParameter("Codigo")).find())
                    salidas.add("Ingrese un Codigo postal valido");
            }
            else 
                salidas.add("Ocurrio un error Codigo");
            
            if(request.getParameter("Num_Tarjet")!=null)
            {
                pattern = Pattern.compile("[0-9]{16}");
                if(request.getParameter("Num_Tarjet").equals(""))
                    salidas.add("Debe ingresar su Número de tarjeta");
                else if(!pattern.matcher(request.getParameter("Num_Tarjet")).find())
                    salidas.add("Ingrese un Número de tarjeta valido");
            }
            else 
                salidas.add("Ocurrio un error Tarjeta");
            
            if(request.getParameter("Mes")!=null)
            {
                pattern = Pattern.compile("[0-1]{0,1}[0-9]{1}");
                if(request.getParameter("Mes").equals(""))
                    salidas.add("Debe ingresar su Mes de expiración");
                else if(!pattern.matcher(request.getParameter("Mes")).find())
                    salidas.add("Ingrese un Mes de expiración valido");
                else if(Integer.parseInt(request.getParameter("Mes"))>0 && Integer.parseInt(request.getParameter("Mes"))<13)
                    mes = (byte) (Byte.parseByte(request.getParameter("Mes"))-1);
                else
                    salidas.add("Ingrese un Mes de expiración valido");
            }
            else 
                salidas.add("Ocurrio un error Mes");
            
            if(request.getParameter("Ano")!=null)
            {
                pattern = Pattern.compile("[0-9]{2}");
                if(request.getParameter("Ano").equals(""))
                    salidas.add("Debe ingresar su Año de expiración");
                else if(!pattern.matcher(request.getParameter("Ano")).find())
                    salidas.add("Ingrese un Año de expiración valido");
                else
                    año = 2000 + Integer.parseInt(request.getParameter("Ano"));
            }
            else 
                salidas.add("Ocurrio un error Mes");
            
            if(request.getParameter("CCV")!=null)
            {
                pattern = Pattern.compile("[0-9]{3}");
                if(request.getParameter("CCV").equals(""))
                    salidas.add("Debe ingresar su CCV");
                else if(!pattern.matcher(request.getParameter("CCV")).find())
                    salidas.add("Ingrese un CCV valido");
            }
            else 
                salidas.add("Ocurrio un error CCV");
            
            if(Calendar.getInstance().get(Calendar.YEAR)>año && año != -1)
                salidas.add("Ingrese una tarjeta valida (expiración)");
            else if(Calendar.getInstance().get(Calendar.YEAR)==año && año != -1)
                if(Calendar.getInstance().get(Calendar.MONTH)>mes && mes != -1)
                    salidas.add("Ingrese una tarjeta valida (expiración)");
            
            if(request.getParameter("Nombre_Tar")!=null)
            {
                if(request.getParameter("Nombre_Tar").equals(""))
                    salidas.add("Debe ingresar el Nombre del dueño de la tarjeta");
            }
            else 
                salidas.add("Ocurrio un error Nombre_Tar");
            
            if(salidas.isEmpty())
            {
                validacion = true;
            }
        }
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FormularioPagar</title>");
            out.println("<meta charset=\"utf-8\" />");
            if(validacion)
                out.print("<meta http-equiv=\"Refresh\" content=\"0; url='http://localhost:8081/Pasteleria/RealizarCompra'\"/>");
            out.println("<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">");
            out.println("<link href=\"./CSS/Pagarstyle.css\" rel=\"stylesheet\"");
            out.println("</head>");
            out.println("<body>");
            
            if(validacion)
                out.print("<p><h1>Realizando Compra</h1></p>");
            else
            {
                out.println(header);
                out.println("<br>");
                out.println("<div class=\"card card-body\">\n" +
                "            <form action=\"/Pasteleria/FormularioPagar\" method=\"post\">\n");
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
                out.println(
                "                <div class=\"form-group\"> <!-- Full Name -->\n" +
                "                    <label for=\"full_name_id\" class=\"control-label\">Nombre Completo</label>\n" +
                "                    <input type=\"text\" class=\"form-control\" id=\"full_name_id\" name=\"Nombre\" placeholder=\"Ingrese su nombre completo\">\n" +
                "                </div>    \n" +
                "\n" +
                "                <div class=\"form-group\"> <!-- Street 1 -->\n" +
                "                    <label for=\"street1_id\" class=\"control-label\">Direccion Completa</label>\n" +
                "                    <input type=\"text\" class=\"form-control\" id=\"street1_id\" name=\"Direccion\" placeholder=\"Ingrese su dirección\">\n" +
                "                </div>               \n" +
                "\n" +
                "                <div class=\"form-group\"> <!-- City-->\n" +
                "                    <label for=\"city_id\" class=\"control-label\">Ciudad</label>\n" +
                "                    <input type=\"text\" class=\"form-control\" id=\"city_id\" name=\"Ciudad\" placeholder=\"Ingrese su ciudad\">\n" +
                "                </div>                                    \n" +
                "                                        \n" +
                "                <div class=\"form-group\"> <!-- State Button -->\n" +
                "                    <label for=\"state_id\" class=\"control-label\">Estado</label>\n" +
                "                    <select name=\"Estado\"class=\"form-control\" id=\"state_id\">\n" +
                "                        <option value=\"SE\">Seleccione su estado</option>\n" +
                "                        <option>Aguascalientes</option>\n" +
                "                        <option>Baja California</option>\n" +
                "                        <option>Baja California Sur</option>\n" +
                "                        <option>Campeche</option>\n" +
                "                        <option>Chiapas</option>\n" +
                "                        <option>Chihuahua</option>\n" +
                "                        <option>Ciudad de México</option>\n" +
                "                        <option>Coahuila</option>\n" +
                "                        <option>Colima</option>\n" +
                "                        <option>Durango</option>\n" +
                "                        <option>Estado de México</option>\n" +
                "                        <option>Guanajuato</option>\n" +
                "                        <option>Guerrero</option>\n" +
                "                        <option>Hidalgo</option>\n" +
                "                        <option>Jalisco</option>\n" +
                "                        <option>Michoacán</option>\n" +
                "                        <option>Morelos</option>\n" +
                "                        <option>Nayarit</option>\n" +
                "                        <option>Nuevo León</option>\n" +
                "                        <option>Oaxaca</option>\n" +
                "                        <option>Puebla</option>\n" +
                "                        <option>Querétaro</option>\n" +
                "                        <option>Quintana Roo</option>\n" +
                "                        <option>San Luis Potosí</option>\n" +
                "                        <option>Sinaloa</option>\n" +
                "                        <option>Sonora</option>\n" +
                "                        <option>Tabasco</option>\n" +
                "                        <option>Tamaulipas</option>\n" +
                "                        <option>Tlaxcala</option>\n" +
                "                        <option>Veracruz</option>\n" +
                "                        <option>Yucatán</option>\n" +
                "                        <option>Zacatecas </option>\n" +
                "                    </select>                    \n" +
                "                </div>\n" +
                "                \n" +
                "                <div class=\"form-group\"> <!-- Zip Code-->\n" +
                "                    <label for=\"zip_id\" class=\"control-label\">Codigo Postal</label>\n" +
                "                    <input type=\"text\" class=\"form-control\" id=\"zip_id\" name=\"Codigo\" maxlength=\"5\" placeholder=\"Ingrese su codigo postal\">\n" +
                "                </div>        \n" +
                "                \n" +
                "                <br><br>    \n" +
                "\n" +
                "                <!---------------------------------------------------------------------------------------->\n" +
                "\n" +
                "                <div class=\"credit-card-div card card-body\">\n" +
                "                    <div class=\"panel panel-default\" >\n" +
                "                        <div class=\"panel-heading\">\n" +
                "                            <div class=\"row \">\n" +
                "                                <div class=\"col-md-12\">\n" +
                "                                    <input type=\"text\" name=\"Num_Tarjet\" maxlength=\"16\" class=\"form-control\" placeholder=\"Ingresa el numero de tarjeta\" />\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"row \">\n" +
                "                                <div class=\"col-md-3 col-sm-3 col-xs-3\">\n" +
                "                                    <span class=\"help-block text-muted small-font\" >Mes de expiración</span>\n" +
                "                                    <input type=\"text\" name=\"Mes\" maxlength=\"2\" class=\"form-control\" placeholder=\"MM\" />\n" +
                "                                </div>\n" +
                "                                <div class=\"col-md-3 col-sm-3 col-xs-3\">\n" +
                "                                    <span class=\"help-block text-muted small-font\" >Año de expiración</span>\n" +
                "                                    <input type=\"text\" name=\"Ano\" maxlength=\"2\" class=\"form-control\" placeholder=\"YY\" />\n" +
                "                                </div>\n" +
                "                                <div class=\"col-md-3 col-sm-3 col-xs-3\">\n" +
                "                                    <span class=\"help-block text-muted small-font\" >  CCV</span>\n" +
                "                                    <input type=\"text\" name=\"CCV\" maxlength=\"3\" class=\"form-control\" placeholder=\"CCV\" />\n" +
                "                                </div>\n" +
                "                                <div class=\"col-md-3 col-sm-3 col-xs-3\">\n" +
                "                                    <img src=\"./Imagenes/tarjeta.png\" class=\"img-rounded\" />\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"row \">\n" +
                "                                <div class=\"col-md-12 pad-adjust\">\n" +
                "                                    <input type=\"text\" name=\"Nombre_Tar\" class=\"form-control\" placeholder=\"Nombre del dueño de la tarjeta\" />\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"row \">\n" +
                "                                <div class=\"col-md-6 col-sm-6 col-xs-6 pad-adjust\">\n" +
                "                                    <a href=\"/Pasteleria/Carrito\" type=\"submit\"  class=\"btn btn-danger\"/>Cancelar</a>\n" +
                "                                </div>\n" +
                "                                <div class=\"col-md-6 col-sm-6 col-xs-6 pad-adjust\">\n" +
                "                                    <input type=\"submit\"  class=\"btn btn-warning btn-block\" value=\"Comprar\" />\n" +
                "                                </div>\n" +
                "                            </div>    \n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </form>\n" +
                "        </div>"); 
            }
            
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
        processRequest(request, response,false);
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
        processRequest(request, response,true);
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
