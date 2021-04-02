package it.welld.patternrecognition.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.welld.patternrecognition.daos.PointDAO;
import it.welld.patternrecognition.dtos.LinesDTO;
import it.welld.patternrecognition.dtos.SimpleResponse;
import it.welld.patternrecognition.dtos.SpaceDTO;
import it.welld.patternrecognition.exceptions.BindingException;
import it.welld.patternrecognition.exceptions.DeleteException;
import it.welld.patternrecognition.services.PatternRecognitionService;

@RestController
public class Controller {
	
	@Autowired
	PatternRecognitionService patternRecognitionService;
	
	@PostMapping(value = "/point")
	public ResponseEntity<SimpleResponse> addPoint(@RequestBody PointDAO point) throws BindingException {
		if(point.getX() == null || point.getY() == null){
			throw new BindingException("Invalid content.");
		}
		patternRecognitionService.addPoint(point);
		return new ResponseEntity<SimpleResponse>(new SimpleResponse(201, "Point added."), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/space")
	public ResponseEntity<SpaceDTO> getSpace() {
		return ResponseEntity.ok(patternRecognitionService.getSpace());
	}
	
	@GetMapping(value = "/lines/{n}")
	public ResponseEntity<LinesDTO> getLines( @PathVariable("n") Long minPoints) throws BindingException {
		if(minPoints < 0) throw new BindingException("Invalid number of points.");
		return ResponseEntity.ok(patternRecognitionService.getLines(minPoints));
	}
	
	@DeleteMapping(value = "/space")
	public ResponseEntity<SimpleResponse> deleteSpace() throws DeleteException{
		try {
			patternRecognitionService.deleteSpace();			
			return ResponseEntity.ok(new SimpleResponse(200,"Space deleted."));
		}catch(Exception e) {
			throw new DeleteException();
		}
	}
}
