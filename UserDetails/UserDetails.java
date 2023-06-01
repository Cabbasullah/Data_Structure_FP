
package UserDetails;
public class UserDetails {
    private String username;
    private Object password;

    public UserDetails(String username, Object password) {
        this.username = username;
        this.password = password;
    }

    public String getuserName() {
        return username;
    }

    public Object getPassword() {
        return password;
    }

    public void setuserName(String uname) {
        this.username = uname;
    }

    public void setPassword(Object psswrd) {
        this.password = psswrd;
    }
}
