package nl.hu.sd.s2.sds2project2025404finders.info;

import java.util.List;

public class CampingInfo {

    /** Short description of the camping, used in the "Over de camping" card. */
    private String about;

    /** Full address string shown in the practical information card. */
    private String address;

    /** Reception opening hours (e.g. "08:00 – 20:00"). */
    private String receptionHours;

    /** List of season descriptions (e.g. "Low (Jan–Mar)", "High (Jul–Aug)"). */
    private List<String> seasons;

    /** Public contact email address. */
    private String contactEmail;

    /** Public contact phone number. */
    private String contactPhone;

    /** List of available facilities (pool, playground, etc.). */
    private List<String> facilities;

    /** List of house rules displayed on the info page. */
    private List<String> houseRules;

    /** Empty constructor required for JSON deserialization. */
    public CampingInfo() {
    }

    /**
     * Convenience constructor to populate all fields at once.
     */
    public CampingInfo(String about,
                       String address,
                       String receptionHours,
                       List<String> seasons,
                       String contactEmail,
                       String contactPhone,
                       List<String> facilities,
                       List<String> houseRules) {
        this.about = about;
        this.address = address;
        this.receptionHours = receptionHours;
        this.seasons = seasons;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.facilities = facilities;
        this.houseRules = houseRules;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceptionHours() {
        return receptionHours;
    }

    public void setReceptionHours(String receptionHours) {
        this.receptionHours = receptionHours;
    }

    public List<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<String> seasons) {
        this.seasons = seasons;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public List<String> getHouseRules() {
        return houseRules;
    }

    public void setHouseRules(List<String> houseRules) {
        this.houseRules = houseRules;
    }
}
