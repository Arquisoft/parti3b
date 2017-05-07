package es.uniovi.asw.persistence.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.uniovi.asw.infrastructure.MyLogger;

public class Jpa {

	private static EntityManagerFactory emf = null;
	private static ThreadLocal<EntityManager> emThread = new ThreadLocal<EntityManager>();

	public static EntityManager createEntityManager() {
		EntityManager entityManager = getEmf().createEntityManager();
		emThread.set(entityManager);
		return entityManager;
	}

	public static EntityManager getManager() {
		return emThread.get();
	}

	private static EntityManagerFactory getEmf() {
		if (emf == null) {
			String persistenceUnitName = loadPersistentUnitName();
			emf = Persistence.createEntityManagerFactory(persistenceUnitName, getConfForPostgres());
		}
		return emf;
	}

	private static String loadPersistentUnitName() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(Jpa.class.getResourceAsStream("/META-INF/persistence.xml"));
			MyLogger.debug("====> Estoy sacando unidad de pesistencia");

			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("persistence-unit");
			MyLogger.debug("Nombre unidad: " + ((Element) nl.item(0)).getAttribute("name"));

			return ((Element) nl.item(0)).getAttribute("name");

		} catch (ParserConfigurationException e1) {
			throw new RuntimeException(e1);
		} catch (SAXException e1) {
			throw new RuntimeException(e1);
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
	}

	private static Map<String, String> getConfForPostgres() {
		String databaseUrl = System.getenv("DATABASE_URL");

		StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");

		String userName = st.nextToken();
		String password = st.nextToken();
		String host = st.nextToken();
		String port = st.nextToken();
		String databaseName = st.nextToken();

		String jdbcUrl = String.format(
				"jdbc:postgresql://%s:%s/%s?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", host, port,
				databaseName);

		Map<String, String> properties = new HashMap<String, String>();

		properties.put("javax.persistence.jdbc.url", jdbcUrl);
		properties.put("javax.persistence.jdbc.user", userName);
		properties.put("javax.persistence.jdbc.password", password);
		properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");

		return properties;
	}

}