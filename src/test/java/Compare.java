import org.apache.commons.lang3.StringUtils;

public class Compare {
    public static void main(String[] args) {
        int i = compareEach("1.2.3a", "1.2.4b");
        System.out.println(i);

    }


    public static int compareEach(String s1, String s2) {


        String[] a1 = s1.split("\\.");
        String[] a2 = s2.split("\\.");
        int index = 0;

        int ml = Math.min(a1.length, a2.length);
        int result = 0;
        for (int i = 0; i < ml; i++) {
            int a = 0;

            //如果a1[i],a2[i]是数字，直接判断结果
            if (StringUtils.isNumeric(a1[i]) && StringUtils.isNumeric(a2[i])) {
                if (Integer.parseInt(a1[i]) - Integer.parseInt(a2[i]) != 0) {
                    return Integer.parseInt(a1[i]) - Integer.parseInt(a2[i]);
                }
            } else {
                //如果s1或s2不是数字，先比较数字部分，若不相同，直接返回结果，
                // 若数字部分相同，则返回字母比较大小(默认只有最后一位是字母)
                char[] c1 = a1[i].toCharArray();
                char[] c2 = a2[i].toCharArray();

                int preA1 = Integer.parseInt(a1[i].substring(0, c1.length - 2));
                int preA2 = Integer.parseInt(a2[i].substring(0, c1.length - 2));
                if (preA1 - preA2 != 0) {
                    return preA1 - preA2;
                } else {
                    return c1[c1.length - 1] - c2[c2.length - 1];
                }
            }

        }

        return result;
    }

}
