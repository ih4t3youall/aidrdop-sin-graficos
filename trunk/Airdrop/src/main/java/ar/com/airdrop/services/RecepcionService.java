package ar.com.airdrop.services;

import ar.com.airdrop.exceptions.RecibirServerSocketException;
import ar.com.airdrop.threads.RecibirArchivo;
import ar.com.airdrop.threads.RecibirMensaje;

public class RecepcionService {

	
	public void iniciarServerSocketObjetos(){
		
		RecibirMensaje recibirMensaje;
		try {
			recibirMensaje = new RecibirMensaje();
		recibirMensaje.start();
		new RecibirArchivo().start();
		} catch (RecibirServerSocketException e) {
			e.printStackTrace();
		}
	}
	
}
