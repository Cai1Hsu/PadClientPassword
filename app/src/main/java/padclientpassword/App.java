package padclientpassword;

import padclientpassword.AESSet;

public class App {
    public static void main(String[] args) {
        System.out.println(
            "Password:" +
            decrypt());
    }
    public static String decrypt(){
        String aespwd = "**Place the Encrypted Password Here**";
        AESSet aSet = new AESSet();
        try {
            byte[] byaes = aSet.decrypt(aespwd);
            return new String(byaes, 0, byaes.length);
        } catch (Exception e) {
            return null;
        }
    }
}