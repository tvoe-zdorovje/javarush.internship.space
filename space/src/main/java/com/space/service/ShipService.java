package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.service.filter.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShipService {
	Ship save(Ship ship);

	Page<Ship> findAll(List<SearchCriteria> criteria, Integer pageNumber, Integer pageSize, ShipOrder order);
	Ship findById(Long id);
	long count(List<SearchCriteria> criteria);

	Ship update(Long id,Ship ship);

	boolean delete(Long id);

	boolean exist(Long id);
}
