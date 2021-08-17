package APP;

public class Company {
    private String name;
    private String email;
    private String phoneNumber;
    private String officialWebsite;
    private String password;

    public Company() {}
    public Company(String name, String email, String phoneNumber, String officialWebsite, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.officialWebsite = officialWebsite;
        this.password = password;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getOfficialWebsite() { return officialWebsite; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setOfficialWebsite(String officialWebsite) { this.officialWebsite = officialWebsite; }
    public void setPassword(String password) { this.password = password; }

}
