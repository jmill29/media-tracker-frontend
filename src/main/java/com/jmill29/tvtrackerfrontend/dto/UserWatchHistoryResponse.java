package com.jmill29.tvtrackerfrontend.dto;

/**
 * DTO representing a TV show that a user has added to their personal watch history.
 * This is used as a response object when retrieving watch history entries from the backend.
 */
public class UserWatchHistoryResponse {

    /** The ID of the TV show being tracked */
    private int showId;

    /** The name/title of the TV show */
    private String showName;

    /** A brief description of the TV show */
    private String description;

    /** URL to the show's image (poster, cover, etc.) */
    private String imageUrl;

    /** The user's current watch status for this show (e.g., "Watching", "Completed") */
    private String status;

    /**
     * Default constructor for deserialization.
     */
    public UserWatchHistoryResponse() {
    }

    /**
     * Full constructor to initialize all fields.
     *
     * @param showId      the ID of the TV show
     * @param showName    the title of the TV show
     * @param description a brief description of the show
     * @param imageUrl    a publicly accessible image URL
     * @param status      the watch status (as a readable string)
     */
    public UserWatchHistoryResponse(int showId, String showName, String description, String imageUrl, String status) {
        this.showId = showId;
        this.showName = showName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * String representation for debugging/logging.
     */
    @Override
    public String toString() {
        return "UserWatchHistoryResponse{" +
                "showId=" + showId +
                ", showName='" + showName + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
