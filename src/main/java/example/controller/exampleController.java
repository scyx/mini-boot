package example.controller;

import boot.annotation.mvc.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cyx
 */
@RestController(value = "")
public class exampleController {

    @GetMapping(value = "/getId/{id}")
    public String getId(@PathVariable String id,
                        @RequestParam("name") String name,
                        @RequestParam("age") int age) {
        return "id:" + id + "name:" + name + "age:" + age;
    }

    @PostMapping(value = "/getName/post/{id}")
    public Map<String, Object> createUser(@RequestBody User user, @RequestParam String s, @PathVariable int id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("user",user);
        map.put("s",s);
        return map;
    }

    @PostMapping(value = "/getName/post")
    public Map<String, Object> post(@RequestBody User user) {
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        return map;
    }
}
