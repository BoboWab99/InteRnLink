package APP;

public class InternshipOffer {
    private int internshipId;
    private String companyName;
    private String internshipTitle;
    private String companyDescription;
    private String internshipDescription;
    private String requirements;
    private String applicationDeadline;
    private String manager;

    public int getInternshipId() { return internshipId; }
    public String getCompanyName() { return companyName; }
    public String getInternshipTitle() { return internshipTitle; }
    public String getCompanyDescription() { return companyDescription; }
    public String getInternshipDescription() { return internshipDescription; }
    public String getRequirements() { return requirements; }
    public String getApplicationDeadline() { return applicationDeadline; }
    public String getManager() { return manager; }

    public void setInternshipId(int id) { this.internshipId = id; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setInternshipTitle(String internshipTitle) { this.internshipTitle = internshipTitle; }
    public void setCompanyDescription(String companyDescription) { this.companyDescription = companyDescription; }
    public void setInternshipDescription(String internshipDescription) { this.internshipDescription = internshipDescription; }
    public void setRequirements(String requirements) { this.requirements = requirements; }
    public void setApplicationDeadline(String applicationDeadline) { this.applicationDeadline = applicationDeadline; }
    public void setManager(String manager) { this.manager = manager; }

    @Override
    public String toString() {
        return "InternshipOffer{" +
                "companyName='" + companyName + '\'' +
                ", internshipTitle='" + internshipTitle + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", internshipDescription='" + internshipDescription + '\'' +
                ", requirements='" + requirements + '\'' +
                ", applicationDeadline='" + applicationDeadline + '\'' +
                ", manager='" + manager + '\'' +
                '}';
    }

}
