package gs.dice.diceroller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiceRollerApplicationTests {

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate;

	@Test
	public void checkApplicationStartedAndIsHealthy() {
		restTemplate = new TestRestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port +"/health", String.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getBody(), containsString("UP"));
	}

}
