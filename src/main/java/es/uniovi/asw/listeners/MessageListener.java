package es.uniovi.asw.listeners;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import es.uniovi.asw.model.Comentario;
import es.uniovi.asw.model.Sugerencia;
import es.uniovi.asw.model.VotoComentario;
import es.uniovi.asw.model.VotoSugerencia;
import es.uniovi.asw.producers.Topics;
import es.uniovi.asw.webService.Message;
import es.uniovi.asw.webService.controllers.Estadisticas;


/**
 * Created by herminio on 28/12/16.
 */
@ManagedBean
public class MessageListener {

    private static final Logger logger = Logger.getLogger(MessageListener.class);
   
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    Estadisticas estadisticas;
    
    public MessageListener() {
	}
    @KafkaListener(topics = "exampleTopic")
    public void listen(String data) {
        logger.info("New message received: \"" + data + "\"");
    }
    
    @KafkaListener(topics = Topics.CREATE_SUGGESTION)
    public void createSuggestion(String data) {
    	Sugerencia sugg = Message.getSugerenciaFromJSON(data);
        estadisticas.añadirSugerencia(sugg);
        
        estadisticas.sendToAdminConsole("[CREAR SUGERENCIA] Autor de la accion [id="+sugg.getCitizen().getId()+"] " + sugg.getCitizen().getNombre() + 
        		"  [id=" + sugg.getId() + "] " + sugg.getTitulo());
        logger.info("[CREAR SUGERENCIA] Autor de la accion [id="+sugg.getCitizen().getId()+"] " + sugg.getCitizen().getNombre() + 
        		"  [id=" + sugg.getId() + "] " + sugg.getTitulo());
    }
    
    @KafkaListener(topics = "DELETE_SUGGESTION")
    public void deleteSuggestion(String data) {
    	Sugerencia sugg = Message.getSugerenciaFromJSON(data);
        
        estadisticas.sendToAdminConsole("[ELIMINAR SUGERENCIA] Autor de la accion [id="+sugg.getCitizen().getId()+"] " + sugg.getCitizen().getNombre() + 
        		"  [id=" + sugg.getId() + "] " + sugg.getTitulo());
        
        logger.info("[ELIMINAR SUGERENCIA] Autor de la accion [id="+sugg.getCitizen().getId()+"] " + sugg.getCitizen().getNombre() + 
        		"  [id=" + sugg.getId() + "] " + sugg.getTitulo());
    }
    
    
    @KafkaListener(topics = Topics.VOTE_SUGGESTION)
    public void voteSuggPositive(String data) {
    	VotoSugerencia voto = Message.getVotoSugerenciaFromJSON(data);
    	
        estadisticas.sendToAdminConsole("[VOTAR SUGERENCIA] Autor de la accion [id="+voto.getCitizen().getId()+"] " +
        voto.getCitizen().getNombre());
        
        logger.info("[VOTAR SUGERENCIA] Autor de la accion [id="+voto.getCitizen().getId()+"] " +
        voto.getCitizen().getNombre());
    }
    
    
    @KafkaListener(topics = Topics.COMMENT_SUGGESTION)
    public void commentSuggestion(String data) {
    	Comentario comentario = Message.getComentarioFromJSON(data);
        
        estadisticas.sendToAdminConsole("[COMENTARS SUGERENCIA] Autor del comentario [id="+ comentario.getCitizen().getId()+"] " +
        		comentario.getCitizen().getNombre()+ " en la sugerencia: " + comentario.getSugerencia());
        
        logger.info("[COMENTARS SUGERENCIA] Autor del comentario [id="+ comentario.getCitizen().getId()+"] " +
        comentario.getCitizen().getNombre()+ " en la sugerencia: " + comentario.getSugerencia());
    }
    
//    @KafkaListener(topics =  "DELETE_COMMENT")
//    public void deleteComment(String data) {
//    	Comentario comentario = Message.getComentarioFromJSON(data);
//        logger.info("New message received: [ELIMINAR COMENTARIO]  \"" +  data + "\"");
//        
//        logger.info("[ELIMINAR COMENTARIO] Autor del comentario [id="+ comentario.getCitizen().getId()+"] " +
//        comentario.getCitizen().getNombre()+ " en la sugerencia: " + comentario.getSugerencia());
//    }

    @KafkaListener(topics = Topics.VOTE_COMMENT)
    public void voteComment(String data) {
    	VotoComentario votoComentario = Message.getVotoComentarioFromJSON(data);
        logger.info("New message received: [ELIMINAR COMENTARIO]  \"" +  data + "\"");
        
        estadisticas.sendToAdminConsole("[ELIMINAR COMENTARIO] Autor de la accion [id="+ votoComentario.getCitizen().getId()+"] " +
                votoComentario.getCitizen().getNombre()+ " en el comentario: " + votoComentario.getComentario());
        
        logger.info("[ELIMINAR COMENTARIO] Autor de la accion [id="+ votoComentario.getCitizen().getId()+"] " +
        votoComentario.getCitizen().getNombre()+ " en el comentario: " + votoComentario.getComentario());
    }


}
