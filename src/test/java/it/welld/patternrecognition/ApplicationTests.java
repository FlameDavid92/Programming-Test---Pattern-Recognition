package it.welld.patternrecognition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.welld.patternrecognition.daos.PointDAO;
import it.welld.patternrecognition.dtos.SpaceDTO;
import it.welld.patternrecognition.services.PatternRecognitionService;

@SpringBootTest
class ApplicationTests {
	
	@Autowired 
	private PatternRecognitionService patternRecognitionService;

	@Test
	void contextLoads() {
	}
	
	@Test
	void Test1() {
		Set<PointDAO> points = new HashSet<>();
		points.add(new PointDAO(1.0,1.0));
		points.add(new PointDAO(1.0,2.0));
		points.add(new PointDAO(2.0,1.0));
		points.add(new PointDAO(2.0,2.0));
		SpaceDTO space = new SpaceDTO(points);
		patternRecognitionService.loadSpace(space);
		
		assertEquals(patternRecognitionService.getSpace().getSpace().size(), 4);
		assertEquals(patternRecognitionService.getLines(2l).getLines().size(), 6);
		assertEquals(patternRecognitionService.getLines(3l).getLines().size(), 0);
		
		patternRecognitionService.deleteSpace();
		assertEquals(patternRecognitionService.getSpace().getSpace().size(), 0);
	}
}
