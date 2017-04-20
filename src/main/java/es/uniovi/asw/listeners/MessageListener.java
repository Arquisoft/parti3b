package es.uniovi.asw.listeners;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import es.uniovi.asw.model.Comentario;
import es.uniovi.asw.model.Sugerencia;
import es.uniovi.asw.model.VotoComentario;
import es.uniovi.asw.model.VotoSugerencia;
import es.uniovi.asw.webService.Message;
import es.uniovi.asw.webService.controllers.Estadisticas;


/**
 * Created by herminio on 28/12/16.
 */
@ManagedBean
public class MessageListener {

    private static final Logger logger = Logger.getLogger(MessageListener.class);

    @Autowired
    Estadisticas estadisticas;
    
    @KafkaListener(topics = "exampleTopic")
    public void listen(String data) {
        logger.info("New message received: \"" + data + "\"");
    }
    
    @KafkaListener(topics = "CREATE_SUGGESTION")
    public void createSuggestion(String data) {
    	Sugerencia sugg = Message.getSugerenciaFromJSON(data);
        logger.info("New message received: [CREAR SUGERENCIA]  \"" +  data + "\"");
        estadisticas.añadirMensaje("*Received -> [CREAR SUGERENCIA] Autor de la accion [id="+sugg.getCitizen().getId()+"] " + sugg.getCitizen().getNombre() + 
        		"  [id=" + sugg.getId() + "] " + sugg.getTitulo());
        estadisticas.añadirSugerencia(sugg);
        logger.info("[CREAR SUGERENCIA] Autor de la accion [id="+sugg.getCitizen().getId()+"] " + sugg.getCitizen().getNombre() + 
        		"  [id=" + sugg.getId() + "] " + sugg.getTitulo());
    }
    
    @KafkaListener(topics = "DELETE_SUGGESTION")
    public void deleteSuggestion(String data) {
    	Sugerencia sugg = Message.getSugerenciaFromJSON(data);
        logger.info("New message received: [ELIMINAR SUGERENCIA]  \"" +  data + "\"");
        
        logger.info("[ELIMINAR SUGERENCIA] Autor de la accion [id="+sugg.getCitizen().getId()+"] " + sugg.getCitizen().getNombre() + 
        		"  [id=" + sugg.getId() + "] " + sugg.getTitulo());
    }
    
    @KafkaListener(topics = "VOTE_SUGGESTION_POSITIVE")
    public void voteSuggPositive(String data) {
    	VotoSugerencia voto = Message.getVotoSugerenciaFromJSON(data);
        logger.info("New message received: [VOTAR POSITIVO SUGERENCIA]  \"" +  data + "\"");
        
        logger.info("[VOTAR POSITIVO SUGERENCIA] Autor de la accion [id="+voto.getCitizen().getId()+"] " +
        voto.getCitizen().getNombre());
    }
    
    
    @KafkaListener(topics = "VOTE_SUGGESTION_NEGATIVE")
    public void voteSuggNegative(String data) {
    	VotoSugerencia voto = Message.getVotoSugerenciaFromJSON(data);
        logger.info("New message received: [VOTAR NEGATIVO SUGERENCIA]  \"" +  data + "\"");
        
        logger.info("[VOTAR NEGATIVO SUGERENCIA] Autor de la accion [id="+voto.getCitizen().getId()+"] " +
        voto.getCitizen().getNombre());
    }
    
    @KafkaListener(topics = "COMMENT_SUGGESTION")
    public void commentSuggestion(String data) {
    	Comentario comentario = Message.getComentarioFromJSON(data);
        logger.info("New message received: [COMENTAR SUGERENCIA]  \"" +  data + "\"");
        
        logger.info("[COMENTARS SUGERENCIA] Autor del comentario [id="+ comentario.getCitizen().getId()+"] " +
        comentario.getCitizen().getNombre()+ " en la sugerencia: " + comentario.getSugerencia());
    }
    @KafkaListener(topics = "DELETE_COMMENT")
    public void deleteComment(String data) {
    	Comentario comentario = Message.getComentarioFromJSON(data);
        logger.info("New message received: [ELIMINAR COMENTARIO]  \"" +  data + "\"");
        
        logger.info("[ELIMINAR COMENTARIO] Autor del comentario [id="+ comentario.getCitizen().getId()+"] " +
        comentario.getCitizen().getNombre()+ " en la sugerencia: " + comentario.getSugerencia());
    }

    @KafkaListener(topics = "VOTE_COMMENT")
    public void voteComment(String data) {
    	VotoComentario votoComentario = Message.getVotoComentarioFromJSON(data);
        logger.info("New message received: [ELIMINAR COMENTARIO]  \"" +  data + "\"");
        
        logger.info("[ELIMINAR COMENTARIO] Autor de la accion [id="+ votoComentario.getCitizen().getId()+"] " +
        votoComentario.getCitizen().getNombre()+ " en el comentario: " + votoComentario.getComentario());
    }


}
