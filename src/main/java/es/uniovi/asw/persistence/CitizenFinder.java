package es.uniovi.asw.persistence;

import java.util.List;

import es.uniovi.asw.model.Citizen;
import es.uniovi.asw.persistence.util.Jpa;
import es.uniovi.asw.util.Encriptador;

public class CitizenFinder {

	public static Citizen findByDNI(String dni) {
		return (Citizen) Jpa.getManager().createNamedQuery("Citizen.findByDNI", Citizen.class).setParameter(1, dni)
				.getResultList().stream().findFirst().orElse(null);
	}

	public static List<Citizen> findAll() {
		return Jpa.getManager().createNamedQuery("Citizen.findAll", Citizen.class).getResultList();
	}

	public static Object findByUserAndPass(String user, String pass) {
			List<Citizen> result= Jpa.getManager().createNamedQuery("Citizen.findByUserAndPass", Citizen.class).
				setParameter(1, user).setParameter(2, pass).getResultList();
		
		if (result.isEmpty())
			return null;

		return result.get(0);
	}
}
