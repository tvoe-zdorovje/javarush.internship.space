package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.filter.SearchCriteria;
import com.space.service.ShipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@RestController()
@RequestMapping("rest")
public class ShipController {

	@Autowired
	private ShipServiceImpl service;

	@PersistenceContext
	private EntityManager entityManager;


	@PostMapping(value = "/ships")
	public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
		if (ship.isUsed() == null) ship.setUsed(false);
		Ship response = service.save(ship);
		if (response==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(service.save(ship), HttpStatus.OK);
	}




	@GetMapping(value = "/ships")
	public List<Ship> read(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "planet", required = false) String planet,
			@RequestParam(name = "shipType", required = false) ShipType shipType,
			@RequestParam(name = "after", required = false) Long after,
			@RequestParam(name = "before", required = false) Long before,
			@RequestParam(name = "isUsed", required = false) Boolean isUsed,
			@RequestParam(name = "minSpeed", required = false) Double minSpeed,
			@RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
			@RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
			@RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
			@RequestParam(name = "minRating", required = false) Double minRating,
			@RequestParam(name = "maxRating", required = false) Double maxRating,
			@RequestParam(name = "order", required = false, defaultValue = "ID") ShipOrder order,
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize) {


		List<SearchCriteria> criteria = createCriteria(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);


		return service.findAll(criteria, pageNumber, pageSize, order).getContent();
	}


	@GetMapping(value = "/ships/count")
	public long getCount(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "planet", required = false) String planet,
			@RequestParam(name = "shipType", required = false) ShipType shipType,
			@RequestParam(name = "after", required = false) Long after,
			@RequestParam(name = "before", required = false) Long before,
			@RequestParam(name = "isUsed", required = false) Boolean isUsed,
			@RequestParam(name = "minSpeed", required = false) Double minSpeed,
			@RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
			@RequestParam(name = "minCrewSize", required = false) Integer minCrewSize,
			@RequestParam(name = "maxCrewSize", required = false) Integer maxCrewSize,
			@RequestParam(name = "minRating", required = false) Double minRating,
			@RequestParam(name = "maxRating", required = false) Double maxRating,
			@RequestParam(name = "order", required = false, defaultValue = "ID") ShipOrder order,
			@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

		List<SearchCriteria> criteria = createCriteria(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);

		return service.count(criteria);

	}

	private List<SearchCriteria> createCriteria(String name, String planet, ShipType shipType, Long after, Long before, Boolean isUsed, Double minSpeed,
	                                            Double maxSpeed, Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
		List<SearchCriteria> criteria = new ArrayList<>();
		if (name != null) criteria.add(new SearchCriteria("name", ":", name));
		if (planet != null) criteria.add(new SearchCriteria("planet", ":", planet));
		if (shipType != null) criteria.add(new SearchCriteria("shipType", "=", shipType));
		if (after != null) criteria.add(new SearchCriteria("prodDate", ">", new Date(after)));
		if (before != null) criteria.add(new SearchCriteria("prodDate", "<", new Date(before)));
		if (isUsed != null) criteria.add(new SearchCriteria("isUsed", "=", isUsed));
		if (minSpeed != null) criteria.add(new SearchCriteria("speed", ">=", minSpeed));
		if (maxSpeed != null) criteria.add(new SearchCriteria("speed", "<=", maxSpeed));
		if (minCrewSize != null) criteria.add(new SearchCriteria("crewSize", ">=", minCrewSize));
		if (maxCrewSize != null) criteria.add(new SearchCriteria("crewSize", "<=", maxCrewSize));
		if (minRating != null) criteria.add(new SearchCriteria("rating", ">=", minRating));
		if (maxRating != null) criteria.add(new SearchCriteria("rating", "<=", maxRating));
		return criteria;
	}

	@GetMapping(value = "/ships/{id}")
	public ResponseEntity<Ship> read(@PathVariable(name = "id") Long id) {
		if (id<1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (!service.exist(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Ship response = service.findById(id);
		return response==null? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/ships/{id}")
	public ResponseEntity<Ship> update(@PathVariable(name = "id") Long id, @RequestBody(required = false) Ship ship) {
		if (id<1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (!service.exist(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			Ship response = service.update(id, ship);
		return response==null?new ResponseEntity<>(HttpStatus.BAD_REQUEST):new ResponseEntity<>(response,HttpStatus.OK);
	}

	@DeleteMapping(value = "/ships/{id}")
	public ResponseEntity<Ship> delete(@PathVariable(name = "id") Long id) {
		if (id<1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (service.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


}
