package example.controller;

import boot.annotation.ioc.Autowired;
import boot.annotation.mvc.*;
import boot.core.ioc.BeansFactory;
import example.service.A;
import example.service.B;
import example.service.C;
import example.service.ExampleService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cyx
 */
@Slf4j
@RestController(value = "/exampleController")
public class exampleController {

    @Autowired
    ExampleService exampleService;

    @GetMapping(value = "/testCircleDependency")
    public Object testCircleDependency() {
        StringBuilder result = new StringBuilder();
        result.append("当前依赖情况：A依赖B B依赖A C依赖A <br/>");
        A a = (A)BeansFactory.SINGLETONS.get("example.service.A");
        B b = (B)BeansFactory.SINGLETONS.get("example.service.B");
        C c = (C)BeansFactory.SINGLETONS.get("example.service.C");
        result.append("当前a ：").append(a.toString()).append("<br/>");
        result.append("当前a类名 ：").append(a.getClass().getName()).append("<br/>");
        result.append("当前a中的b ：").append(a.getB().toString()).append("<br/>");
        result.append("当前b ：").append(b.toString()).append("<br/>");
        result.append("当前b类名 ：").append(b.getClass().getName()).append("<br/>");
        result.append("当前b中的a ：").append(b.getA().toString()).append("<br/>");
        result.append("当前c中的a ：").append(c.getA().toString()).append("<br/>");
        result.append("当前c类名 ：").append(c.getClass().getName()).append("<br/>");
        return result.toString();
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
