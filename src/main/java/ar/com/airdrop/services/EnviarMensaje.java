package ar.com.airdrop.services;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ar.com.airdrop.dominio.Mensaje;
import ar.com.airdrop.exceptions.EnviarSocketException;

public class EnviarMensaje extends Thread{
	
	public Mensaje mensaje;
	private static int PUERTO = 8123;
	
	public EnviarMensaje(Mensaje mensaje){
		this.mensaje = mensaje;
	}
	
	public void run(){
		
		

		Socket socket = null;
		ObjectOutputStream buffer = null;
			
		try {
			//TODO ver que pasa aca que en debian trae localhost en ves de la ip
			//TODO esto tiene que ser un thread
			socket = new Socket(mensaje.getIpDestino(), PUERTO);

			buffer = new ObjectOutputStream(socket.getOutputStream());

			buffer.writeObject(mensaje);

		} catch (Exception e) {

			String error = "Error con el socket al acceder al puerto : ";
			try {
				throw new EnviarSocketException(e, mensaje.getPc().getIp(), error);
			} catch (EnviarSocketException e1) {
				e1.printStackTrace();
			}

		} finally {

			if (socket != null) {

				try {
					socket.close();
				} catch (IOException e) {
					String error = "Error al cerrar el socket : ";
					try {
						throw new EnviarSocketException(e, mensaje.getPc().getIp(),
								error);
					} catch (EnviarSocketException e1) {
						e1.printStackTrace();
					}
				}

			}

		}
		}
		
	}
	


