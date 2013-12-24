package ar.com.airdrop.services;

import java.io.FileInputStream;
import java.io.ObjectOutputStream;

import ar.com.airdrop.dominio.Mensaje;
import ar.com.airdrop.dominio.MensajeTomaFichero;
import ar.com.airdrop.exceptions.EnviarSocketException;

public class EnvioService{

	
//	private static int PUERTO = 8123;



	public void enviarMensaje(Mensaje mensaje) throws EnviarSocketException {

		
		EnviarMensaje enviarMensaje = new EnviarMensaje(mensaje);
		enviarMensaje.start();
	}
	
	
	
	 public void enviaFichero(String fichero, ObjectOutputStream oos)
	    {
		 
		 
		 
	        try
	        {
	            boolean enviadoUltimo=false;
	            // Se abre el fichero.
	            FileInputStream fis = new FileInputStream(fichero);
	            
	            // Se instancia y rellena un mensaje de envio de fichero
	            MensajeTomaFichero mensaje = new MensajeTomaFichero();
	            mensaje.nombreFichero = fichero;
	            
	            // Se leen los primeros bytes del fichero en un campo del mensaje
	            int leidos = fis.read(mensaje.contenidoFichero);
	            
	            // Bucle mientras se vayan leyendo datos del fichero
	            while (leidos > -1)
	            {
	                
	                // Se rellena el número de bytes leidos
	                mensaje.bytesValidos = leidos;
	                
	                // Si no se han leido el máximo de bytes, es porque el fichero
	                // se ha acabado y este es el último mensaje
	                if (leidos < MensajeTomaFichero.LONGITUD_MAXIMA)
	                {
	                    mensaje.ultimoMensaje = true;
	                    enviadoUltimo=true;
	                }
	                else
	                    mensaje.ultimoMensaje = false;
	                
	                // Se envía por el socket
	                oos.writeObject(mensaje);
	                
	                // Si es el último mensaje, salimos del bucle.
	                if (mensaje.ultimoMensaje)
	                    break;
	                
	                // Se crea un nuevo mensaje
	                mensaje = new MensajeTomaFichero();
	                mensaje.nombreFichero = fichero;
	                
	                // y se leen sus bytes.
	                leidos = fis.read(mensaje.contenidoFichero);
	            }
	            
	            if (enviadoUltimo==false)
	            {
	                mensaje.ultimoMensaje=true;
	                mensaje.bytesValidos=0;
	                oos.writeObject(mensaje);
	            }
	            // Se cierra el ObjectOutputStream
	            oos.close();
	        } catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }





	 
	 
	 
}
