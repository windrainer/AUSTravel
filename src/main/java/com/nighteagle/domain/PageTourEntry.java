package com.nighteagle.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PageTourEntry.
 */
@Entity
@Table(name = "page_tour_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PageTourEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "heading")
    private String heading;

    @Column(name = "sub_title")
    private String subTitle;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "img_url_1")
    private String imgUrl1;

    @Column(name = "img_url_2")
    private String imgUrl2;

    @Column(name = "css_style")
    private String cssStyle;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time")
    private LocalDate createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time")
    private LocalDate updateTime;

    @Column(name = "col_width")
    private String colWidth;

    @OneToOne
    @JoinColumn(unique = true)
    private Tour tour;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PageTourEntry name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeading() {
        return heading;
    }

    public PageTourEntry heading(String heading) {
        this.heading = heading;
        return this;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public PageTourEntry subTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public PageTourEntry content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl1() {
        return imgUrl1;
    }

    public PageTourEntry imgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
        return this;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public PageTourEntry imgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
        return this;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    public String getCssStyle() {
        return cssStyle;
    }

    public PageTourEntry cssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
        return this;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getCreateBy() {
        return createBy;
    }

    public PageTourEntry createBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public PageTourEntry createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public PageTourEntry updateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public PageTourEntry updateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public String getColWidth() {
        return colWidth;
    }

    public PageTourEntry colWidth(String colWidth) {
        this.colWidth = colWidth;
        return this;
    }

    public void setColWidth(String colWidth) {
        this.colWidth = colWidth;
    }

    public Tour getTour() {
        return tour;
    }

    public PageTourEntry tour(Tour tour) {
        this.tour = tour;
        return this;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
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
        PageTourEntry pageTourEntry = (PageTourEntry) o;
        if (pageTourEntry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pageTourEntry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PageTourEntry{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", heading='" + getHeading() + "'" +
            ", subTitle='" + getSubTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", imgUrl1='" + getImgUrl1() + "'" +
            ", imgUrl2='" + getImgUrl2() + "'" +
            ", cssStyle='" + getCssStyle() + "'" +
            ", createBy='" + getCreateBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateBy='" + getUpdateBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", colWidth='" + getColWidth() + "'" +
            "}";
    }
}
