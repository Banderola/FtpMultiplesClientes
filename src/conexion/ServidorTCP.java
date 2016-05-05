/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edson
 */
abstract public class ServidorTCP extends Thread{
    protected Socket sc;
     
    protected ServidorTCP(Socket sc) throws IOException {
        this.sc=sc;
           
    }
   
    
    
    protected Mensaje obtenerMensaje()  {
        InputStream is=null;
        ObjectInput oi=null;
         try {
             is =sc.getInputStream();
             oi = new ObjectInputStream(is);
         } catch (IOException ex) {
             Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
         }
        Mensaje mensaje=null;
        while(true){    
            try {     
                mensaje = (Mensaje)oi.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
                break;                        
        }
        
        return mensaje;
    }
    
    protected void enviarMensaje(Mensaje mensaje){
           
        try {
            OutputStream os = sc.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(mensaje);
            oos.flush();
            
        } catch (IOException ex) {
            System.out.println("Error al enviar mensaje al servidor");
        }
       
         
    }
    
    
    
    
    
    
}
