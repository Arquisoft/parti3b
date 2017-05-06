package es.uniovi.asw.business;

import java.util.List;

import es.uniovi.asw.model.Administrador;
import es.uniovi.asw.model.Categoria;
import es.uniovi.asw.model.Citizen;
import es.uniovi.asw.model.Sugerencia;
import es.uniovi.asw.model.exception.BusinessException;

public interface SystemService {
	
	public List<Categoria> findAllCategories() throws BusinessException;
	
	public Categoria findCategoriaById(Long id) throws BusinessException;
	
	public List<Sugerencia> findAllSugerencias() throws BusinessException;
	
	public Sugerencia findSugerenciaById(Long id) throws BusinessException;
	
	public List<Sugerencia> findSugerenciasByCategory(Long idCategory) throws BusinessException;
	
	public Citizen findCitizenByUserAndPass(String usuario, String password) throws BusinessException;

	public Administrador findAdminByUserAndPass(String usuario, String password) throws BusinessException;

	public List<Sugerencia> findSugerenciasByUserId(Long id) throws BusinessException;

	public Citizen getParticipant(String usuario, String password) throws BusinessException;
}
