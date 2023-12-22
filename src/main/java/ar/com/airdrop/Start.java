package ar.com.airdrop;

import java.io.IOException;

import javax.swing.JOptionPane;

import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.domine.Pc;
import ar.com.airdrop.exceptions.ServiceException;
import ar.com.airdrop.services.IpService;
import ar.com.airdrop.services.PcService;
import ar.com.airdrop.services.ReceptionService;
import ar.com.airdrop.views.MenuPrincipal;

public class Start {

	private static IpService ipService = (IpService) SpringContext.getContext()
			.getBean("ipService");
	private ReceptionService receptionService = (ReceptionService) SpringContext
			.getContext().getBean("recepcionService");
	private PcService pcService = (PcService) SpringContext
			.getContext().getBean("pcService");
	
	

	public Start() {
		Pc pc = null;
		try {
			pc = ipService.obtenerIp();
			if (pcService.obtenerIpLocal().equals("0")){
				pcService.setIpLocalhost(pc.getIp());
			}
			pcService.setNombreLocal(pc.getPcName());

			
		} catch (ServiceException e) {
			JOptionPane
					.showMessageDialog(null,
							"Error al obtener la ip local, verifique su conexion a internet");
			System.exit(0);
		}
			
		
		receptionService.startServerSocketObjects();
		
		try {
			MenuPrincipal menuPrincipal = new MenuPrincipal();
		} catch (IOException e) {
			
			System.out.println("error jodete");
		}


	}

	public IpService getIpService() {
		return ipService;
	}

	public void setIpService(IpService ipService) {
		this.ipService = ipService;
	}

	public ReceptionService getReceptionService() {
		return receptionService;
	}

	public void setReceptionService(ReceptionService recepcionService) {
		this.receptionService = recepcionService;
	}

}
