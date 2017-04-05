package project.hugbunadarverkefni2;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by svein on 26.3.2017.
 */

public class Event implements Serializable {

    String id;
    String numberOfAttendees;
    Date startTime, endTime;
    String title, coverPhotosSrc, profilePhotoSrc, description, venue;

    public Event( String id, String numberOfAttendees, Date startTime, Date endTime, String title, String coverPhotosSrc, String profilePhotoSrc, String description, String venue) {
        this.id = id;
        this.numberOfAttendees = numberOfAttendees;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.coverPhotosSrc = coverPhotosSrc;
        this.profilePhotoSrc = profilePhotoSrc;
        this.description = description;
        this.venue = venue;
    }

    public String getId() {
        return id;
    }

    public String getNumberOfAttendees() {
        return numberOfAttendees;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverPhotosSrc() {
        return coverPhotosSrc;
    }

    public String getProfilePhotoSrc() {
        return profilePhotoSrc;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }
}
