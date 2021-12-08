package demetra.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account extends PanacheEntityBase {

    @Id
    public String amazonId;

    @Column(nullable = false)
    public String alexaId;

}
