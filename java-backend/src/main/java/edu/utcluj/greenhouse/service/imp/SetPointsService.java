package edu.utcluj.greenhouse.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.utcluj.greenhouse.exception.OutOfBoundsException;
import edu.utcluj.greenhouse.model.SetPoints;
import edu.utcluj.greenhouse.repository.ISetPointsRepository;
import edu.utcluj.greenhouse.service.ISetPointsService;

@Component
public class SetPointsService implements ISetPointsService {

	@Autowired
	private ISetPointsRepository setPointsRepository;

	@Override
	public void save(SetPoints setPoints) throws OutOfBoundsException {
		SetPoints sp = setPointsRepository.find(setPoints.getTemperatureSetPoint(), setPoints.getHumiditySetPoint());
		if (sp == null) {
			// new set points
			checkSetPoints(setPoints);
			setPoints.setDate(new Date(System.currentTimeMillis()));
			setPointsRepository.save(setPoints);
		} else {
			// update set points date if set points exist
			sp.setDate(new Date(System.currentTimeMillis()));
			setPointsRepository.save(sp);
		}
	}

	@Override
	public SetPoints find() {
		return setPointsRepository.findLates();
	}

	@Override
	public List<SetPoints> findAll() {
		return setPointsRepository.findAll();
	}

	private void checkSetPoints(SetPoints sp) throws OutOfBoundsException {
		System.out.println(sp.getTemperatureSetPoint() + " " +sp.getHumiditySetPoint());
		if (sp.getTemperatureSetPoint() < 20 || sp.getTemperatureSetPoint() > 35)
			throw new OutOfBoundsException("Limitele referintei pentru temepratura, sunt : 20 si 35 de grade Celsius!");
		if (sp.getHumiditySetPoint() < 40 || sp.getHumiditySetPoint() > 70)
			throw new OutOfBoundsException("Limitele referintei pentru umiditate, sunt : 40 si 70 de procente!");
	}
}
