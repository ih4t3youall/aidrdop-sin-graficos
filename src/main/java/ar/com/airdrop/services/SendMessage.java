package ar.com.airdrop.services;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ar.com.airdrop.domine.Message;
import ar.com.airdrop.exceptions.SendThroughtSocketException;

public class SendMessage extends Thread{
	
	public Message message;
	private static int PUERTO = 8123;
	
	public SendMessage(Message message){
		this.message = message;
	}
	
	public void run(){
		
		Socket socket = null;
		ObjectOutputStream buffer = null;
			
		try {
			//TODO ver que pasa aca que en debian trae localhost en ves de la ip
			//TODO esto tiene que ser un thread
			socket = new Socket(message.getDestinationIp(), PUERTO);

			buffer = new ObjectOutputStream(socket.getOutputStream());

			buffer.writeObject(message);

		} catch (Exception e) {

			String error = "Error con el socket al acceder al puerto : ";
			try {
				throw new SendThroughtSocketException(e, message.getPc().getIp(), error);
			} catch (SendThroughtSocketException e1) {
				e1.printStackTrace();
			}

		} finally {

			if (socket != null) {

				try {
					socket.close();
				} catch (IOException e) {
					String error = "Error al cerrar el socket : ";
					try {
						throw new SendThroughtSocketException(e, message.getPc().getIp(),
								error);
					} catch (SendThroughtSocketException e1) {
						e1.printStackTrace();
					}
				}

			}

		}
		}
		
	}
	


