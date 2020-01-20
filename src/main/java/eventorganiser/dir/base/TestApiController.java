package eventorganiser.dir.base;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class TestApiController {

    //@EventListener(ApplicationReadyEvent.class)
//    public void testingAPI() { // This test helped with the API login authentication issue
//        try {
//            final String uri = "http://localhost:8080/api/get/user?username=drake.s.deaton@gmail.com";
//            RestTemplate restTemplate = new RestTemplate();
//            String result = restTemplate.getForObject(uri, String.class);
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
