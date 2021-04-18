package boot.util;

public class PatternMatchUtil {
    public static boolean simpleMatch(String pattern, String str) {
        if (pattern == null || str == null) {
            return false;
        }
        // 找到第一个*的位置
        int firstIndex = pattern.indexOf('*');
        // 没找到 就判断两个字符串是否完全相等
        if (firstIndex == -1) {
            return pattern.equals(str);
        }
        // 如果*的位置是第一个
        if (firstIndex == 0) {
            // 并且模式串的长度为1 则为全匹配 不管str的值 直接返回true
            if (pattern.length() == 1) {
                return true;
            }
            // 从第一个*往后的第二个*的位置
            int nextIndex = pattern.indexOf('*',firstIndex + 1);
            // 如果第没有二个* 判断str是否以pattern的*后面的结尾
            if (nextIndex == -1) {
                return str.endsWith(pattern.substring(1));
            }
            // 第一个*和第二个*中间的部分
            String part = pattern.substring(1,nextIndex);

            if ("".equals(part)) {
                return simpleMatch(pattern.substring(nextIndex),str);
            }
            // 字符串的part位置
            int partIndex = str.indexOf(part);
            while (partIndex != -1) {
                if (simpleMatch(pattern.substring(nextIndex),str.substring(partIndex + part.length()))) {
                    return true;
                }
                partIndex = str.indexOf(part,partIndex + 1);
            }
            return false;
        }
        return (str.length() >= firstIndex &&
                pattern.substring(0,firstIndex).equals(str.substring(0,firstIndex))
                && simpleMatch(pattern.substring(firstIndex),str.substring(firstIndex)));
    }

    public static void main(String[] args) {
        String pattern = "com.github.demo.*.*Service*";
        String str = "com.github.demo.user.userService";
        boolean res = simpleMatch(pattern,str);
        System.out.println(res);
    }
}
