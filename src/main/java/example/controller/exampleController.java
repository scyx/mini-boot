package example.controller;

import boot.annotation.mvc.GetMapping;
import boot.annotation.mvc.RestController;

@RestController(value = "")
public class exampleController {

    @GetMapping(value = "/getString")
    public String getTest() {
        return "123";
    }
}
