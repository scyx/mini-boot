package example.service;

import boot.annotation.ioc.Component;

/**
 * @author cyx
 */
@Component
public class ExampleServiceImpl implements ExampleService {
    @Override
    public String test(int id) {
        return "accept id :" + id;
    }
}
