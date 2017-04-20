package es.uniovi.asw.business.impl.citizen;

import es.uniovi.asw.business.impl.Command;
import es.uniovi.asw.model.Citizen;
import es.uniovi.asw.model.exception.BusinessException;
import es.uniovi.asw.persistence.util.Jpa;

public class UpdateCitizen implements Command{
	private Citizen ciudadano;
	
	public UpdateCitizen(Citizen citizen) {
		this.ciudadano= citizen;
	}

	@Override
	public Object execute() throws BusinessException {
		Jpa.getManager().merge(ciudadano);
		return ciudadano;
	}
}
