package ar.com.airdrop.services;

import ar.com.airdrop.dominio.Mensaje;

public class ArchivoService {
	
	
	private Mensaje mensaje;
	
	private String directorioSalvado="";
	

	public void archivoAEnviar(Mensaje mensaje){
		
		this.mensaje = mensaje;
		
	}
	
	public Mensaje obtenerArchivoAEviar(){
		
		return mensaje;
		
	}
	
	public String obtenerNombreArchivo(){
		
		return mensaje.getFile().getName();
		
	}

	public void archivoARecibir(Mensaje mensaje) {

		this.mensaje = mensaje;
	}
	
	
	public void setDirectorioSalvado(String directorioSalvado){
		
		this.directorioSalvado = directorioSalvado;
		
	}
	
	public String getDirectorioSalvado(){
		
		return this.directorioSalvado;
		
	}
	
	

	

}
