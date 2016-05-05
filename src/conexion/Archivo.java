/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edson
 */
public class Archivo extends Mensaje {
    private byte[] datos;
    private String nombre;
    

    public Archivo(int idSession,File archivo)  {
        super(3);
        this.idSession=idSession;
        try {
            datos = Files.readAllBytes(archivo.toPath());
            
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.nombre=archivo.getName();
        
    }

    public void getFile(String nombre){
        
        try { 
            Files.write(new File(nombre + this.nombre).toPath(), datos,CREATE);
        } catch (IOException ex) {
            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }

    

    
    
    
}
