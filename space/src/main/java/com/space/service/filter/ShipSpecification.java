package com.space.service.filter;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ShipSpecification implements Specification<Ship> {

	private final SearchCriteria criteria;

	public ShipSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Specification<Ship> and(Specification<Ship> other) {
		return Specification.where(this).and(other);
	}

	@Override
	public Specification<Ship> or(Specification<Ship> other) {
		return Specification.where(this).or(other);
	}

	@Override
	public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {


		switch (criteria.getKey()) {
			case "shipType":
				return shipTypePredicate(root, query, criteriaBuilder);
			case "prodDate":
				return datePredicate(root, query, criteriaBuilder);
			case "isUsed":
				return booleanPredicate(root, query, criteriaBuilder);

			default:
				break;
		}


		switch (criteria.getOperation()) {

			case ">=":
				return criteriaBuilder.greaterThanOrEqualTo(
						root.get(criteria.getKey()), criteria.getValue().toString());
			case ">":
				return criteriaBuilder.greaterThan(
						root.get(criteria.getKey()), criteria.getValue().toString());
			case "<=":
				return criteriaBuilder.lessThanOrEqualTo(
						root.get(criteria.getKey()), criteria.getValue().toString());

			case "<":
				return criteriaBuilder.lessThan(
						root.get(criteria.getKey()), criteria.getValue().toString());
			case ":":
				return criteriaBuilder.like(
						root.get(criteria.getKey()), "%" + criteria.getValue() + "%");

			case "=":
				return criteriaBuilder.equal(
						root.get(criteria.getKey()), criteria.getValue());

		}


		return null;
	}

	private Predicate datePredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		switch (criteria.getOperation()) {

			case ">":
				return criteriaBuilder.greaterThan(
						root.get(criteria.getKey()).as(java.sql.Date.class), criteriaBuilder.literal(criteria.getValue()).as(java.sql.Date.class));
			case "<":
				return criteriaBuilder.lessThanOrEqualTo(
						root.get(criteria.getKey()).as(java.sql.Date.class), criteriaBuilder.literal(criteria.getValue()).as(java.sql.Date.class));
			case "=":
				return criteriaBuilder.equal(
						root.get(criteria.getKey()).as(java.sql.Date.class), criteriaBuilder.literal(criteria.getValue()).as(java.sql.Date.class));
		}
		return null;
	}

	private Predicate booleanPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if ("=".equals(criteria.getOperation())) {
			return criteriaBuilder.equal(
					root.get(criteria.getKey()).as(Boolean.class), criteriaBuilder.literal(criteria.getValue()).as(Boolean.class));
		}
		return null;
	}

	private Predicate shipTypePredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		if ("=".equals(criteria.getOperation())) {
			return criteriaBuilder.equal(
					root.get(criteria.getKey()).as(ShipType.class), criteriaBuilder.literal(criteria.getValue()).as(ShipType.class));
		}
		return null;
	}


}
