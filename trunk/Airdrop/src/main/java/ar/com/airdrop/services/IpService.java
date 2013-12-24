package ar.com.airdrop.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ar.com.airdrop.dominio.Pc;
import ar.com.airdrop.exceptions.ServiceException;


public class IpService {

	private String hostAddress ;
	private String hostName ;
	
	public Pc obtenerIp() throws ServiceException{
		if (hostAddress == null){
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {

			throw new ServiceException("Error al obtener la ip" ,e1);
		}
		hostAddress = localHost.getHostAddress();
		hostName = localHost.getHostName();
		}
		Pc pc = new Pc(hostAddress);
		pc.setNombreEquipo(hostName);
		return pc;
		
	}
	
	public String obtenerNombrePc() throws ServiceException{
		return hostName;
	}
	
	
	
	
	
	
	
}
