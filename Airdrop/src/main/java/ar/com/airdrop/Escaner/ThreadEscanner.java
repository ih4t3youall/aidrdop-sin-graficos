package ar.com.airdrop.Escaner;


import java.net.InetAddress;
import java.util.LinkedList;

import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.dominio.Pc;
import ar.com.airdrop.services.PcService;

public class ThreadEscanner extends Thread {
	
	

	private String serverHostName = null; 
//	private LinkedList<Pc> pcs = new LinkedList<Pc>();
	private PcService pcService = (PcService) SpringContext.getContext().getBean("pcService");
	private LinkedList<Pc> pcs = new LinkedList<Pc>();
	
	
	public ThreadEscanner(String serverHosName,LinkedList<Pc> pcs){
		this.serverHostName = serverHosName;
		this.pcs = pcs;
		
	}
	
	 public void run() {
	      

			InetAddress ip;
			try {
		
				
				
				ip = InetAddress.getByName(this.serverHostName);
			
			if (ip.isReachable(4000)){
//				pcService.addPcExterna(new Pc(serverHostName));
				this.pcs.add(new Pc(serverHostName));
				System.out.println("se encontro el host "+serverHostName);
				
			}else {
				
				System.out.println("error con el host: "+serverHostName);
				
			}
		 
			} catch (Exception e) {
				System.out.println("error en los threads"+e.getCause());
				e.printStackTrace();
			}
		 
		 
	   }

	public PcService getPcService() {
		return pcService;
	}

	public void setPcService(PcService pcService) {
		this.pcService = pcService;
	}

	 
	 
	
	

}