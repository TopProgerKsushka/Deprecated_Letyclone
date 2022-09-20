package org.ksushka.letyclone.emailservice.model;

import javax.persistence.*;

@Entity
@Table(name = "lc_shop")
public class Shop {
    public Shop() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private String logoUrl;

    @Column(nullable = false)
    private Double maxCashback;

    private String description;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrl() {
        return url;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public Double getMaxCashback() {
        return maxCashback;
    }

    public String getDescription() {
        return description;
    }
}
