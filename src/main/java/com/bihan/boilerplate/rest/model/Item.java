package com.bihan.boilerplate.rest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Category name is required")
    private Category category;

    @NotNull(message = "description is required")
    private String description;

    @NotNull
    @Column(name = "is_listed")
    private boolean isListed;

    // To add timestamp fields here, created_at, modified_at, created_by, modified_by

    public static class ItemBuilder {
        private Long id;
        private Category category;
        private String description;
        private boolean isListed;

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
            return item;
        }
    }

}
