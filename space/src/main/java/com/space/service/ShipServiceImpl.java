package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import com.space.service.filter.SearchCriteria;
import com.space.service.filter.ShipSpecificationsBuilder;
import com.space.service.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service()
public class ShipServiceImpl implements ShipService {

	@Autowired
	private ShipRepository shipRepository;


	@Override
	public Ship save(Ship ship) {
		if (!Validator.isValid(ship)) return null;
		if (ship.isUsed() == null) ship.setUsed(false);
		ship.createRating();
		shipRepository.saveAndFlush(ship);
		return ship;
	}
	@Override
	public Page<Ship> findAll(List<SearchCriteria> criteria, Integer pageNumber, Integer pageSize, ShipOrder order) {
		if (criteria.isEmpty()) return shipRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName())));
		else return shipRepository.findAll(new ShipSpecificationsBuilder(criteria).build(), PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName())));
	}

	@Override
	public long count(List<SearchCriteria> criteria) {
		return criteria.isEmpty() ? shipRepository.count() : shipRepository.count(new ShipSpecificationsBuilder(criteria).build());
	}

	@Override
	public Ship findById(Long id) {
		return shipRepository.findById(id).orElse(null);
	}

	@Override
	public Ship update(Long id, Ship ship) {
		Ship dbShip = shipRepository.findById(id).orElse(null);
		if (dbShip==null) return null;

		if (ship.getName()!=null) dbShip.setName(ship.getName());
		if (ship.getPlanet()!=null) dbShip.setPlanet(ship.getPlanet());
		if (ship.getSpeed()!=null) dbShip.setSpeed(ship.getSpeed());
		if (ship.getCrewSize()!=null) dbShip.setCrewSize(ship.getCrewSize());
		if (ship.getShipType()!=null) dbShip.setShipType(ship.getShipType());
		if (ship.isUsed()!=null) dbShip.setUsed(ship.isUsed());
		if (ship.getProdDate()!=null) dbShip.setProdDate(ship.getProdDate());

		if (!Validator.isValid(dbShip)) return null;
		return save(dbShip);

	}

	@Override
	public boolean delete(Long id) {
		if (shipRepository.existsById(id)) {
			shipRepository.deleteById(id);
			return !shipRepository.existsById(id);
		}
		return false;
	}

	@Override
	public boolean exist(Long id) {
		return shipRepository.existsById(id);
	}
}
