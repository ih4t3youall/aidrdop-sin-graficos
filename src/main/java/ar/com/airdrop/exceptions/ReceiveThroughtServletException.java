package ar.com.airdrop.exceptions;

public class ReceiveThroughtServletException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReceiveThroughtServletException(Exception e , String ip , String message){
		System.out.println(message+ip+e.getCause());
	}

}
