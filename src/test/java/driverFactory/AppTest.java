package driverFactory;

import org.testng.annotations.Test;

public class AppTest {
	
	@Test
	public void kickstart() throws Throwable {
		driverScricpt ds=new driverScricpt();
		ds.starttest();
	}

}
