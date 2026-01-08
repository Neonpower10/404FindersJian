package nl.hu.sd.s2.sds2project2025404finders.domain;

/**
 * Domain model representing a single Camping Prebuild.
 *
 * This class is used by the API (CampingDataResource),
 * and later can be stored/retrieved through a DAO.
 */
public class CampingData {

    private int id;
    private String name;
    private String description;
    private String imageHomepage;

    public CampingData() {
        // Required empty constructor for JSON serialization
    }

    public CampingData(int id, String name, String description, String imageHomepage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageHomepage = imageHomepage;
    }

    // ----- Getters -----

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageHomepage() { return imageHomepage; }


    public String getDescription() {
        return description;
    }

    // ----- Setters -----

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageHomepage(String image) { this.imageHomepage = image; }


    public void setDescription(String description) {
        this.description = description;
    }
}
