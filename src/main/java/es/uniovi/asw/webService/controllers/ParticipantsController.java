package es.uniovi.asw.webService.controllers;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.uniovi.asw.business.Services;
import es.uniovi.asw.model.Citizen;
import es.uniovi.asw.model.exception.BusinessException;
import es.uniovi.asw.util.VerificadorEmail;

@Controller
@Scope("session")
public class ParticipantsController {

	private static final Logger logger = Logger
			.getLogger(ParticipantsController.class);

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
	@RequestMapping(value = "/irAinfoUsuario")
	public String navegarAinfoUsuario(HttpSession session, Model modelo)
			throws BusinessException {
		
		modelo.addAttribute("user", session.getAttribute("user"));

		return "infoUsuario";

	}
	private void changeInfo(Citizen user) throws BusinessException {

		Services.getCitizenServices().updateInfo(user);

		logger.info("Se ha actualizado el usuario correctamente");
	}
}
