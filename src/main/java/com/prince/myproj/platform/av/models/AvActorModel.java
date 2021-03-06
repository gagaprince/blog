package com.prince.myproj.platform.av.models;

import java.io.Serializable;

public class AvActorModel implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column av_actor.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column av_actor.actor
     *
     * @mbggenerated
     */
    private String actor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column av_actor.source_url
     *
     * @mbggenerated
     */
    private String sourceUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column av_actor.id
     *
     * @return the value of av_actor.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column av_actor.id
     *
     * @param id the value for av_actor.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column av_actor.actor
     *
     * @return the value of av_actor.actor
     *
     * @mbggenerated
     */
    public String getActor() {
        return actor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column av_actor.actor
     *
     * @param actor the value for av_actor.actor
     *
     * @mbggenerated
     */
    public void setActor(String actor) {
        this.actor = actor == null ? null : actor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column av_actor.source_url
     *
     * @return the value of av_actor.source_url
     *
     * @mbggenerated
     */
    public String getSourceUrl() {
        return sourceUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column av_actor.source_url
     *
     * @param sourceUrl the value for av_actor.source_url
     *
     * @mbggenerated
     */
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AvActorModel other = (AvActorModel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getActor() == null ? other.getActor() == null : this.getActor().equals(other.getActor()))
            && (this.getSourceUrl() == null ? other.getSourceUrl() == null : this.getSourceUrl().equals(other.getSourceUrl()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getActor() == null) ? 0 : getActor().hashCode());
        result = prime * result + ((getSourceUrl() == null) ? 0 : getSourceUrl().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table av_actor
     *
     * @mbggenerated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", actor=").append(actor);
        sb.append(", sourceUrl=").append(sourceUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}