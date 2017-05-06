package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.uniovi.asw.Application;
import es.uniovi.asw.util.VerificadorEmail;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class VerificadorEmailTest {

	String[] emailCorrectos;
	String[] emailIncorrectos;
	@Before
	public void setSB(){
		emailCorrectos = new String[3];
		emailIncorrectos= new String[3];
		
		emailCorrectos[0]="ivanpsl95@gmail.com";
		emailCorrectos[1]="emailTest@hotmail.es";
		emailCorrectos[2]="3email@tel21.es";
		
		emailIncorrectos[0]="asdgdc@";
		emailIncorrectos[1]="12345.2123";
		emailIncorrectos[2]="sadasd.asd";
		
	}
	@Test
	public void test() {
		for(int i=0; i< emailCorrectos.length; i++){
			assertEquals(true, VerificadorEmail.validateEmail(emailCorrectos[i]));
		}
		for(int i=0; i< emailIncorrectos.length; i++){
			assertEquals(false, VerificadorEmail.validateEmail(emailIncorrectos[i]));
		}
	}

}
