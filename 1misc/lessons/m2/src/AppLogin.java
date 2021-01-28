import java.util.HashSet;

public class AppLogin {

    public static void main(String[] args) {
        HashSet<Login> uniqueLogins = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            uniqueLogins.add(new Login("cspacey0@myspace.com", "Carita", "Spacey"));
            uniqueLogins.add(new Login("sbretherick1@va.gov", "Sammy", "Bretherick"));
            uniqueLogins.add(new Login("zcripwell2@dot.gov", "Zia", "Cripwell"));
        }
        System.out.println("size: " + uniqueLogins.size());

        for (Login login : uniqueLogins) {
            System.out.println(login.getEmailAddress());
        }
    }

}
