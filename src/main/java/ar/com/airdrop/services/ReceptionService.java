package ar.com.airdrop.services;

import ar.com.airdrop.exceptions.ReceiveThroughtServletException;
import ar.com.airdrop.threads.RecibirArchivo;
import ar.com.airdrop.threads.ReceiveMessage;

public class ReceptionService {

	
	public void startServerSocketObjects(){
		
		ReceiveMessage receiveMessage;
		try {
			receiveMessage = new ReceiveMessage();
		receiveMessage.start();
		new RecibirArchivo().start();
		} catch (ReceiveThroughtServletException e) {
			e.printStackTrace();
		}
	}
	
}
