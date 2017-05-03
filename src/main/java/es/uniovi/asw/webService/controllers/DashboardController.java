package es.uniovi.asw.webService.controllers;

import java.sql.Date;


import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;

import es.uniovi.asw.business.CitizenService;
import es.uniovi.asw.business.SystemService;
import es.uniovi.asw.business.impl.CitizenServiceImpl;
import es.uniovi.asw.business.impl.SystemServiceImpl;
import es.uniovi.asw.model.Administrador;
import es.uniovi.asw.model.Citizen;
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

	@Autowired
	private Estadisticas estadisticas;

	@RequestMapping("/inicio")
	public String log() {
		return "dashlogin";
	}

	// Salimos de sesion
	@RequestMapping("/salir")
	public String salir(Model model, HttpSession session) {
		session.removeAttribute("user");
		model.addAttribute("user", null);
		logger.info("Cerrando sesion");
		return "dashlogin";

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
			@RequestParam String nombre, @RequestParam String password) throws BusinessException {

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
	
		
		if (test) {
			if (nombre.equals(testAdmin.getUsuario())
					&& password.equals(testAdmin.getPassword())) {
				logger.info("Iniciando sesion como admin de pruebas");
				session.setAttribute("user", testAdmin);
				session.setAttribute("tipo", "admin");
				

				modelo.addAttribute("sugerencias", estadisticas.getSugerencias());
				
				return "dashboard";

			} else if (nombre.equals(testCitizen.getUsuario())
					&& password.equals(testCitizen.getPassword())) {

				logger.info("Iniciando sesion como usuario de pruebas");
				session.setAttribute("user", testCitizen);
				session.setAttribute("tipo", "ciudadano");
				modelo.addAttribute("user", testCitizen);
				return "infoUsuario";
			} else {
				modelo.addAttribute("err", "Usuario no encontrado");
				return "dashlogin";
			}
		}
		
		/*******************************************************
		 * 	El codigo anterior permite realizar pruebas, 
		 * 	el codigo siguiente sigue el funcionamiento correcto.
		 * ******************************************************/
		if (nombre.length() <= 0 || password.length() <= 0) {
			modelo.addAttribute("err", "Complete todos los campos");
			return "dashlogin";
		}

		try {
			SystemService sService = new SystemServiceImpl();
			Administrador admin = sService.findAdminByUserAndPass(nombre, password);
			if (admin != null) {
				session.setAttribute("user", admin);
				session.setAttribute("tipo", "admin");
				
				modelo.addAttribute("sugerencias", estadisticas.getSugerencias());
				
				logger.info("Iniciando sesion como administrador ( user=" + admin.getUsuario() + ")");
				return "dashboard";
			} else {

				Citizen ciudadano = sService.findCitizenByUserAndPass(nombre,
						password);
				if (ciudadano == null && admin == null) {
					modelo.addAttribute("err", "Usuario no encontrado");
					return "dashlogin";
				} else {
					session.setAttribute("user", ciudadano);
					session.setAttribute("tipo", "ciudadano");
					modelo.addAttribute("user", ciudadano);
					logger.info("Iniciando sesion como usuario ( user=" + ciudadano.getUsuario() + ")");
					return "listaSolicitudes";
				}
			}
		} catch (BusinessException e) {
			modelo.addAttribute("err", e.getMessage());
			return "dashlogin";
		}
	}

	
	
	@RequestMapping(value = "/volverAinfo", method = RequestMethod.GET)
	public String getParticipantInfo(HttpSession session, Model modelo,
			@ModelAttribute("user") Citizen usuario) {

		modelo.addAttribute("user", session.getAttribute("user"));

		return "infoUsuario";
	}
	@RequestMapping(value = "/oldlogin", method = RequestMethod.GET)
	public String getParticipantInfo(HttpSession session) {
		return "oldlogin";
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

	
	@RequestMapping("/limpiar")
	public String limpiarMensajes(Model model){
		estadisticas.limpiar();
		return "dashcons";
	}
	@RequestMapping(value = "/consolaDashboard")
	public String navegarDashboardConsola(Model modelo) {
		modelo.addAttribute("mensajes", estadisticas.getMensajes());
		return "dashcons";
	}
	@RequestMapping(value = "/inicioDashboard")
	public String navegarDashboardInicio(Model modelo) {

		modelo.addAttribute("sugerencias", estadisticas.getSugerencias());
		return "dashboard";
	}
	public Estadisticas getEstadisticas() {
		return estadisticas;
	}
}