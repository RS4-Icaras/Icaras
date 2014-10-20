package nl.rsvier.icaras.core.intake;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IntakeTest {

	 public static void main(String[] args) {
	  ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(
	    "icarasdb-context.xml");
	  springContext.close();
	 }

	
}
