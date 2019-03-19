package Server.Model;

public class Account{

    private String account;
    private String password;

    public Account() {
    }

    public Account(String account,String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return "Account="+account+",  Password="+password;
    }
}
