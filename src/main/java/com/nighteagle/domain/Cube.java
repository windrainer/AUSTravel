package com.nighteagle.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cube.
 */
@Entity
@Table(name = "cube")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cube implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "col_width")
    private String colWidth;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "heading")
    private String heading;

    @Column(name = "content")
    private String content;

    @Column(name = "style")
    private String style;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Cube imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getColWidth() {
        return colWidth;
    }

    public Cube colWidth(String colWidth) {
        this.colWidth = colWidth;
        return this;
    }

    public void setColWidth(String colWidth) {
        this.colWidth = colWidth;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Cube subTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getHeading() {
        return heading;
    }

    public Cube heading(String heading) {
        this.heading = heading;
        return this;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getContent() {
        return content;
    }

    public Cube content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStyle() {
        return style;
    }

    public Cube style(String style) {
        this.style = style;
        return this;
    }

    public void setStyle(String style) {
        this.style = style;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cube cube = (Cube) o;
        if (cube.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cube.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cube{" +
            "id=" + getId() +
            ", imgUrl='" + getImgUrl() + "'" +
            ", colWidth='" + getColWidth() + "'" +
            ", subTitle='" + getSubTitle() + "'" +
            ", heading='" + getHeading() + "'" +
            ", content='" + getContent() + "'" +
            ", style='" + getStyle() + "'" +
            "}";
    }
}
