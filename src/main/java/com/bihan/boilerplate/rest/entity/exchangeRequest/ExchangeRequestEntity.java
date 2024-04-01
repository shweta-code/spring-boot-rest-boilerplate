package com.bihan.boilerplate.rest.entity.exchangeRequest;

import com.bihan.boilerplate.rest.entity.Item;
import com.bihan.boilerplate.rest.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "exchange_request")
public class ExchangeRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // To add create/modify here, created_at, modified_at, created_by, modified_by

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

    @Version
    private int version;

    public enum ExchangeRequestUserActions {
        ACCEPT("Accept"),
        REJECT("Reject"),
        CANCEL("Cancel"),
        COMPLETE("Complete");

        ExchangeRequestUserActions(String value) {
            this.value = value;
        }

        private String value;
    }

    public static class Builder {
        private final ExchangeRequestEntity exchangeRequestEntity;

        public Builder() {
            this.exchangeRequestEntity = new ExchangeRequestEntity();
        }

        public Builder withRequesterUser(User requesterUser) {
            this.exchangeRequestEntity.requesterUser = requesterUser;
            return this;
        }

        public Builder withReceiverUser(User receiverUser) {
            this.exchangeRequestEntity.receiverUser = receiverUser;
            return this;
        }

        public Builder withRequestedItem(Item requestedItem) {
            this.exchangeRequestEntity.requestedItem = requestedItem;
            return this;
        }

        public Builder withOfferedItem(Item offeredItem) {
            this.exchangeRequestEntity.offeredItem = offeredItem;
            return this;
        }

        public Builder withStatus(String status) {
            this.exchangeRequestEntity.status = status;
            return this;
        }

        public ExchangeRequestEntity build() {
            return this.exchangeRequestEntity;
        }
    }
}

// TODO - Created indexed on FK

