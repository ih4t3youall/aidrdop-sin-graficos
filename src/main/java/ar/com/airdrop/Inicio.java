package ar.com.airdrop;

import java.io.IOException;

import javax.swing.JOptionPane;

import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.dominio.Pc;
import ar.com.airdrop.exceptions.ServiceException;
import ar.com.airdrop.services.IpService;
import ar.com.airdrop.services.PcService;
import ar.com.airdrop.services.RecepcionService;
import ar.com.airdrop.vistas.MenuPrincipal;

public class Inicio {

	private static IpService ipService = (IpService) SpringContext.getContext()
			.getBean("ipService");
	private RecepcionService recepcionService = (RecepcionService) SpringContext
			.getContext().getBean("recepcionService");
	private PcService pcService = (PcService) SpringContext
			.getContext().getBean("pcService");
	
	

	public Inicio() {
		Pc pc = null;
		try {
			pc = ipService.obtenerIp();
			if (pcService.obtenerIpLocal().equals("0")){
			pcService.setIpLocalhost(pc.getIp());
			}
			pcService.setNombreLocal(pc.getNombreEquipo());

			
		} catch (ServiceException e) {
			JOptionPane
					.showMessageDialog(null,
							"Error al obtener la ip local, verifique su conexion a internet");
			System.exit(0);
		}
			
		
		recepcionService.iniciarServerSocketObjetos();
		
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

	public RecepcionService getRecepcionService() {
		return recepcionService;
	}

	public void setRecepcionService(RecepcionService recepcionService) {
		this.recepcionService = recepcionService;
	}

}
