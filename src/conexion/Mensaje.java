/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.io.Serializable;

/**
 *
 * @author Edson
 */
public class Mensaje implements Serializable{
    private int id;
    protected int idSession;

    protected Mensaje(int id) {
        this.id = id;
        this.idSession=0;
    }
    protected Mensaje(int id, int idSession) {
        this.id = id;
        this.idSession=idSession;
    }

    public int getId() {
        return id;
    }

    public int getIdSession() {
        return idSession;
    }
    
    
    
    
    
}
