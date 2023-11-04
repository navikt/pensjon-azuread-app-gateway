package no.nav.pensjon.gateway.pensjonazureadappgateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String handle() {
		return "Hello WebFlux";
	}
}
