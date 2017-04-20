package es.uniovi.asw.webService.controllers;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.uniovi.asw.business.CitizenService;
import es.uniovi.asw.business.SystemService;
import es.uniovi.asw.business.impl.CitizenServiceImpl;
import es.uniovi.asw.business.impl.SystemServiceImpl;
import es.uniovi.asw.model.Administrador;
import es.uniovi.asw.model.Citizen;
import es.uniovi.asw.model.Sugerencia;
import es.uniovi.asw.model.exception.BusinessException;
import es.uniovi.asw.util.Encriptador;
import es.uniovi.asw.util.VerificadorEmail;

@Controller
@Scope("session")
public class DashboardController {

	private boolean test = false;
	private Citizen testCitizen = new Citizen("NombreDeTest", "ApellidosDeTest",
			"testemail@email", new Date(0), "C\\direccionDeTest", "Esp", "71905648",
			"ciudadano1", "ciudadano");
	private Administrador testAdmin = new Administrador("admin", "admin");

	private static final Logger logger = Logger.getLogger(DashboardController.class);
	//private List<Object> sseEmitters = Collections.synchronizedList(new ArrayList<>());

	@Autowired
	private Estadisticas estadisticas;

//	@Autowired
//	private KafkaProducer kafkaProducer;

	/*@RequestMapping("/")
	public String landing(Model model) {
		return "login";
	}*/

	@RequestMapping("/inicio")
	public String log() {
		return "login";
	}

	// Salimos de sesion
	@RequestMapping("/salir")
	public String salir(Model model, HttpSession session) {
		session.removeAttribute("user");
		model.addAttribute("user", null);
		logger.info("Cerrando sesion");
		return "login";

	}

	// @RequestMapping("/testUsuario")
	// public String testUsuario(Model model, HttpSession session) {
	// @SuppressWarnings("deprecation")
	// Date fecha = new Date(0, 0, 0);
	// Citizen user = new Citizen("testUser", Encriptador.encriptar("ss"),
	// "email@test.com", "123T", "TestName", "TestApp", fecha,
	// "C/test", "España");
	// model.addAttribute("user", user);
	// session.setAttribute("user", user);
	// return "infoUsuario";
	// }

	/**
	 * Metodo que gestion la peticion que permite loguearse
	 * 
	 * @param session
	 * @param modelo
	 * @param nombre
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/entrar", method = RequestMethod.POST)
	public String getParticipantInfo(HttpSession session, Model modelo,
			@RequestParam String nombre, @RequestParam String password) {

		/******************
		 * TEST ************************
		 * 
		 * Test administrador para acceder a dashboard sin BD : Usuario: admin
		 * Password: admin
		 * 
		 * Test ciudadano para acceder a informacion sin BD: Usuario: ciudadano
		 * Password: ciudadano
		 * 
		 * */
		logger.info("Iniciando sesion");
		if (test) {
			if (nombre.equals(testAdmin.getUsuario())
					&& password.equals(testAdmin.getPassword())) {

				session.setAttribute("user", testAdmin);
				session.setAttribute("tipo", "admin");
				// modelo.setAttribute("mensajes" )
				modelo.addAttribute("mensajes", estadisticas.getMensajes());

				return "dashboard";

			} else if (nombre.equals(testCitizen.getUsuario())
					&& password.equals(testCitizen.getPassword())) {

				session.setAttribute("user", testCitizen);
				session.setAttribute("tipo", "ciudadano");
				modelo.addAttribute("user", testCitizen);
				return "infoUsuario";
			} else {
				modelo.addAttribute("err", "Usuario no encontrado");
				return "login";
			}
		}
		
		/*******************************************************
		 * 	El codigo anterior permite realizar pruebas, 
		 * 	el codigo siguiente sigue el funcionamiento correcto.
		 * ******************************************************/
		if (nombre.length() <= 0 || password.length() <= 0) {
			modelo.addAttribute("err", "Complete todos los campos");
			return "login";
		}

