package ar.edu.uai.lia.lookiate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter @Setter
@ToString
public class User {
    @Id
    private String userId;
    private String userName;
    private String userLocation;
    private String subscriptionType;
    private Date createdAt;
    private Boolean isActive;

    public void updateProfile(String userName) {
        this.userName = userName;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(isActive);
    }

    public void changeSubscription(String newType) {
        this.subscriptionType = newType;
    }

}
