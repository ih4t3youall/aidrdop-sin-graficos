package ar.com.airdrop.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;

import ar.com.airdrop.constantes.Constantes;
import ar.com.airdrop.context.SpringContext;
import ar.com.airdrop.dominio.Mensaje;
import ar.com.airdrop.dominio.Pc;
import ar.com.airdrop.exceptions.RecibirServerSocketException;
import ar.com.airdrop.services.ArchivoService;
import ar.com.airdrop.services.EnvioService;
import ar.com.airdrop.services.PcService;

public class RecibirMensaje extends Thread {

	private PcService pcService = (PcService) SpringContext.getContext()
			.getBean("pcService");
	private EnvioService envioService = (EnvioService) SpringContext
			.getContext().getBean("envioService");
	private ArchivoService archivoService = (ArchivoService) SpringContext
			.getContext().getBean("archivoService");

	private static int PUERTO = Constantes.PUERTO;

	public RecibirMensaje()
			throws RecibirServerSocketException {

	}

	public void run() {

		while (true) {
			ServerSocket socket = null;

			try {
				socket = new ServerSocket(PUERTO);
			} catch (IOException e) {
				String error = "Error al cear el socket";
				System.out.println(error);
			}
			System.out.println("Esperando envio....");

			try {
				Socket cliente = socket.accept();

				String ipOtroCliente = cliente.getInetAddress()
						.getHostAddress();
				System.out.println("Conectado con cliente de " + ipOtroCliente);

				cliente.setSoLinger(true, 10);

				ObjectInputStream buffer = new ObjectInputStream(
						cliente.getInputStream());

				Mensaje mensaje = (Mensaje) buffer.readObject();

				if (mensaje.getComando().equals("who")) {

					Pc respuestaPc = pcService.getPcLocal();

					pcService.addPcExterna(mensaje.getPc());
					Mensaje mensajeRespuesta = new Mensaje(
							pcService.getPcLocal());
					mensajeRespuesta.setIpDestino(ipOtroCliente);
					mensajeRespuesta.setComando("autenticar");
					mensajeRespuesta.setIpDestino(ipOtroCliente);
					envioService.enviarMensaje(mensajeRespuesta);

				}

				if (mensaje.getComando().equals("archivo")) {

					 JFileChooser jfc = new JFileChooser();
					 jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					 if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
						 archivoService.archivoARecibir(mensaje);
						 archivoService.setDirectorioSalvado(jfc.getSelectedFile().getAbsolutePath());
					 
					 
					Mensaje mensajeRespuesta = new Mensaje(
							pcService.getPcLocal());
					mensajeRespuesta.setIpDestino(ipOtroCliente);
					mensajeRespuesta.setComando("okArchivo");
					mensajeRespuesta.setNombreArchivo(mensaje
							.getNombreArchivo());
					envioService.enviarMensaje(mensajeRespuesta);
					 }

				}
				if (mensaje.getComando().equals("bash")){
					
					Process p = Runtime.getRuntime().exec(mensaje.getMensaje());
					
					if (mensaje.isRespuesta()){
					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
					BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
					String s = null;
					String respuestaComando = "";
					// Leemos la salida del comando y la desplegamos en el jTextArea
					while ((s = stdInput.readLine()) != null ) {
						System.out.println(s);
					respuestaComando +=s+"\n";
					}
					stdInput.close();
					stdError.close();
					
					Mensaje mensajeRespuesta = new Mensaje(
							pcService.getPcLocal());
					mensajeRespuesta.setIpDestino(ipOtroCliente);
					mensajeRespuesta.setComando("respuestaComando");
					mensajeRespuesta.setMensaje(respuestaComando);
					envioService.enviarMensaje(mensajeRespuesta);
					}
					
				}
				
				
				if (mensaje.getComando().equals("respuestaComando")){
					
					System.out.println("++++++++++++++++++++++++++++++++++");
					System.out.println("+++++++respuesta del comando++++++");
					System.out.println(mensaje.getMensaje());
					System.out.println("+++++++fin respuesta del comando++++++");
					System.out.println("++++++++++++++++++++++++++++++++++");
					
				}
				

				if (mensaje.getComando().equals("okArchivo")) {

					Mensaje archivoAEviar = archivoService
							.obtenerArchivoAEviar();

					Socket socketEnviarArch = new Socket(
							archivoAEviar.getIpDestino(),
							Constantes.PUERTO_ARCHIVOS);
					
					envioService.enviaFichero(archivoAEviar.getFile()
							.getAbsolutePath(), new ObjectOutputStream(
							socketEnviarArch.getOutputStream()));

					

				}
				if (mensaje.getComando().equals("autenticar")) {

					System.out.println("Recibi una pc de la ip : "
							+ ipOtroCliente);

					Pc pc = mensaje.getPc();
					pcService.addPcExterna(pc);

				}

				if (mensaje.getComando().equals("mensajePrompt")) {

					System.out.println("************************************************");
					System.out.println("*********Se recibio un mensaje******************");
					System.out.println("************************************************");
					System.out.println(mensaje.getMensaje());
					System.out.println("************************************************");
					System.out.println("*********Fin mensaje****************************");
					System.out.println("************************************************");

				}


			} catch (Exception e) {

				String error = "Error con el serversocket en el puerto : "
						+ PUERTO;
				System.out.println(error);

			} finally {

				try {
					if (socket != null) {
						socket.close();
					}
				} catch (IOException e) {
					System.err.println("Error al cerrar el serversocket.");

				}

			}
		}

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
