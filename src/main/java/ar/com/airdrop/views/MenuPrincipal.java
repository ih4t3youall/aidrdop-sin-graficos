package ar.com.airdrop.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import ar.com.airdrop.scanner.Escanear;
import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.domine.Message;
import ar.com.airdrop.domine.Pc;
import ar.com.airdrop.exceptions.SendThroughtSocketException;
import ar.com.airdrop.persistence.Persistence;
import ar.com.airdrop.services.SendService;
import ar.com.airdrop.services.PcService;

public class MenuPrincipal {

	private static PcService pcService = (PcService) SpringContext.getContext()
			.getBean("pcService");

	private static SendService sendService = (SendService) SpringContext
			.getContext().getBean("sendService");

	public MenuPrincipal() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {

			System.out.println("");
			System.out.println("");
			System.out.println("Choose...");
			System.out.println("1) Change my ip");
			System.out.println("2) Send Message");
			System.out.println("3) See my Ip");
			System.out.println("4) See external Pc");
			System.out.println("5) Send command");
			System.out.println("6) Save configuration");
			System.out.println("7) Insert ip");
			System.out.println("8) Scan...");
			System.out.println("9) Exit");

			int opcion = 0;

			try {
				opcion = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
				System.out.println("You need to type a number");

			}

			switch (opcion) {
			case 1:
				System.out.println("Change Ip");
				System.out.println("Insert new Ip");
				String local = br.readLine();
				pcService.getPcLocal().setIp(local);
				System.out
						.println("You can pick a new neme, if not just press enter");
				local = br.readLine();

				if (!local.equals("")) {
					pcService.getPcLocal().setPcName(local);
				}

				System.out
						.println("Your configuration change, these are your new credentials...");
				System.out.println("Ip: " + pcService.getPcLocal().getIp());
				System.out.println("Name: "
						+ pcService.getPcLocal().getPcName());

				break;
			case 2:

				LinkedList<Pc> obtainExternalPcList = pcService
						.getListExternalPc();

				for (Pc pcExterna : obtainExternalPcList) {

					System.out.println(pcExterna.getIp());

				}

				System.out.println("Inser the new Ip");
				String ipToSend = br.readLine();
				boolean flag = true;
				for (Pc pcExterna : obtainExternalPcList) {
					if (ipToSend.equals(pcExterna.getIp())) {
						flag = true;
					}
				}
				Pc sendPc = null;
				if (flag) {
					sendPc = new Pc(ipToSend);
					Message message = new Message(sendPc);
					message.setCommand("mensajePrompt");
					System.out.println("Write message to send.");
					String mensajeString = br.readLine();
					message.setMessage(mensajeString);
					message.setDestinationIp(ipToSend);
					try {
						sendService.sendMessage(message);
					} catch (SendThroughtSocketException e) {
						System.out.println("Error sending the message");
					}
				} else {
					System.out.println("The ip does not exists");
				}

				break;
			case 3:
				System.out.println("Local ip is: ");
				System.out.println("ip: " + pcService.getPcLocal().getIp());
				System.out.println("Name: "
						+ pcService.getPcLocal().getPcName());
				break;

			case 4:

				System.out.println("List pcs");
				obtainExternalPcList = pcService.getListExternalPc();

				for (Pc pcExterna : obtainExternalPcList) {

					System.out.println(pcExterna.getIp());

				}
				break;
			case 5:
				obtainExternalPcList = pcService.getListExternalPc();

				for (Pc externalPc : obtainExternalPcList) {

					System.out.println(externalPc.getIp());

				}

				System.out.println("Insert the pc Ip");
				ipToSend = br.readLine();
				flag = false;
				for (Pc pcExterna : obtainExternalPcList) {

					if (ipToSend.equals(pcExterna.getIp())) {
						flag = true;

					}

				}
				sendPc = null;
				if (flag) {
					sendPc = new Pc(ipToSend);

					System.out.println("Insert command to send");
					String mensajeString = br.readLine();

					Message message = new Message(sendPc);
					message.setDestinationIp(sendPc.getIp());
					message.setCommand("bash");
					message.setMessage(mensajeString);

					while (true) {
						System.out
								.println("whit answer? true/false");
						String conRespuesta = br.readLine();

						if (conRespuesta.equals("true")) {
							message.setResponse(true);
							break;
						}

						if (conRespuesta.equals("false")) {
							message.setResponse(false);
							break;
						}

					}

					try {
						sendService.sendMessage(message);
					} catch (SendThroughtSocketException e) {
						System.out.println("Error when sending the message.");
					}
				} else {
					System.out.println("The ip does not exist.");
				}

				break;
			case 6:
				Persistence persistence = new Persistence();
				persistence.saveRecord(pcService);
				break;

			case 7:
				System.out.println("insert external ip");
				String newIp = br.readLine();
				Message message = new Message(new Pc(newIp));
				message.setDestinationIp(newIp);
				message.setCommand("who");

				try {
					sendService.sendMessage(message);
				} catch (SendThroughtSocketException e) {
					System.out.println("Error whit the handshake");
				}

				break;
			case 8:
				try {
					new Escanear().startScanning();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 9:
				System.out.println("Finishing...");
				System.exit(0);
			default:
				System.out.println("Option does not exist.");
				break;
			}
		}
	}


}
