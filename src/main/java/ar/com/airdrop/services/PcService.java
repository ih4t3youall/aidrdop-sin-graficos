package ar.com.airdrop.services;

import java.io.Serializable;
import java.util.LinkedList;

import ar.com.airdrop.domine.Pc;
import ar.com.airdrop.exceptions.FileNotExist;
import ar.com.airdrop.persistence.Persistence;

public class PcService implements Serializable{

	private Pc pcLocal = new Pc("0");
	private LinkedList<Pc> externalPc = new LinkedList<Pc>();

	
	public PcService(){
		Persistence persistence = new Persistence();
		//se fija si hay configuraciones y las carga
		
			try {
				persistence.loadRecord(this);
			} catch (FileNotExist e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		
	}
	
	
	public void setExternalPc(LinkedList<Pc> externalPc){
		
		this.externalPc = externalPc;
		
	}
	
	
	public Pc getPcLocal() {

		
		
		return pcLocal;
	}

	public void setIpLocalhost(String ip) {

		pcLocal.setIp(ip);

	}

	public void setNombreLocal(String nombre) {

		pcLocal.setPcName(nombre);

	}

	public String obtenerIpLocal() {
		return this.pcLocal.getIp();
	}

	public String getLocalPcName() {
		return this.pcLocal.getPcName();
	}

	public void addExternalPc(Pc pc) {

		boolean f = true;
		for (Pc iterPc : externalPc) {

			if (iterPc.getIp().equals(pc.getIp())) {
				//para actualizar cuando cambia por archivo->Editar local, asi se actualiza la lista.
				iterPc.setPcName(pc.getPcName());
				f = false;
			}

		}

		if (f) {

			this.externalPc.add(pc);
		}
	}

	public LinkedList<Pc> getListExternalPc() {
		return this.externalPc;
	}

	public void cleanList() {
		externalPc.clear();
	}

}
