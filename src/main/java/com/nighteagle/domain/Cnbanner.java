package com.nighteagle.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cnbanner.
 */
@Entity
@Table(name = "cnbanner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cnbanner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "background")
    private String background;

    @Column(name = "logo")
    private String logo;

    @Column(name = "text_1")
    private String text1;

    @Column(name = "text_2")
    private String text2;

    @Column(name = "url_1")
    private String url1;

    @Column(name = "url_2")
    private String url2;

    @Column(name = "css_1")
    private String css1;

    @Column(name = "css_2")
    private String css2;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "activated")
    private Boolean activated;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackground() {
        return background;
    }

    public Cnbanner background(String background) {
        this.background = background;
        return this;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getLogo() {
        return logo;
    }

    public Cnbanner logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getText1() {
        return text1;
    }

    public Cnbanner text1(String text1) {
        this.text1 = text1;
        return this;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public Cnbanner text2(String text2) {
        this.text2 = text2;
        return this;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getUrl1() {
        return url1;
    }

    public Cnbanner url1(String url1) {
        this.url1 = url1;
        return this;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public Cnbanner url2(String url2) {
        this.url2 = url2;
        return this;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getCss1() {
        return css1;
    }

    public Cnbanner css1(String css1) {
        this.css1 = css1;
        return this;
    }

    public void setCss1(String css1) {
        this.css1 = css1;
    }

    public String getCss2() {
        return css2;
    }

    public Cnbanner css2(String css2) {
        this.css2 = css2;
        return this;
    }

    public void setCss2(String css2) {
        this.css2 = css2;
    }

    public String getName() {
        return name;
    }

    public Cnbanner name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isActivated() {
        return activated;
    }

    public Cnbanner activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
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
        Cnbanner cnbanner = (Cnbanner) o;
        if (cnbanner.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cnbanner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cnbanner{" +
            "id=" + getId() +
            ", background='" + getBackground() + "'" +
            ", logo='" + getLogo() + "'" +
            ", text1='" + getText1() + "'" +
            ", text2='" + getText2() + "'" +
            ", url1='" + getUrl1() + "'" +
            ", url2='" + getUrl2() + "'" +
            ", css1='" + getCss1() + "'" +
            ", css2='" + getCss2() + "'" +
            ", name='" + getName() + "'" +
            ", activated='" + isActivated() + "'" +
            "}";
    }
}
