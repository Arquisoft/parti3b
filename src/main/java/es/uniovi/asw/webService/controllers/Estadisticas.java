package es.uniovi.asw.webService.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;

import org.codehaus.groovy.control.messages.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.uniovi.asw.business.Services;
import es.uniovi.asw.model.*;
import es.uniovi.asw.model.exception.BusinessException;

@Controller
@ManagedBean(value="estadisticas")
public class Estadisticas {

	@Autowired
	private SimpMessagingTemplate template;
	
	public List<Sugerencia> sugerencias = new ArrayList<Sugerencia>();
	
	


	public List<String> mensajes = new ArrayList<String>();

	public Estadisticas() throws BusinessException{
		crearCategoriaEjemplo();
	//cargarSugerencias();
		añadirMensaje("Mensajes: ");
	}
	
	public void añadirSugerencia(Sugerencia sug)
	{
		sugerencias.add(sug);
	}
	
	@MessageMapping("wsocket")
	@SendTo("/topic/sugerencias")
	private List<Sugerencia> cargarSugerencias() throws BusinessException {
		
		sugerencias.addAll(Services.getSystemServices().findAllSugerencias());
		return sugerencias;
	}
	
	
	public List<Sugerencia> getTopSugerencias() {
		return sugerencias;
	}

	public void setTopSugerencias(ArrayList<Sugerencia> topSugerencias) {
		this.sugerencias = topSugerencias;
	}
	
	private void crearCategoriaEjemplo(){
		Citizen testCitizen= new Citizen("nombre" + sugerencias.size(), "apellidos","testemail" +sugerencias.size()+ "@email",new Date() , sugerencias.size()+"direccion",
				"nacionalidad","DNI", "usuario"+sugerencias.size(), "password");
		Categoria testCategoria = new Categoria("testCategoria",new Date() ,new Date() , 2);
		Sugerencia testSugerencia = new Sugerencia(testCitizen,"titulo", "contenido", testCategoria);
		VotoSugerencia testVotoSugerencia = new VotoSugerencia(testSugerencia, testCitizen, true);
		testSugerencia.getVotos().add(testVotoSugerencia);
		//testSugerencia.setId(Long.parseLong(String.valueOf(topSugerencias.size())));
		sugerencias.add(testSugerencia);

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
	
	/**
	 * Metodo que envia informacion a la consola visible por el administrador desde el dashboard
	 * @param msg: mensaje a mostrar 
	 */
	public void sendToAdminConsole(String msg) {
        try {
	
        	template.convertAndSend("/topic/mensajes", new ObjectMapper().writeValueAsString(new SimpleAdminMessage(msg)));
        	System.out.println(msg);
    
        } catch (MessagingException e) {
        	
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
      
	}
	
	
	public void limpiar(){
		añadirMensaje("Mensajes: ");
		mensajes.clear();
		
	}
	public List<Sugerencia> getSugerencias() {
		return sugerencias;
	}

	public void setSugerencias(List<Sugerencia> sugerencias) {
		this.sugerencias = sugerencias;
	}
}

class SimpleAdminMessage{
	String message;
	public SimpleAdminMessage(String msg){
		message=msg;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

