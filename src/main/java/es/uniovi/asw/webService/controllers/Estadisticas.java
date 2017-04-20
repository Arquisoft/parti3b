package es.uniovi.asw.webService.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.context.annotation.Scope;

import es.uniovi.asw.model.Categoria;
import es.uniovi.asw.model.Citizen;
import es.uniovi.asw.model.Sugerencia;
import es.uniovi.asw.model.VotoSugerencia;

@ManagedBean(value="estadisticas")
	 	
public class Estadisticas {

	
	
	public ArrayList<Sugerencia> topSugerencias = new ArrayList<Sugerencia>();
	
	public List<String> mensajes = new ArrayList<String>();

	public Estadisticas(){
		crearCategoriaEjemplo();
		añadirMensaje("Mensajes: ");
	}
	public void añadirSugerencia(Sugerencia sug)
	{
		topSugerencias.add(sug);
	}
	
	public ArrayList<Sugerencia> getTopSugerencias() {
		return topSugerencias;
	}

	public void setTopSugerencias(ArrayList<Sugerencia> topSugerencias) {
		this.topSugerencias = topSugerencias;
	}
	
	private void crearCategoriaEjemplo(){
		Citizen testCitizen= new Citizen("nombre" + topSugerencias.size(), "apellidos","testemail" +topSugerencias.size()+ "@email",new Date() , topSugerencias.size()+"direccion",
				"nacionalidad","DNI", "usuario"+topSugerencias.size(), "password");
		Categoria testCategoria = new Categoria("testCategoria",new Date() ,new Date() , 2);
		Sugerencia testSugerencia = new Sugerencia(testCitizen,"titulo", "contenido", testCategoria);
		VotoSugerencia testVotoSugerencia = new VotoSugerencia(testSugerencia, testCitizen, true);
		testSugerencia.getVotos().add(testVotoSugerencia);
		//testSugerencia.setId(Long.parseLong(String.valueOf(topSugerencias.size())));
		topSugerencias.add(testSugerencia);

//		kafkaProducer.send(Topics.CREATE_SUGGESTION, Message.setMessage(testSugerencia));
	}
	
	
	public List<String> getMensajes() {
		return mensajes;
	}
	public void setMensajes(List<String> mensajes) {
		this.mensajes = mensajes;
	}
	public void añadirMensaje(String str){
		mensajes.add(str);
	}
	
//	@Scheduled(cron = "*/4 * * * * *")
	public void sendProposalMessagesCouncilstaff() {
		crearCategoriaEjemplo();
		añadirMensaje("Añadiendo sugerencia");
	}
	
	
	public void limpiar(){
		añadirMensaje("Mensajes: ");
		mensajes.clear();
		
	}
}
