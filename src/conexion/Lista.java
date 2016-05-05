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
public class Lista extends Mensaje {
    private String[] lista;

    public Lista(String[] lista) {
        super(4);
        this.lista=lista;
    }

    public String[] getLista() {
        return lista;
    }
    
    
    
    
}