		try {
			SystemService sService = new SystemServiceImpl();
			Administrador admin = sService.findAdminByUserAndPass(nombre, password);
			if (admin != null) {
				session.setAttribute("user", admin);
				session.setAttribute("tipo", "admin");
				return "dashboard";
			} else {

				Citizen ciudadano = sService.findCitizenByUserAndPass(nombre,
						password);
				if (ciudadano == null && admin == null) {
					modelo.addAttribute("err", "Usuario no encontrado");
					return "login";
				} else {
					session.setAttribute("user", ciudadano);
					session.setAttribute("tipo", "ciudadano");
					modelo.addAttribute("user", ciudadano);
					return "infoUsuario";
				}
			}
		} catch (BusinessException e) {
			modelo.addAttribute("err", e.getMessage());
			return "login";
		}
	}

	//
	@RequestMapping(value = "/volverAinfo", method = RequestMethod.GET)
	public String getParticipantInfo(HttpSession session, Model modelo,
			@ModelAttribute("user") Citizen usuario) {

		modelo.addAttribute("user", session.getAttribute("user"));

		return "infoUsuario";
	}

	/**
	 * Metodo que permite navegar a la pagina para cambiar el email
	 * 
	 * @param modelo
	 * @return
	 */
	@RequestMapping(value = "/cambiarE")
	public String navegarCambiarEmail(HttpSession session, Model modelo) {
		modelo.addAttribute("err", " ");
		modelo.addAttribute("user", session.getAttribute("user"));
		return "cambiarEmail";
	}

	/**
	 * Metodo que se encarga del cambio de email
	 * 
	 * @param session
	 * @param modelo
	 * @param usuario
	 * @param password
	 * @param newEmail
	 * @return
	 */
	@RequestMapping(value = "/cambioEmail", method = RequestMethod.POST)
	public String changeEmail(HttpSession session, Model modelo,
			@ModelAttribute("user") Citizen usuario,
			@RequestParam String password, @RequestParam String newEmail) {
		CitizenService cService = new CitizenServiceImpl();
		Citizen user = (Citizen) session.getAttribute("user");

		try {
			if ((test && password.equals("ciudadano"))
					|| password.equals(Encriptador.desencriptar(user
							.getPassword()))) {

				if (!VerificadorEmail.validateEmail(newEmail)) {
					modelo.addAttribute("err", "Email incorrecto");
					return "cambiarEmail";
				}

				modelo.addAttribute("err", "");
				if (!test)
					cService.changeEmail(user, newEmail);
				else
					user.setEmail(newEmail);

				session.setAttribute("user", user);
				modelo.addAttribute("success",
						"Se ha actualizado el email correctamente");
				logger.info("Se ha actualizado el email correctamente");
				return "exito";

			} else {
				modelo.addAttribute("err", "Contraseña incorrecta");
				logger.info("Contraseña incorrecta al actualizar email");
				return "cambiarEmail";
			}

		} catch (Exception e) {
			e.printStackTrace();

			logger.info("Error al cambiar el email");
			modelo.addAttribute("err", e.getMessage());
			return "error";
		}
	}

	//
	
	@RequestMapping(value = "/cambiarP")
	public String navegarCambiarContrasena(Model modelo,
			@ModelAttribute("user") Citizen usuario) {
		modelo.addAttribute("user", usuario);
		modelo.addAttribute("err", " ");
		return "cambiarPass";
	}

	//
	//
	@RequestMapping(value = "/cambio", method = RequestMethod.POST)
	public String changePassword(HttpSession session, Model modelo,
			@ModelAttribute("user") Citizen usuario,
			@RequestParam String password, @RequestParam String newPassword1,
			@RequestParam String newPassword2) {
		CitizenService cService = new CitizenServiceImpl();
		Citizen user = (Citizen) session.getAttribute("user");
		try {

			if (!password.equals(Encriptador.desencriptar(user.getPassword())) && !test) {
				modelo.addAttribute("err", "Contraseña incorrecta");
				return "cambiarPass";
			}
			if(test && !testCitizen.getPassword().equals(password)){
				modelo.addAttribute("err", "Contraseña incorrecta");
				return "cambiarPass";
			}
			if (newPassword1.length() <= 0) {
				modelo.addAttribute("err", "Escriba la nueva contraseña");
				return "cambiarPass";
			}
			if (!newPassword1.equals(newPassword2)) {
				modelo.addAttribute("err", "Las contraseñas deben coincidir");
				return "cambiarPass";
			}

			modelo.addAttribute("err", "");
			if(!test)
				cService.changePassword(user, newPassword1);
			else {
				user.setPassword(newPassword1);
			}
			
			modelo.addAttribute("err", "");
			modelo.addAttribute("user", user);
			session.setAttribute("user", user);
			modelo.addAttribute("success",
					"Se ha actualizado la contraseña correctamente");
			logger.info("La contraseña ha sido cambiada con exito");
			return "exito";

		} catch (Exception e) {
			e.printStackTrace();
			modelo.addAttribute("err", e.getMessage());
			return "error";
		}
	}

	@RequestMapping("/enviarTestSugerencia")
	public String send(Model model) {
		estadisticas.añadirMensaje("Enviando sugerencia");
		estadisticas.sendProposalMessagesCouncilstaff();
		// kProducer.send("CREATE_SUGGESTION", );
		return "dashboard";
	}

	@RequestMapping("/limpiar")
	public String limpiarMensajes(Model model){
		estadisticas.limpiar();
		return "dashboard";
	}
	@ModelAttribute("sugerencias")
	public List<Sugerencia> getSugerencias() {
		return estadisticas.getTopSugerencias();
	}

	@ModelAttribute("mensajes")
	public List<String> getMensajes() {
		return estadisticas.getMensajes();
	}

	public Estadisticas getEstadisticas() {
		return estadisticas;
	}
}