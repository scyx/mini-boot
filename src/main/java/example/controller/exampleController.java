package example.controller;

import boot.annotation.ioc.Autowired;
import boot.annotation.mvc.*;
import boot.core.ioc.BeansFactory;
import example.service.A;
import example.service.B;
import example.service.ExampleService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cyx
 */
@Slf4j
@RestController(value = "")
public class exampleController {

    @Autowired
    ExampleService exampleService;

    @GetMapping(value = "/testCircleDependency")
    public Object testA() {
        A a = (A)BeansFactory.SINGLETONS.get("example.service.A");
        B b = (B)BeansFactory.SINGLETONS.get("example.service.B");
        Map<String,Object> map = new HashMap<>();
        map.put("a",a.toString());
        map.put("a.b",a.getB().toString());
        map.put("b.a",b.getA().toString());
        map.put("b",b.toString());
        return map;
    }


    @GetMapping(value = "/getId/{id}")
    public String getId(@PathVariable int id) {
        return exampleService.test(id);
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
