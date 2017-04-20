package es.uniovi.asw.persistence;

import java.util.List;

import es.uniovi.asw.model.Administrador;
import es.uniovi.asw.persistence.util.Jpa;
import es.uniovi.asw.util.Encriptador;

public class AdministradorFinder {
	
	public static Object findByUserAndPass(String user, String pass) {
		List<Administrador> admin = Jpa.getManager().createNamedQuery("Admin.findByUserAndPass", Administrador.class).
				setParameter(1, user).setParameter(2, Encriptador.encriptar(pass)).getResultList();
		if(!admin.isEmpty()){
			return admin.get(0);
		}
		else return null;
	}

}
