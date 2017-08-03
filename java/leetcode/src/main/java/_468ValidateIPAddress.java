import java.util.regex.Pattern;

/**
 * @author yangxiaochen
 * @date 2017/8/3 17:57
 */
public class _468ValidateIPAddress {
    public static void main(String[] args) {

//        System.out.println(Arrays.toString("2001:0db8:85a3:0:0:8A2E:0370:7334:".split(":")));
        _468ValidateIPAddress o = new _468ValidateIPAddress();
//        System.out.println(o.validIPAddress("172.16.254.1"));
//        System.out.println(o.validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334"));
//        System.out.println(o.validIPAddress("256.256.256.256"));
//        System.out.println(o.validIPAddress("2001:0db8:85a3::8A2E:0370:7334"));
//        System.out.println(o.validIPAddress("1e1.4.5.6"));
//        System.out.println(o.validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334:"));
        System.out.println(o.validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:7334"));
//        System.out.println(o.validIPAddress("172.16.254.1"));
//        System.out.println(o.validIPAddress("172.16.254.1"));
//        System.out.println(o.validIPAddress("172.16.254.1"));
//        System.out.println(o.validIPAddress("172.16.254.1"));
//        System.out.println(o.validIPAddress("172.16.254.1"));


        Pattern pattern = Pattern.compile("[0-9a-fA-F][0-9a-fA-F]?[0-9a-fA-F]?[0-9a-fA-F]?");
        System.out.println(pattern.matcher("2001").matches());
        System.out.println(pattern.matcher("0db8").matches());
        System.out.println(pattern.matcher("85a3").matches());
        System.out.println(pattern.matcher("0").matches());
        System.out.println(pattern.matcher("0").matches());
        System.out.println(pattern.matcher("8A2E").matches());
        System.out.println(pattern.matcher("0370").matches());
    }

    Pattern pattern = Pattern.compile("[0-9a-fA-F][0-9a-fA-F]?[0-9a-fA-F]?[0-9a-fA-F]?");
    Pattern patternNum = Pattern.compile("[0-9]+");
    public String validIPAddress(String IP) {
        if (IP.indexOf('.') > 0) {
            return valideIPV4(IP);
        } else if (IP.indexOf(':') >= 0) {
            return valideIPV6(IP);
        } else {
            return "Neither";
        }
    }

    String valideIPV4(String IP) {
        if(IP.endsWith(".")) {
            return "Neither";
        }
        String[] groups = IP.split("\\.");
        if (groups.length != 4) {
            return "Neither";
        }
        for (String group : groups) {
            if (group.length() == 0) {
                return "Neither";
            }
            if (group.length() > 3) {
                return "Neither";
            }
            if (group.startsWith("0") && group.length() > 1) {
                return "Neither";
            }

            if (!patternNum.matcher(group).matches()) {
                return "Neither";
            }

            int groupValue = Integer.parseInt(group);
            if (groupValue < 0) {
                return "Neither";
            }
            if (groupValue > 255) {
                return "Neither";
            }
        }

        return "IPv4";
    }

    String valideIPV6(String IP) {
        if (IP.endsWith(":") && !IP.endsWith("::")) {
            return "Neither";
        }
        String[] group = null;
        if (IP.indexOf("::") >= 0) {
            String[] biggroup = IP.split("::");
            if (biggroup.length != 2) {
                return "Neither";
            }

            if (biggroup[0].length() + biggroup[1].length() > 6) {
                return "Neither";
            }

            if (biggroup[0].length() == 0) {
                group = biggroup[1].split(":");
            } else if (biggroup[1].length() == 0) {
                group = biggroup[0].split(":");
            } else {
                String[] g0 = biggroup[0].split(":");
                String[] g1 = biggroup[1].split(":");

                group = new String[g0.length + g1.length];
                for (int i = 0; i < g0.length; i++) {
                    group[i] = g0[i];
                }
                for (int i = 0; i < g1.length; i++) {
                    group[i + g0.length] = g1[i];
                }
            }

        } else {
            group = IP.split(":+");
            if (group.length != 8) {
                return "Neither";
            }
        }


        for (String s : group) {
            if (s.length() == 0) {
                return "Neither";
            }



            if(!pattern.matcher(s).matches()) {
                return "Neither";
            }
        }


        return "IPv6";
    }
}
