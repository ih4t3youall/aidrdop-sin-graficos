package ar.com.airdrop.services;

import java.io.FileInputStream;
import java.io.ObjectOutputStream;

import ar.com.airdrop.domine.Message;
import ar.com.airdrop.domine.GetFileMessage;
import ar.com.airdrop.exceptions.SendThroughtSocketException;

public class SendService {

	
//	private static int PUERTO = 8123;



	public void sendMessage(Message message) throws SendThroughtSocketException {
		SendMessage sendMessage = new SendMessage(message);
		sendMessage.start();
	}
	
	
	
	 public void sendFile(String file, ObjectOutputStream oos)
	    {
		 
		 
		 
	        try
	        {
	            boolean lastSent=false;
	            // Se abre el file.
	            FileInputStream fis = new FileInputStream(file);
	            
	            // Se instancia y rellena un message de envio de file
	            GetFileMessage message = new GetFileMessage();
	            message.fileName = file;
	            
	            // Se leen los primeros bytes del file en un campo del message
	            int read = fis.read(message.fileContent);
	            
	            // Bucle mientras se vayan leyendo datos del file
	            while (read > -1)
	            {
	                
	                // Se rellena el numero de bytes leidos
	                message.validBytes = read;
	                
	                // Si no se han leido el maximo de bytes, es porque el file
	                // se ha acabado y este es el ultimo message
	                if (read < GetFileMessage.MAX_LENGHT)
	                {
	                    message.lastMessage = true;
	                    lastSent=true;
	                }
	                else
	                    message.lastMessage = false;
	                
	                // Se envia por el socket
	                oos.writeObject(message);
	                
	                // Si es el ultimo message, salimos del bucle.
	                if (message.lastMessage)
	                    break;
	                
	                // Se crea un nuevo message
	                message = new GetFileMessage();
	                message.fileName = file;
	                
	                // y se leen sus bytes.
	                read = fis.read(message.fileContent);
	            }
	            
	            if (lastSent==false)
	            {
	                message.lastMessage =true;
	                message.validBytes =0;
	                oos.writeObject(message);
	            }
	            // Se cierra el ObjectOutputStream
	            oos.close();
	        } catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }





	 
	 
	 
}
