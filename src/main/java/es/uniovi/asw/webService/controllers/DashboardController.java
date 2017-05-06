package es.uniovi.asw.webService.controllers;

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

import es.uniovi.asw.business.Services;
import es.uniovi.asw.model.Administrador;
import es.uniovi.asw.model.Citizen;
import es.uniovi.asw.model.exception.BusinessException;
import es.uniovi.asw.util.VerificadorEmail;

@Controller
@Scope("session")
public class DashboardController {

	private static final Logger logger = Logger
			.getLogger(DashboardController.class);

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
			@RequestParam String nombre, @RequestParam String password)
			throws BusinessException {

		if (nombre.length() <= 0 || password.length() <= 0) {
			modelo.addAttribute("err", "Complete todos los campos");
			return "dashlogin";
		}

		try {
			Administrador admin = Services.getSystemServices()
					.findAdminByUserAndPass(nombre, password);
			if (admin != null) {
				session.setAttribute("user", admin);
				session.setAttribute("tipo", "admin");
				Actions.listarSugerencias(modelo, null);

				logger.info("Iniciando sesion como administrador ( user="
						+ admin.getUsuario() + ")");
				return "dashboard";
			} else {

				Citizen ciudadano = Services.getSystemServices()
						.getParticipant(nombre, password);
				if (ciudadano == null && admin == null) {
					modelo.addAttribute("err", "Usuario no encontrado");
					return "dashlogin";
				} else {
					session.setAttribute("user", ciudadano);
					session.setAttribute("tipo", "ciudadano");
					modelo.addAttribute("user", ciudadano);
					Actions.listarSugerencias(modelo, ciudadano);
					session.setAttribute("admin", null);
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
	public String cambiarEmail(HttpSession session, Model modelo,
			@ModelAttribute("user") Citizen usuario,
			@RequestParam String password, @RequestParam String newEmail) {

		Citizen user = (Citizen) session.getAttribute("user");
		try {
			if (password.equals(user.getPassword())) {

				if (!VerificadorEmail.validateEmail(newEmail)) {
					modelo.addAttribute("err", "Email incorrecto");
					return "cambiarEmail";
				} else
					modelo.addAttribute("err", "");
				user.setEmail(newEmail);

				changeInfo(user);

				session.setAttribute("user", user);
				modelo.addAttribute("success",
						"Se ha actualizado el email correctamente");

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

	/**
	 * Metodo que se encarga de cambiar la contraseña
	 * 
	 * @param session
	 * @param modelo
	 * @param usuario
	 *            : usuario
	 * @param password
	 *            : contraseña vieja
	 * @param newPassword1
	 *            : contraseña nueva
	 * @param newPassword2
	 *            : contraseña nueva repetida
	 * @return
	 */
	@RequestMapping(value = "/cambio", method = RequestMethod.POST)
	public String changePassword(HttpSession session, Model modelo,
			@ModelAttribute("user") Citizen usuario,
			@RequestParam String password, @RequestParam String newPassword1,
			@RequestParam String newPassword2) {

		Citizen user = (Citizen) session.getAttribute("user");
		try {

			if (!password.equals(user.getPassword())) {
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
			user.setPassword(newPassword1);

			changeInfo(user);

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
	public String limpiarMensajes(Model model) {
		estadisticas.limpiar();
		return "dashcons";
	}

	@RequestMapping(value = "/consolaDashboard")
	public String navegarDashboardConsola(Model modelo) {
		modelo.addAttribute("mensajes", estadisticas.getMensajes());
		return "dashcons";
	}

	@RequestMapping(value = "/inicioDashboard")
	public String navegarDashboardInicio(Model modelo) throws BusinessException {
		Actions.listarSugerencias(modelo, null);
		modelo.addAttribute("sugerencias", estadisticas.getSugerencias());
		return "dashboard";
	}


	@RequestMapping(value = "/irAParticipationSystem")
	public String navegarAparticipationSystem(HttpSession session,
			Model modelo) throws BusinessException {
		Actions.listarSugerencias(modelo, null);
		
		session.setAttribute("user", null);
		
		 return "listaSolicitudesadmin";
	
	}

	public Estadisticas getEstadisticas() {
		return estadisticas;
	}

	private void changeInfo(Citizen user) throws BusinessException {

		Services.getCitizenServices().updateInfo(user);

		logger.info("Se ha actualizado el usuario correctamente");
	}
}
