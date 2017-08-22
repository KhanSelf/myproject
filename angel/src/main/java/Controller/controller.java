package Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
/**
 * Created by khans on 2017-08-22.
 */
@Controller
public class controller {

    @RequestMapping(value = "/")
    public String test(){
        return "index";
    }
}
