package example.controller;

import boot.annotation.mvc.GetMapping;
import boot.annotation.mvc.PathVariable;
import boot.annotation.mvc.RestController;

/**
 * @author cyx
 */
@RestController(value = "")
public class exampleController {

    @GetMapping(value = "/getId/{id}/{name}")
    public String getId(@PathVariable String id,
                        @PathVariable String name) {
        return "id:" + id + "name:" + name;
    }

    @GetMapping(value = "/getName/{name}")
    public String getName(@PathVariable("name") String name) {
        return name;
    }
}
