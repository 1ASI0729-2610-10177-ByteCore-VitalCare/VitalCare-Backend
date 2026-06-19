package com.bytecore.vitalcare.platform.suscription.domain.model.aggregates;

import com.bytecore.vitalcare.platform.suscription.domain.model.commands.CreateSubscriptionCommand;
import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionPlan;
import com.bytecore.vitalcare.platform.suscription.domain.model.valueobjects.SubscriptionStatus;
import com.bytecore.vitalcare.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
@Getter
public class Subscription extends AbstractDomainAggregateRoot<Subscription> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Enumerated(EnumType.STRING)
    @Setter
    private SubscriptionPlan plan;

    @Setter
    private BigDecimal price;

    @Setter
    private LocalDate startDate;

    @Setter
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Setter
    private SubscriptionStatus status;

    @Setter
    private Long userId;

    public Subscription() {}

    public Subscription(SubscriptionPlan plan, BigDecimal price, LocalDate startDate, LocalDate endDate, Long userId) {
        this.plan = plan;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = SubscriptionStatus.PENDING;
        this.userId = userId;
    }

    public Subscription(CreateSubscriptionCommand command) {
        this(command.plan(), command.price(), command.startDate(), command.endDate(), command.userId());
    }

    public void activate() { this.status = SubscriptionStatus.ACTIVE; }

    public void cancel() { this.status = SubscriptionStatus.CANCELED; }

    public void expire() { this.status = SubscriptionStatus.EXPIRED; }

    public boolean isActive() { return this.status == SubscriptionStatus.ACTIVE; }
}
