package ar.com.airdrop.Escaner;

import java.util.LinkedList;
import java.util.StringTokenizer;

import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.dominio.Mensaje;
import ar.com.airdrop.dominio.Pc;
import ar.com.airdrop.exceptions.EnviarSocketException;
import ar.com.airdrop.services.EnvioService;
import ar.com.airdrop.services.PcService;

public class Escanear {

	private static LinkedList<Pc> pcs = new LinkedList<Pc>();
	
	private PcService pcService = (PcService) SpringContext.getContext()
			.getBean("pcService");
	private EnvioService envioService = (EnvioService) SpringContext
			.getContext().getBean("envioService");

	public void inicioEscanner() throws InterruptedException {

		String ipAEscanear = limpiarIp(pcService.obtenerIpLocal());
		
		

		
		
		for (int i = 0; i < 255; i++) {

			String serverHostName = ipAEscanear + i;
			ThreadEscanner threadTestIp = new ThreadEscanner(serverHostName,
					pcs);
			threadTestIp.start();

		}
		
		
		

		Thread.sleep(7000);

		for (Pc pc : pcs) {
			if (!pc.getIp().equals(pcService.obtenerIpLocal())) {

				Mensaje mensaje = new Mensaje(pcService.getPcLocal());
				mensaje.setIpDestino(pc.getIp());
				mensaje.setComando("who");

				try {
					envioService.enviarMensaje(mensaje);
				} catch (EnviarSocketException e) {
					e.printStackTrace();
				}
			} else {

				System.out.println("omitiendo localhost...");

			}

		}

	}

	public String limpiarIp(String ip) {

		String ipLimpia = "";
		StringTokenizer token = new StringTokenizer(ip, ".");

		for (int i = 0; i < 3; i++) {

			ipLimpia += token.nextToken() + ".";

		}

		return ipLimpia;

	}

	public PcService getPcService() {
		return pcService;
	}

	public void setPcService(PcService pcService) {
		this.pcService = pcService;
	}

	public EnvioService getEnvioService() {
		return envioService;
	}

	public void setEnvioService(EnvioService envioService) {
		this.envioService = envioService;
	}

}
