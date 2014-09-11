import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import junit.framework.TestCase;


public class PhoneListTest extends TestCase{

	
	@Test
	public void test01() throws IOException {

		// given
		final URL url = readFile("phone03.xml");

		// when
		PhoneList.scanFromBufferedInput(null, url.getPath(), true);

	}
	
	private URL readFile(final String fileName) {
		URL url = this.getClass().getResource(fileName);
		return url;
	}
}
