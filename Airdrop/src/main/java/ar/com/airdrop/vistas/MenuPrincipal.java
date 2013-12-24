package ar.com.airdrop.vistas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import ar.com.airdrop.Escaner.Escanear;
import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.dominio.Mensaje;
import ar.com.airdrop.dominio.Pc;
import ar.com.airdrop.exceptions.EnviarSocketException;
import ar.com.airdrop.persistencia.Persistencia;
import ar.com.airdrop.services.EnvioService;
import ar.com.airdrop.services.PcService;

public class MenuPrincipal {

	private static PcService pcService = (PcService) SpringContext.getContext()
			.getBean("pcService");

	private static EnvioService envioService = (EnvioService) SpringContext
			.getContext().getBean("envioService");

	public MenuPrincipal() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {

			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("Seleccione...");
			System.out.println("1) cambiar mi ip");
			System.out.println("2) enviar mensaje");
			System.out.println("3) Ver mi ip");
			System.out.println("4) ver pc externas");
			System.out.println("5) Enviar comando");
			System.out.println("6) Guardar configuracion");
			System.out.println("7) Ingresar ip");
			System.out.println("8) Escanear...");
			System.out.println("9) salir");

			int opcion = 0;

			try {
				opcion = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Debe Escribir un numero");

			}

			switch (opcion) {
			case 1:
				System.out.println("selecciono cambiar la ip");
				System.out.println("ingrese nueva ip");
				String local = br.readLine();
				pcService.getPcLocal().setIp(local);
				System.out
						.println("ingrese nuevo nombre si no desea cambiarlo presione enter");
				local = br.readLine();

				if (!local.equals("")) {
					pcService.getPcLocal().setNombreEquipo(local);
				}

				System.out
						.println("su configuracion ah cambiado, sus nuevos datos son...");
				System.out.println("ip: " + pcService.getPcLocal().getIp());
				System.out.println("nombre: "
						+ pcService.getPcLocal().getNombreEquipo());

				break;
			case 2:

				LinkedList<Pc> obtenerListaPcExternas = pcService
						.obtenerListaPcExternas();

				for (Pc pcExterna : obtenerListaPcExternas) {

					System.out.println(pcExterna.getIp());

				}

				System.out.println("Ingrese el ip de la pc");
				String ipEnviar = br.readLine();
				boolean flag = true;
				for (Pc pcExterna : obtenerListaPcExternas) {

					if (ipEnviar.equals(pcExterna.getIp())) {
						flag = true;

					}

				}
				Pc pcEnviar = null;
				if (flag) {
					pcEnviar = new Pc(ipEnviar);
					Mensaje mensaje = new Mensaje(pcEnviar);
					mensaje.setComando("mensajePrompt");
					System.out.println("ingrese mensaje a enviar");
					String mensajeString = br.readLine();
					mensaje.setMensaje(mensajeString);
					mensaje.setIpDestino(ipEnviar);
					try {
						envioService.enviarMensaje(mensaje);
					} catch (EnviarSocketException e) {
						System.out.println("error al enviar el mensaje");
					}
				} else {
					System.out.println("el ip no existe");
				}

				break;
			case 3:
				System.out.println("La ip local es:");
				System.out.println("ip: " + pcService.getPcLocal().getIp());
				System.out.println("nombre: "
						+ pcService.getPcLocal().getNombreEquipo());
				break;

			case 4:

				System.out.println("lista pcs");
				obtenerListaPcExternas = pcService.obtenerListaPcExternas();

				for (Pc pcExterna : obtenerListaPcExternas) {

					System.out.println(pcExterna.getIp());

				}
				break;
			case 5:
				obtenerListaPcExternas = pcService.obtenerListaPcExternas();

				for (Pc pcExterna : obtenerListaPcExternas) {

					System.out.println(pcExterna.getIp());

				}

				System.out.println("Ingrese el ip de la pc");
				ipEnviar = br.readLine();
				flag = false;
				for (Pc pcExterna : obtenerListaPcExternas) {

					if (ipEnviar.equals(pcExterna.getIp())) {
						flag = true;

					}

				}
				pcEnviar = null;
				if (flag) {
					pcEnviar = new Pc(ipEnviar);

					System.out.println("Ingrese el comando a enviar");
					String mensajeString = br.readLine();

					Mensaje mensaje = new Mensaje(pcEnviar);
					mensaje.setIpDestino(pcEnviar.getIp());
					mensaje.setComando("bash");
					mensaje.setMensaje(mensajeString);

					while (true) {
						System.out
								.println("con respuesta?responder true/false");
						String conRespuesta = br.readLine();

						if (conRespuesta.equals("true")) {
							mensaje.setRespuesta(true);
							break;
						}

						if (conRespuesta.equals("false")) {
							mensaje.setRespuesta(false);
							break;
						}

					}

					try {
						envioService.enviarMensaje(mensaje);
					} catch (EnviarSocketException e) {
						System.out.println("error al enviar el mensaje");
					}
				} else {
					System.out.println("el ip no existe");
				}

				break;
			case 6:
				Persistencia persistencia = new Persistencia();
				persistencia.Guardar(pcService);
				break;

			case 7:
				System.out.println("ingrese ip externo");
				String nuevaIp = br.readLine();
				Mensaje mensaje = new Mensaje(new Pc(nuevaIp));
				mensaje.setIpDestino(nuevaIp);
				mensaje.setComando("who");

				try {
					envioService.enviarMensaje(mensaje);
				} catch (EnviarSocketException e) {
					System.out.println("error al conectarse con la otra pc");
				}

				break;
			case 8:
				try {
					new Escanear().inicioEscanner();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 9:
				System.out.println("terminando...");
				System.exit(0);
			default:
				System.out.println("no existe en el menu");
				break;
			}
		}
	}


}
