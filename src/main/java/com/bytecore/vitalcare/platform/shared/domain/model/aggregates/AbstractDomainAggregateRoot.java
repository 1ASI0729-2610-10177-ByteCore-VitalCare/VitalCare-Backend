package com.bytecore.vitalcare.platform.shared.domain.model.aggregates;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;

/**
 * Base class for all domain aggregate roots.
 *
 * @param <T> the concrete aggregate root type
 */
@NullMarked
public abstract class AbstractDomainAggregateRoot<T extends AbstractDomainAggregateRoot<T>>
        extends AbstractAggregateRoot<T> {

    protected void registerDomainEvent(Object event) { super.registerEvent(event); }

    public Collection<Object> domainEvents() { return super.domainEvents(); }

    public void clearDomainEvents() { super.clearDomainEvents(); }
}
