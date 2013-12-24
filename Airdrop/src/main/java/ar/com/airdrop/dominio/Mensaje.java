package ar.com.airdrop.dominio;

import java.io.File;
import java.io.Serializable;

public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;
	private Pc pc;
	private String comando;
	private String nombreArchivo;
	private File file;
	private long tamanioFile;
	private String mensaje;
	private String ipDestino;
	private boolean respuesta;

	public Mensaje(Pc pc) {

		this.pc = pc;

	}

	public boolean isRespuesta() {
		return respuesta;
	}

	public void setRespuesta(boolean respuesta) {
		this.respuesta = respuesta;
	}

	public Pc getPc() {
		return pc;
	}

	public void setPc(Pc pc) {
		this.pc = pc;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public long getTamanioFile() {
		return tamanioFile;
	}

	public void setTamanioFile(long tamanioFile) {
		this.tamanioFile = tamanioFile;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIpDestino() {
		return ipDestino;
	}

	public void setIpDestino(String ipDestino) {
		this.ipDestino = ipDestino;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

}
