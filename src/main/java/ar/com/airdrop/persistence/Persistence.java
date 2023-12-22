package ar.com.airdrop.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ar.com.airdrop.exceptions.FileNotExist;
import ar.com.airdrop.services.PcService;

public class Persistence {

	

	public void saveRecord(PcService pcService) {
		Object obj = pcService;

		File arch = new File("airdropdata");
		arch.delete();

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("airdropdata");

			ObjectOutputStream obj_out = new ObjectOutputStream(fileOut);

			obj_out.writeObject(obj);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadRecord(PcService pcService) throws FileNotExist {

		File f = new File("airdropdata");
		if(f.exists()){
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("airdropdata"));

			Object aux = ois.readObject();

			PcService service = (PcService) aux;
			pcService.setExternalPc(service.getListExternalPc());
			pcService.setIpLocalhost(service.obtenerIpLocal());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		}else {
			throw new FileNotExist("el archivo no existe");
		}

	}
}
