package org.example.ebookstore.entity;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    List<OrderItem> orderItems;
    Integer bookCount;
    Integer moneyCount;
}
