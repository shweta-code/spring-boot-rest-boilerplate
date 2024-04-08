package com.bihan.boilerplate.rest.entity.exchangeRequest;

import com.bihan.boilerplate.rest.entity.Item;
import com.bihan.boilerplate.rest.entity.User;
import com.bihan.boilerplate.rest.entity.baseEntity.BaseEntity;
import com.bihan.boilerplate.rest.entity.baseEntity.VersionedBaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "exchange_request")
public class ExchangeRequestEntity extends VersionedBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requesterUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiverUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_item_id", nullable = false)
    private Item requestedItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offered_item_id", nullable = false)
    private Item offeredItem;

    private String status;

    public void setStatus(String status) {
        this.status = status;
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

// TODO - Created indexes on FK

