package com.space.service.validator;

import com.space.model.Ship;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;

public class Validator {
	public static boolean isValid(Ship ship) {
		if (ship.getName() == null || !isValid(ship.getName())) return false;
		if (ship.getPlanet() == null || !isValid(ship.getPlanet())) return false;
		if (ship.getShipType() == null) return false;
		if (ship.getCrewSize() == null || !isValid(ship.getCrewSize())) return false;
		if (ship.getSpeed() == null) return false;
		ship.setSpeed((Math.round(ship.getSpeed() * 100) / 100d));
		if (!isValid(ship.getSpeed())) return false;
		if (ship.getProdDate() == null || !isValid(ship.getProdDate())) return false;
		return true;
	}

	public static boolean isValid(String nameOrPlanet) {
		return !(nameOrPlanet.length() == 0 || nameOrPlanet.length() > 50);
	}

	public static boolean isValid(Integer crewSize) {
		return !(crewSize < 1 || crewSize > 9999);
	}

	public static boolean isValid(Double speed) {
		return !(speed < 0.01 || speed > 0.99);
	}

	public static boolean isValid(Date prodDate) {
		return !(prodDate.getYear() < 900 || prodDate.getYear() > 1119);
	}

}
