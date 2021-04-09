package example.aspect;

import boot.annotation.aop.After;
import boot.annotation.aop.Aspect;
import boot.annotation.aop.Before;
import boot.annotation.aop.PointCut;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cyx
 */
@Aspect
public class TestAspect {

    @PointCut("example")
    public void PointCut() {

    }

    @Before
    public void doBefore() {

    }

    @After
    public void doAfter() {

    }

    public static void main(String[] args) {
        String p = "com.github.demo.*.*Service*";
        String s = "com.github.demo.user.UserService";
//        Pattern pattern = Pattern.compile(p);
//        Matcher matcher = pattern.matcher(s);
        System.out.println(simpleMatch(p,s));
//        System.out.println(matcher.find());
    }

    public static boolean simpleMatch(String pattern, String str) {
        if (pattern == null || str == null) {
            return false;
        }
        int firstIndex = pattern.indexOf('*');
        if (firstIndex == -1) {
            return pattern.equals(str);
        }
        if (firstIndex == 0) {
            if (pattern.length() == 1) {
                return true;
            }
            int nextIndex = pattern.indexOf('*', firstIndex + 1);
            if (nextIndex == -1) {
                return str.endsWith(pattern.substring(1));
            }
            String part = pattern.substring(1, nextIndex);
            if ("".equals(part)) {
                return simpleMatch(pattern.substring(nextIndex), str);
            }
            int partIndex = str.indexOf(part);
            while (partIndex != -1) {
                if (simpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
                    return true;
                }
                partIndex = str.indexOf(part, partIndex + 1);
            }
            return false;
        }
        return (str.length() >= firstIndex
                && pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex))
                && simpleMatch(pattern.substring(firstIndex), str.substring(firstIndex)));
    }












}
