package nl.hu.sd.s2.sds2project2025404finders.info;

import java.util.Arrays;

public class CampingInfoRepository {

    /** Single instance of CampingInfo that represents the current configuration. DUMMY DATA! */
    private static CampingInfo campingInfo = new CampingInfo(
            "Camping Bacon is een rustig gelegen camping midden in het groen. " +
                    "Ruime plaatsen, moderne faciliteiten en een ontspannen sfeer.",
            "Baconlaan 1, 1234 AB Utrecht",
            "08:00 – 20:00",
            Arrays.asList(
                    "Laag (jan–mrt)",
                    "Midden (apr–jun)",
                    "Hoog (jul–aug)",
                    "Nazomer (sep–okt)"
            ),
            "info@campingbacon.nl",
            "+31 (0)30 123 45 67",
            Arrays.asList(
                    "Zwembad met ligweide",
                    "Speeltuin en sportveld",
                    "Sanitairgebouw met douches",
                    "Wasserette",
                    "Campingwinkel met verse broodjes",
                    "Gratis wifi op het terrein"
            ),
            Arrays.asList(
                    "Na 22:00 graag stilte op het terrein.",
                    "Honden zijn welkom, maar altijd aangelijnd.",
                    "Afval graag gescheiden in de containers.",
                    "Open vuur alleen op aangewezen plekken."
            )
    );

    /**
     * Returns the current camping information.
     */
    public static CampingInfo getCampingInfo() {
        return campingInfo;
    }

    /**
     * Updates the stored camping information with new values.
     *
     * @param updated new camping information coming from the client
     * @param updated new camping information coming from the client
     * @return the updated CampingInfo instance
     */
    public static CampingInfo updateCampingInfo(CampingInfo updated) {
        if (updated == null) {
            return campingInfo;
        }

        campingInfo.setAbout(updated.getAbout());
        campingInfo.setAddress(updated.getAddress());
        campingInfo.setReceptionHours(updated.getReceptionHours());
        campingInfo.setSeasons(updated.getSeasons());
        campingInfo.setContactEmail(updated.getContactEmail());
        campingInfo.setContactPhone(updated.getContactPhone());
        campingInfo.setFacilities(updated.getFacilities());
        campingInfo.setHouseRules(updated.getHouseRules());

        return campingInfo;
    }
}
