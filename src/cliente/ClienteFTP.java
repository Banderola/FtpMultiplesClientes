/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import conexion.Archivo;
import conexion.Autentificacion;
import conexion.ClienteTCP;
import conexion.Estado;
import conexion.Lista;
import conexion.Mensaje;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.Timer;


/**
 *
 * @author Edson
 */

public class ClienteFTP extends ClienteTCP {
    private int idSession;
    private Timer timer;
    private String ip;
    private int puerto;
    private JList jList1;
    
    public ClienteFTP(String ip, int puerto, JList jList1) {
        this.ip=ip;
        this.puerto=puerto;
        this.idSession=0;
        this.jList1=jList1;
        timer = new Timer (60000, new ActionListener (){public void actionPerformed(ActionEvent e)
        {    
           timer.stop();
           showMessageDialog(null, "Tiempo de respuesta Excedido");
           reiniciarConexion();     
        }});
        
        if(this.conectar(ip, puerto)){
            showMessageDialog(null, "Conectado");
        }
        else
        {
            showMessageDialog(null, "Desconectado");
        }
        
    }
    
    public void enviarUsuario(String user, String pass) {
        timer.start();
        this.enviarMensaje(new Autentificacion(user,pass));
        this.procesarPeticion(this.obtenerMensaje());
    }

    @Override
    protected void procesarPeticion(Mensaje mensaje) {
        switch(mensaje.getId()){
            case 2:
                timer.stop();
                Estado men=(Estado)mensaje;
                if(men.getTipo()==1){
                    this.idSession=men.getIdSession();
                }
                System.out.println(men.getMensaje());
                showMessageDialog(null, men.getMensaje());
                break;
            case 3:
                timer.stop();
                Archivo arc=(Archivo)mensaje;
                arc.getFile("C_");
                showMessageDialog(null, "Archivo Transferido Correctamente");
                break;
            case 4:
                timer.stop();
                Lista list=(Lista)mensaje;
                listar(list.getLista());
                break;
            
        }
    }
    
    
   public void enviarArchivo(File archivo){
       if(archivo.exists()){
          timer.start();
          this.enviarMensaje(new Archivo(this.idSession,archivo));
          this.procesarPeticion(this.obtenerMensaje());  
       }
       else{
          showMessageDialog(null, "El Archivo no existe"); 
       }    
   }
   
   public void peticionArchivo(String nombre){
       if(nombre==null){
           showMessageDialog(null, "El Archivo no existe"); 
       }
       else{
            timer.start();
            this.enviarMensaje(new Estado (this.idSession,3,true,nombre));
            this.procesarPeticion(this.obtenerMensaje());
       } 
   }
   
   public void peticionLista(){
       timer.start();
       this.enviarMensaje(new Estado (this.idSession,4,true,""));
       this.procesarPeticion(this.obtenerMensaje());      
   }
   
   private void listar(String[] nombreFicheros){
       DefaultListModel listModel = new DefaultListModel();
       for (String nombreFichero : nombreFicheros) {
           listModel.addElement(nombreFichero);
       }
       
       this.jList1.setModel(listModel);
   
   }
   
   private void reiniciarConexion(){
         this.cerrarConexion();  
         this.conectar(ip, puerto);
   }
   
 
    
}
