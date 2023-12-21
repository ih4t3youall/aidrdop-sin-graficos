package ar.com.airdrop.Escaner;

import java.net.Socket;
import java.util.LinkedList;

import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.dominio.Pc;
import ar.com.airdrop.services.PcService;

public class ThreadEscanerPuertos extends Thread {

	private Pc pc = null;
	
	private PcService pcService = (PcService)SpringContext.getContext().getBean("pcService");
	
	private LinkedList<Pc> filtradas;
	
	public ThreadEscanerPuertos(Pc pc, LinkedList<Pc> filtradas){
		
		this.filtradas = filtradas; 
		this.pc = pc;
		
		
		
	}
	
	
	
	public void run(){
		
		try {
			Socket socket = new Socket(pc.getIp(),8123);
			System.out.println("el puerto esta abierto "+pc.getIp());
			filtradas.add(pc);
		} catch (Exception e) {
			System.out.println("error al verificar el puerto de : "+pc.getIp());
			
		}
		
		}



	public PcService getPcService() {
		return pcService;
	}



	public void setPcService(PcService pcService) {
		this.pcService = pcService;
	}
		
	
	
	
	}
	

