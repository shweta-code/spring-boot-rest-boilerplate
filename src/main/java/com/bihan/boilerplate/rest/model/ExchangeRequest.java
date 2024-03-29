package com.bihan.boilerplate.rest.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "exchange_request")
public class ExchangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requesterUser;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiverUser;

    @ManyToOne
    @JoinColumn(name = "requested_item_id", nullable = false)
    private Item requestedItem;

    @ManyToOne
    @JoinColumn(name = "offered_item_id", nullable = false)
    private Item offeredItem;

    private String status;

    // To add timestamp fields here

    public enum ExchangeRequestStatus {
        IN_PROGRESS("IN_PROGRESS"),
        CREATED("CREATED"),
        ACCEPTED("ACCEPTED"),
        REJECTED("REJECTED"), // terminal state
        CANCELLED("CANCELLED"), // terminal state
        EXPIRED("EXPIRED"), // terminal state
        PARTIALLY_COMPLETE("PARTIALLY_COMPLETE"),
        COMPLETED("COMPLETED"); // terminal state

        private String value;

        ExchangeRequestStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static class Builder {
        private final ExchangeRequest exchangeRequest;

        public Builder() {
            this.exchangeRequest = new ExchangeRequest();
        }

        public Builder withRequesterUser(User requesterUser) {
            this.exchangeRequest.requesterUser = requesterUser;
            return this;
        }

        public Builder withReceiverUser(User receiverUser) {
            this.exchangeRequest.receiverUser = receiverUser;
            return this;
        }

        public Builder withRequestedItem(Item requestedItem) {
            this.exchangeRequest.requestedItem = requestedItem;
            return this;
        }

        public Builder withOfferedItem(Item offeredItem) {
            this.exchangeRequest.offeredItem = offeredItem;
            return this;
        }

        public Builder withStatus(String status) {
            this.exchangeRequest.status = status;
            return this;
        }

        public ExchangeRequest build() {
            return this.exchangeRequest;
        }
    }
}
