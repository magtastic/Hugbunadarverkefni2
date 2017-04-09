package project.hugbunadarverkefni2;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by svein on 26.3.2017.
 */

public class Event implements Serializable {
    Date startTime, endTime;
    String title, coverPhotosSrc, profilePhotoSrc, numberOfAttendees, description, venue, lng, lat, id;

    public Event( String id, String numberOfAttendees, Date startTime, Date endTime, String title, String coverPhotosSrc, String profilePhotoSrc, String description, String venue, String lng, String lat) {
        this.id = id;
        this.numberOfAttendees = numberOfAttendees;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.coverPhotosSrc = coverPhotosSrc;
        this.profilePhotoSrc = profilePhotoSrc;
        this.description = description;
        this.venue = venue;
        this.lng = lng;
        this.lat = lat;
    }

    public String getId() {return id;}
    public String getNumberOfAttendees() {return numberOfAttendees;}
    public Date getStartTime() {return startTime;}
    public Date getEndTime() {return endTime;}
    public String getTitle() {return title;}
    public String getCoverPhotosSrc() {return coverPhotosSrc;}
    public String getProfilePhotoSrc() {return profilePhotoSrc;}
    public String getDescription() {return description;}
    public String getVenue() {return venue;}
    public String getLng() {return lng;}
    public String getLat() {return lat;}
}
