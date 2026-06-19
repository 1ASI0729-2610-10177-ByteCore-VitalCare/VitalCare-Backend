package com.bytecore.vitalcare.platform.iam.domain.model.aggregates;

import com.bytecore.vitalcare.platform.iam.domain.model.entities.SupportTicket;
import com.bytecore.vitalcare.platform.iam.domain.model.entities.UserPreference;
import com.bytecore.vitalcare.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
public class User extends AbstractDomainAggregateRoot<User> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(insertable = false, updatable = false)
	@Setter
	private Long id;
	@Setter
	private String name;
	@Setter
	private String email;
	@Setter
	private String password;
	@Embedded
	@Setter
	private UserPreference preference;
	@OneToMany
	private List<SupportTicket> supportTickets;

    public User() {
        this.supportTickets = new ArrayList<>();
        this.preference = UserPreference.getDefault();
    }

    public User(String name, String email, String password) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User addSupportTicket(SupportTicket ticket) {
        this.supportTickets.add(ticket);
        return this;
    }
}
