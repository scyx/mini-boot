package example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cyx
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int age;
    private String name;
    private String desc;
}
