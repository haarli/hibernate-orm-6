package org.hibernate.bugs.model;


import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
//@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "hibernate.test")
@Access(AccessType.FIELD)
public class Parent {

    @Id
    private String objectId;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    private Child favouriteChild;


    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String id) {
        this.objectId = id;
    }

    public Child getFavouriteChild() {
        return favouriteChild;
    }

    public void setFavouriteChild(Child favouriteChild) {
        this.favouriteChild = favouriteChild;
    }
}
