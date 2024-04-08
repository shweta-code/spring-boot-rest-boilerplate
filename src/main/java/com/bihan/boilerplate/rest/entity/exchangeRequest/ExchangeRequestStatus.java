package com.bihan.boilerplate.rest.entity.exchangeRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum ExchangeRequestStatus {
    IN_PROGRESS("IN_PROGRESS") {
        @Override
        protected void addActionsOnReceiverSide() {
            this.nextActionsOnReceiverSide = Collections.EMPTY_SET;
        }

        @Override
        protected void addActionsOnRequesterSide() {
            this.nextActionsOnRequesterSide = Collections.EMPTY_SET;
        }
    },
    CREATED("CREATED") {
        @Override
        protected void addActionsOnReceiverSide() {
            Set<ExchangeRequestUserActions> nextStatus = new HashSet<>();
            nextStatus.add(ExchangeRequestUserActions.ACCEPT);
            nextStatus.add(ExchangeRequestUserActions.REJECT);
            this.nextActionsOnReceiverSide = nextStatus;
        }

        @Override
        protected void addActionsOnRequesterSide() {
            Set<ExchangeRequestUserActions> nextStatus = new HashSet<>();
            nextStatus.add(ExchangeRequestUserActions.CANCEL);
            this.nextActionsOnRequesterSide = nextStatus;
        }
    },
    ACCEPTED("ACCEPTED") {
        @Override
        protected void addActionsOnReceiverSide() {
            Set<ExchangeRequestUserActions> nextStatus = new HashSet<>();
            nextStatus.add(ExchangeRequestUserActions.COMPLETE);
            this.nextActionsOnReceiverSide = nextStatus;
        }

        @Override
        protected void addActionsOnRequesterSide() {
            Set<ExchangeRequestUserActions> nextStatus = new HashSet<>();
            nextStatus.add(ExchangeRequestUserActions.CANCEL);
            this.nextActionsOnRequesterSide = nextStatus;
        }
    },
    REJECTED("REJECTED") {
        @Override
        protected void addActionsOnReceiverSide() {
            this.nextActionsOnReceiverSide = Collections.EMPTY_SET;
        }

        @Override
        protected void addActionsOnRequesterSide() {
            this.nextActionsOnRequesterSide = Collections.EMPTY_SET;
        }
    }, // terminal state
    CANCELLED("CANCELLED") {
        @Override
        protected void addActionsOnReceiverSide() {
            this.nextActionsOnReceiverSide = Collections.EMPTY_SET;
        }

        @Override
        protected void addActionsOnRequesterSide() {
            this.nextActionsOnRequesterSide = Collections.EMPTY_SET;
        }
    },  // terminal state
    EXPIRED("EXPIRED") {
        @Override
        protected void addActionsOnReceiverSide() {
            this.nextActionsOnReceiverSide = Collections.EMPTY_SET;
        }

        @Override
        protected void addActionsOnRequesterSide() {
            this.nextActionsOnRequesterSide = Collections.EMPTY_SET;
        }
    }, // terminal state
    COMPLETED("COMPLETED") {
        @Override
        protected void addActionsOnReceiverSide() {
            this.nextActionsOnReceiverSide = Collections.EMPTY_SET;
        }

        @Override
        protected void addActionsOnRequesterSide() {
            this.nextActionsOnRequesterSide = Collections.EMPTY_SET;
        }
    }; // terminal state

    private String value;
    protected Set<ExchangeRequestUserActions> nextActionsOnReceiverSide;
    protected Set<ExchangeRequestUserActions> nextActionsOnRequesterSide;

    ExchangeRequestStatus(String value) {

        this.value = value;
        this.addActionsOnReceiverSide();
        this.addActionsOnRequesterSide();
    }

    public String getValue() {
        return value;
    }

    protected abstract void addActionsOnReceiverSide();

    protected abstract void addActionsOnRequesterSide();

    public Set<ExchangeRequestUserActions> getNextActionsOnReceiverSide() {
        return nextActionsOnReceiverSide;
    }

    public Set<ExchangeRequestUserActions> getNextActionsOnRequesterSide() {
        return nextActionsOnRequesterSide;
    }

    // Method to get enum instance from value
    public static ExchangeRequestStatus fromValue(String value) {
        for (ExchangeRequestStatus enumValue : ExchangeRequestStatus.values()) {
            if (enumValue.value.equals(value)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
