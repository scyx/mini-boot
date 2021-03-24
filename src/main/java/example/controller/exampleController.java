package example.controller;

import boot.annotation.mvc.GetMapping;
import boot.annotation.mvc.PathVariable;
import boot.annotation.mvc.RestController;

/**
 * @author cyx
 */
@RestController(value = "")
public class exampleController {

    @GetMapping(value = "/getString/{id}")
    public String getTest(@PathVariable String id) {
        return "123";
    }
}
