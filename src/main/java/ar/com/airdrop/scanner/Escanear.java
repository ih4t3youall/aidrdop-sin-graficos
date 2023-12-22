package ar.com.airdrop.scanner;

import java.util.LinkedList;
import java.util.StringTokenizer;

import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.domine.Message;
import ar.com.airdrop.domine.Pc;
import ar.com.airdrop.exceptions.SendThroughtSocketException;
import ar.com.airdrop.services.SendService;
import ar.com.airdrop.services.PcService;

public class Escanear {

	private static LinkedList<Pc> pcs = new LinkedList<Pc>();
	
	private PcService pcService = (PcService) SpringContext.getContext()
			.getBean("pcService");
	private SendService sendService = (SendService) SpringContext
			.getContext().getBean("sendService");

	public void startScanning() throws InterruptedException {

		String ipToScan = cleanIp(pcService.obtenerIpLocal());

		for (int i = 0; i < 255; i++) {

			String serverHostName = ipToScan + i;
			ThreadScanner threadTestIp = new ThreadScanner(serverHostName,
					pcs);
			threadTestIp.start();
		}

		Thread.sleep(7000);

		for (Pc pc : pcs) {
			if (!pc.getIp().equals(pcService.obtenerIpLocal())) {

				Message message = new Message(pcService.getPcLocal());
				message.setDestinationIp(pc.getIp());
				message.setCommand("who");

				try {
					sendService.sendMessage(message);
				} catch (SendThroughtSocketException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("omitiendo localhost...");
			}

		}

	}

	public String cleanIp(String ip) {
		String cleanIp = "";
		StringTokenizer token = new StringTokenizer(ip, ".");

		for (int i = 0; i < 3; i++) {
			cleanIp += token.nextToken() + ".";
		}
		return cleanIp;
	}

	public PcService getPcService() {
		return pcService;
	}

	public void setPcService(PcService pcService) {
		this.pcService = pcService;
	}

	public SendService getSendService() {
		return sendService;
	}

	public void setSendService(SendService sendService) {
		this.sendService = sendService;
	}

}
