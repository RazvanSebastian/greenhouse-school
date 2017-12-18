package edu.utcluj.greenhouse.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.utcluj.greenhouse.exception.OutOfBoundsException;
import edu.utcluj.greenhouse.model.SetPoints;

@Service
public interface ISetPointsService {
	
	/**
	 * Store new set points
	 * @param setPoints
	 * @throws OutOfBoundsException 
	 */
	public void save(SetPoints setPoints) throws OutOfBoundsException;
	
	/**
	 * Find the recent set points 
	 * @return SetPoints object with temperature and humidity values
	 */
	public SetPoints find();
	
	/**
	 * Find all set points
	 * @return
	 */
	public List<SetPoints> findAll();
}
