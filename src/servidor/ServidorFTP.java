/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import conexion.Conexiones;
import conexion.ServidorTCP;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;



/**
 *
 * @author Edson
 */
public class ServidorFTP  extends Thread{
    private ServerSocket ss;
    private JTextArea mensajes;
    private ArrayList<Conexiones> conexiones;
    private boolean activo;

    public ServidorFTP(int puerto, JTextArea mensajes) throws IOException {
        this.mensajes=mensajes;
        this.ss = new ServerSocket(puerto);
        this.conexiones= new ArrayList<Conexiones>();
        this.activo=true;
        
        
    }
    
    public void escucha() {
        try {
            Conexiones conTemp= new Conexiones(ss.accept(),mensajes);
            this.conexiones.add(conTemp);
            conTemp.start();
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
   

   @Override
	public void run() {
            while(activo){
               escucha();
                
            }
        
        } 
    
    public void cerrarServidor() {
        this.activo=false;
        try {
            cerrarConexiones();
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void cerrarConexiones() throws IOException{
        for (Conexiones conexion : conexiones) {
            conexion.cerrarConexion();
        }
    }
    
 
    
    
  
    
}
