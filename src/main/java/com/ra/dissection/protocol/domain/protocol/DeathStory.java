package com.ra.dissection.protocol.domain.protocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lukaszkaleta
 * @since 01.05.13 09:21
 */
public class DeathStory implements Serializable {

    public enum BookType {
        MAIN,
        AM
    }

    private static final long serialVersionUID = 2013050109210000001l;

    /** Type of book. */
    private BookType bookType = BookType.MAIN;

    /** Book number about death. */
    private String bookNumber;

    /** Hospital Id in which patient died. */
    private Long hospitalId;

    /** Hospital wards in which patient was before death. */
    private List<HospitalWardEntry> hospitalWardEntries = new ArrayList<HospitalWardEntry>();

    /** Hospital story. */
    private List<DeathStoryEntry> storyEntries = new ArrayList<DeathStoryEntry>();

    public DeathStory() {
        for (DeathStoryName deathStoryName : DeathStoryName.values()) {
            storyEntries.add(new DeathStoryEntry(deathStoryName));
        }
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public List<HospitalWardEntry> getHospitalWardEntries() {
        return hospitalWardEntries;
    }

    public void setHospitalWardEntries(List<HospitalWardEntry> hospitalWardEntries) {
        this.hospitalWardEntries = hospitalWardEntries;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public List<DeathStoryEntry> getStoryEntries() {
        return storyEntries;
    }

    public void setStoryEntries(List<DeathStoryEntry> storyEntries) {
        this.storyEntries = storyEntries;
    }

    public DeathStoryEntry getStoryEntry(DeathStoryName deathStoryName) {
        if (deathStoryName != null) {
            for (DeathStoryEntry deathStoryEntry : storyEntries) {
                if (deathStoryName.equals(deathStoryEntry.getSourceName())) {
                    return deathStoryEntry;
                }
            }
        }
        return null;
    }
}
