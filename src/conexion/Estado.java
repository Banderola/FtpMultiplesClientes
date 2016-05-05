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
public class Estado extends Mensaje {
    private int tipo;
    private String Mensaje;
    private boolean valido;
    
    public Estado(int tipo,boolean valido, String Mensaje) {
        super(2);
        this.tipo=tipo;
        this.valido=valido;
        this.Mensaje=Mensaje;
        this.idSession=0;
    }
    
    public Estado(int idSession, int tipo,boolean valido, String Mensaje) {
        super(2);
        this.tipo=tipo;
        this.valido=valido;
        this.Mensaje=Mensaje;
        this.idSession=idSession;
    }

    public int getTipo() {
        return tipo;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public boolean isValido() {
        return valido;
    }

    public int getIdSession() {
        return idSession;
    }
    
    
    
    
}
