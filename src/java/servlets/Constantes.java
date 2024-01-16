/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets;

public class Constantes {
    
    private static String header_sin_user = "<nav class=\"navbar navbar-expand-lg navbar-light\" style=\"background-color: #e3f2fd;\">\n" +
"  <div class=\"container-fluid\">\n" +
"    <a class=\"navbar-brand\" href=\"/Pasteleria/Inicio\">Pasteleria</a>\n" +
"    <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
"      <span class=\"navbar-toggler-icon\"></span>\n" +
"    </button>\n" +
"    <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n" +
"        <ul class=\"navbar-nav ms-auto\">\n" +
"            <li class=\"nav-item\">\n" +
"                <a class=\"nav-link\" href=\"/Pasteleria/Login\">Iniciar Sesión</a>\n" +
"            </li>\n" +
"        </ul>\n" +
"    </div>\n" +
"  </div>\n" +
"</nav>";
    
    private static String header_con_user = "<nav class=\"navbar navbar-expand-lg navbar-light\" style=\"background-color: #e3f2fd;\">\n" +
"  <div class=\"container-fluid\">\n" +
"    <a class=\"navbar-brand\" href=\"/Pasteleria/Inicio\">Pasteleria</a>\n" +
"    <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
"      <span class=\"navbar-toggler-icon\"></span>\n" +
"    </button>\n" +
"    <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n" +
"        <ul class=\"navbar-nav ms-auto\">\n" +
"            <li class=\"nav-item\">\n" +
"                <a class=\"nav-link\" href=\"/Pasteleria/Carrito\">Carrito</a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"                <a class=\"nav-link\" href=\"/Pasteleria/HistorialCompras\">Historial de compras</a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"                <a class=\"nav-link\" href=\"/Pasteleria/Logout\">Cerrar Sesión</a>\n" +
"            </li>\n" +
"        </ul>\n" +
"    </div>\n" +
"  </div>\n" +
"</nav>";
    public static String getHeaderSin()
    {
        return header_sin_user;
    }
            
    public static String getHeaderCon()
    {
        return header_con_user;
    }
}
