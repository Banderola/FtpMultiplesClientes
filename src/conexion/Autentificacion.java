/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

/**
 *
 * @author Edson
 */
public class Autentificacion extends Mensaje{
    private String username;
    private String contraseña;

    public Autentificacion(String username, String contraseña ) {
        super(1);
        this.username = username;
        this.contraseña = contraseña;
    }

    public String getUsername() {
        return username;
    }

    public String getContraseña() {
        return contraseña;
    }
    
    
    
    
    
}
