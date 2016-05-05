/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import servidor.ServidorFTP;

/**
 *
 * @author Edson
 */
public class Conexiones extends ServidorTCP{
    
    private JTextArea mensajes;
    private boolean activo;
    private int idSession;
    
    
    public Conexiones(Socket sc,JTextArea mensajes) throws IOException {
        super(sc);
        this.mensajes=mensajes;
        this.activo=true;
        this.idSession=-1;
          
    }
     
    
   @Override
	public void run() {
            while(activo){
                
                procesarPeticion(this.obtenerMensaje());
        
                
            }
        
        } 
        
    
    private boolean validarCliente(String user, String pass) {
        boolean valido=false;
        String linea;
        FileReader fr=null;
        try {
            fr = new FileReader ("user.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServidorFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br = new BufferedReader(fr);
        
        try {
            while((linea=br.readLine())!=null && valido==false) {
                
                StringTokenizer st = new StringTokenizer(linea,",");
                String[] split = new String[]{"",""};
                if(st.countTokens()==2){
                    split[0]=st.nextToken();
                    split[1]=st.nextToken();
                    if(split[0].compareTo(user)==0 && split[1].compareTo(pass)==0)
                    {
                        valido=true;
                    }
                    
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valido;
    }


    protected void procesarPeticion(Mensaje mensaje) {
        
        switch(mensaje.getId()){
            case 1:
                Autentificacion aut=(Autentificacion)mensaje;
                if(validarCliente(aut.getUsername(),aut.getContraseña()))
                {
                   this.idSession=new Random().nextInt();
                   this.enviarMensaje(new Estado(this.idSession,1,true,"Ingreso Valido")); 
                   
                }
                else
                {
                    this.enviarMensaje(new Estado(1,false,"Ingreso Invalido"));
                    
                }   
                
                break;
                
            case 2:
                if(mensaje.getIdSession()==this.idSession){
                    Estado est=(Estado)mensaje;
                    if(est.getTipo()==3){
                        File archivo= new File(est.getMensaje());
                        if(archivo.exists()){
                            if(archivo.isFile()){
                              this.enviarMensaje(new Archivo(0,archivo));  
                            }
                            else{
                              this.enviarMensaje(new Estado(2,false,"El elemento no es un archivo"));
                            }
                            
                            
                        }
                        else{
                            this.enviarMensaje(new Estado(2,false,"El nombre del fichero indicado no existe en el servidor"));
                            
                        }
                        
                    }
                    else if(est.getTipo()==4)
                    {
                            String[] ficheros = new File(".").list();
                            if(ficheros==null){
                                this.enviarMensaje(new Estado(2,false,"No hay ficheros en el Servidor"));
                                
                            }
                            else{
                                this.enviarMensaje(new Lista(ficheros));
                                
                            }
                            
                    }
                }
                else{
                   this.enviarMensaje(new Estado(2,false,"Solicitud no Autorizada"));
                   
                }
                
                break;
            case 3:
                if(mensaje.getIdSession()==this.idSession){
                    Archivo arc=(Archivo)mensaje;
                    arc.getFile("S_");
                    this.enviarMensaje(new Estado(2,false,"Envio de fichero Correcto"));
                    
                }
                else{
                    this.enviarMensaje(new Estado(2,false,"Envio no autorizado"));
                    
                }
                
                break;
            
        }
        
    }
    
    public void cerrarConexion() throws IOException{
        this.activo=false;
        sc.close();
        
    }
    
   
    
}
