package es.uniovi.asw.webService.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import es.uniovi.asw.business.Services;
import es.uniovi.asw.model.*;
import es.uniovi.asw.model.exception.BusinessException;
import es.uniovi.asw.util.clasesSimples.*;
@Controller
@ManagedBean(value="estadisticas")
public class Estadisticas {

	@Autowired
	private SimpMessagingTemplate template;
	
	public List<Sugerencia> sugerencias = new ArrayList<Sugerencia>();
	
	public List<String> mensajes = new ArrayList<String>();

	public Estadisticas() throws BusinessException{
		cargarSugerencias();
	}
	
	public void añadirSugerencia(Sugerencia sug)
	{
		enviarATablaAdmin(sug);
		sugerencias.add(sug);
	}
	
	@MessageMapping("wsocket")
	@SendTo("/topic/sugerencias")
	private List<Sugerencia> cargarSugerencias() throws BusinessException {
		
		sugerencias= Services.getSystemServices().findAllSugerencias();
		return sugerencias;
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
	public void enviarAConsolaAdmin(String msg) {
        try {
	
        	template.convertAndSend("/topic/mensajes", new ObjectMapper().writeValueAsString(new SimpleAdminMessage(msg)));
        	añadirMensaje(msg);
        	System.out.println(msg);
    
        } catch (MessagingException e) {
        	
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
      
	}
	public void enviarVotoATablaAdmin(VotoSugerencia voto){
		 try {
			 SimpleVote sVote = new SimpleVote(voto);
			 for(Sugerencia sug: sugerencias){
				 if(sug.getId()== voto.getSugerencia().getId()) {
					 if(sVote.isaFavor() )
						 sVote.setnPositivos(sug.getPosVotes()+1);
					 else sVote.setnNegativos(sug.getNegVotes()+1);
				 }
				
			 }
	        	template.convertAndSend("/topic/voto", new ObjectMapper().writeValueAsString(sVote));
	        	System.out.println(voto);
	        } catch (MessagingException | JsonProcessingException e) {
	        	
				e.printStackTrace();
			}
	}
	
	public void enviarATablaAdmin(Sugerencia sug) {
        try {
        	template.convertAndSend("/topic/sugerencia", new ObjectMapper().writeValueAsString(new SimpleSugMessage(sug)));
        	System.out.println(sug);
        } catch (MessagingException | JsonProcessingException e) {
        	
			e.printStackTrace();
		}
	}
	
	public void limpiar(){
		añadirMensaje("Mensajes: ");
		mensajes.clear();
		
	}
	public List<Sugerencia> getSugerencias() throws BusinessException {
		cargarSugerencias();
		return sugerencias;
	}

	public void setSugerencias(List<Sugerencia> sugerencias) {
		this.sugerencias = sugerencias;
	}

}
	

