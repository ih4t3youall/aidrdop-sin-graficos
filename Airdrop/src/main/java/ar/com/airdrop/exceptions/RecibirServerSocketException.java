package ar.com.airdrop.exceptions;

public class RecibirServerSocketException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecibirServerSocketException(Exception e ,String ip ,String mensaje){
		
		
		
		System.out.println(mensaje+ip+e.getCause());
		
		
		
		
	}
	
	

}
