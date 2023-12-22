package ar.com.airdrop.services;

import ar.com.airdrop.domine.Message;

public class FileService {
	
	
	private Message message;
	
	private String directorioSalvado="";
	

	public void archivoAEnviar(Message message){
		
		this.message = message;
		
	}
	
	public Message obtenerArchivoAEviar(){
		
		return message;
		
	}
	
	public String obtenerNombreArchivo(){
		
		return message.getFile().getName();
		
	}

	public void archivoARecibir(Message message) {

		this.message = message;
	}
	
	
	public void setDirectorioSalvado(String directorioSalvado){
		
		this.directorioSalvado = directorioSalvado;
		
	}
	
	public String getDirectorioSalvado(){
		
		return this.directorioSalvado;
		
	}
	
	

	

}
