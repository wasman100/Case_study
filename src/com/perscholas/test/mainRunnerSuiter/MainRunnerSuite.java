package automation.mainRunnerSuiter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import automation.Registration.RegistrationRunner;
import automation.getAQuote.GetAQuoteRunner;
import automation.login.LoginRunner;



@RunWith(Suite.class)
@Suite.SuiteClasses({
	RegistrationRunner.class,
	LoginRunner.class,
	GetAQuoteRunner.class
})


public class MainRunnerSuite {

}
