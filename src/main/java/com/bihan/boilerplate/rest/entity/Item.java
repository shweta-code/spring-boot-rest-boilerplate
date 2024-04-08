package com.bihan.boilerplate.rest.entity;

import com.bihan.boilerplate.rest.entity.baseEntity.VersionedBaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "item")
public class Item extends VersionedBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Category name is required")
    private Category category;

    @NotNull(message = "description is required")
    private String description;

    @NotNull
    @Column(name = "is_listed")
    private boolean isListed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User ownerUser;

    private void setCategory(Category category) {
        this.category = category;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setListed(boolean listed) {
        isListed = listed;
    }

    private void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public static class ItemBuilder {
        private Long id;
        private Category category;
        private String description;
        private boolean isListed;

        private User ownerUser;

        public ItemBuilder() {
        }

        public ItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ItemBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ItemBuilder ownerUser(User ownerUser) {
            this.ownerUser = ownerUser;
            return this;
        }

        public ItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ItemBuilder isListed(boolean isListed) {
            this.isListed = isListed;
            return this;
        }

        public Item build() {
            Item item = new Item();
            item.setId(id);
            item.setCategory(category);
            item.setDescription(description);
            item.setListed(isListed);
            item.setOwnerUser(ownerUser);
            return item;
        }
    }

}
