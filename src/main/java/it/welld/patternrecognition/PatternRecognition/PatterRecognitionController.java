package it.welld.patternrecognition.PatternRecognition;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatterRecognitionController {
	
	@PostMapping(value = "/point")
	public ResponseEntity<SimpleResponse> addPoint(@Valid @RequestBody PointDTO point, BindingResult bindingResult) throws BindingException {
		//service add point
		if(bindingResult.hasErrors()) {
			throw new BindingException("Invalid content.");
		}
		return new ResponseEntity<SimpleResponse>(new SimpleResponse(201, "Point added."), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/space")
	public ResponseEntity<Set<PointDTO>> getSpace() {
		//service getSpace
		return ResponseEntity.ok(new HashSet<>());
	}
	
	@GetMapping(value = "/lines/{n}")
	public ResponseEntity<Set<Set<PointDTO>>> getLines( @PathVariable("n") Long minPoints) {
		//service getLines
		return ResponseEntity.ok(new HashSet<>());
	}
	
	@DeleteMapping(value = "/space")
	public ResponseEntity<SimpleResponse> deleteSpace(){
		//service deleteSpace
		return ResponseEntity.ok(new SimpleResponse(200,"Space deleted."));
	}
}
