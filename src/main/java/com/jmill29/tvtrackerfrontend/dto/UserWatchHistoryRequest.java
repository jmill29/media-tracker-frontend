package com.jmill29.tvtrackerfrontend.dto;

import com.jmill29.tvtrackerfrontend.enums.WatchStatus;

/**
 * DTO used for sending a user's watch status for a specific show to the backend.
 * This is used for POST and PUT requests to the /api/watch-history endpoint.
 */
public class UserWatchHistoryRequest {

    /** The ID of the TV show the user is tracking */
    private int showId;

    /** The watch status (e.g., Currently Watching, Completed, etc.) */
    private WatchStatus status;

    /**
     * Default constructor required for deserialization.
     */
    public UserWatchHistoryRequest() {
    }

    /**
     * Constructs a UserWatchHistoryRequest with showId and status.
     *
     * @param showId the unique ID of the TV show
     * @param status the user's current watch status for the show
     */
    public UserWatchHistoryRequest(int showId, WatchStatus status) {
        this.showId = showId;
        this.status = status;
    }

    /**
     * @return the ID of the show
     */
    public int getShowId() {
        return showId;
    }

    /**
     * @param showId the ID of the show to set
     */
    public void setShowId(int showId) {
        this.showId = showId;
    }

    /**
     * @return the user's current watch status
     */
    public WatchStatus getStatus() {
        return status;
    }

    /**
     * @param status the watch status to set
     */
    public void setStatus(WatchStatus status) {
        this.status = status;
    }

    /**
     * Custom string representation excluding null-safe status handling.
     *
     * @return String describing the watch history request
     */
    @Override
    public String toString() {
        return "UserWatchHistoryRequest{" +
                "showId=" + showId +
                ", status=" + (status != null ? status.name() : null) +
                '}';
    }
}
