package ar.com.airdrop.scanner;


import java.net.InetAddress;
import java.util.LinkedList;

import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.domine.Pc;
import ar.com.airdrop.services.PcService;

public class ThreadScanner extends Thread {



	private String serverHostName = null;
	//	private LinkedList<Pc> pcs = new LinkedList<Pc>();
	private PcService pcService = (PcService) SpringContext.getContext().getBean("pcService");
	private LinkedList<Pc> pcs = new LinkedList<Pc>();


	public ThreadScanner(String serverHosName, LinkedList<Pc> pcs){
		this.serverHostName = serverHosName;
		this.pcs = pcs;
	}

	public void run() {

		InetAddress ip;
		try {
			ip = InetAddress.getByName(this.serverHostName);

			if (ip.isReachable(4000)){
				this.pcs.add(new Pc(serverHostName));
				System.out.println("Host not found"+serverHostName);
			}else {
				System.out.println("error with the host: "+serverHostName);
			}

		} catch (Exception e) {
			System.out.println("error on threads"+e.getCause());
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