package com.jmill29.tvtrackerfrontend.dto;

import java.time.LocalDateTime;

/**
 * DTO representing a TV show object returned by the backend.
 * This class is used to deserialize the response from the `/api/shows` endpoint.
 */
public class ShowResponse {

    /** Unique identifier for the TV show */
    private int id;

    /** Name/title of the show */
    private String name;

    /** Brief description or synopsis of the show */
    private String description;

    /** Publicly accessible image URL (poster/cover art) */
    private String imageUrl;

    /** Total number of episodes in the show */
    private int numEpisodes;

    /** Year the show was released */
    private short releaseYear;

    /** Timestamp of when this show entry was created (backend-managed) */
    private LocalDateTime createdAt;

    /**
     * Default constructor required for deserialization.
     */
    public ShowResponse() {
    }

    /**
     * Full constructor for initializing a ShowResponse object.
     *
     * @param id           show ID
     * @param name         show name
     * @param description  brief description of the show
     * @param imageUrl     URL to show image
     * @param numEpisodes  number of episodes
     * @param releaseYear  year of release
     * @param createdAt    timestamp of creation
     */
    public ShowResponse(int id, String name, String description, String imageUrl,
                        int numEpisodes, short releaseYear, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.numEpisodes = numEpisodes;
        this.releaseYear = releaseYear;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getNumEpisodes() {
        return numEpisodes;
    }

    public void setNumEpisodes(int numEpisodes) {
        this.numEpisodes = numEpisodes;
    }

    public short getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns a readable string representation of the ShowResponse object.
     */
    @Override
    public String toString() {
        return "ShowResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", numEpisodes=" + numEpisodes +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
