package ar.com.airdrop.services;

import java.io.Serializable;
import java.util.LinkedList;

import ar.com.airdrop.dominio.Pc;
import ar.com.airdrop.exceptions.ArchivoNoExisteException;
import ar.com.airdrop.persistencia.Persistencia;

public class PcService implements Serializable{

	private Pc pcLocal = new Pc("0");
	private LinkedList<Pc> pcExternas = new LinkedList<Pc>();

	
	public PcService(){
		Persistencia persistencia = new Persistencia();
		//se fija si hay configuraciones y las carga
		
			try {
				persistencia.recuperarGuardado(this);
			} catch (ArchivoNoExisteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}
	
	
	public void setPcExternas(LinkedList<Pc> pcExternas){
		
		this.pcExternas = pcExternas;
		
	}
	
	
	public Pc getPcLocal() {

		
		
		return pcLocal;
	}

	public void setIpLocalhost(String ip) {

		pcLocal.setIp(ip);

	}

	public void setNombreLocal(String nombre) {

		pcLocal.setNombreEquipo(nombre);

	}

	public String obtenerIpLocal() {
		return this.pcLocal.getIp();
	}

	public String obtenerNombrePcLocal() {
		return this.pcLocal.getNombreEquipo();
	}

	public void addPcExterna(Pc pc) {

		boolean f = true;
		for (Pc iterPc : pcExternas) {

			if (iterPc.getIp().equals(pc.getIp())) {
				//para actualizar cuando cambia por archivo->Editar local, asi se actualiza la lista.
				iterPc.setNombreEquipo(pc.getNombreEquipo());
				f = false;
			}

		}

		if (f) {

			this.pcExternas.add(pc);
		}
	}

	public LinkedList<Pc> obtenerListaPcExternas() {
		return this.pcExternas;
	}

	public void limpiarLista() {
		pcExternas.clear();
	}

}
