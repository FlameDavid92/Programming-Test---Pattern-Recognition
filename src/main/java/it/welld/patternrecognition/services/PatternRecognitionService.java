package it.welld.patternrecognition.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import it.welld.patternrecognition.daos.PointDAO;
import it.welld.patternrecognition.daos.StraightSlopedLineDAO;
import it.welld.patternrecognition.dtos.LineDTO;
import it.welld.patternrecognition.dtos.LinesDTO;
import it.welld.patternrecognition.dtos.SpaceDTO;

@Service
@CacheConfig(cacheNames= {"patternrecognition"})
public class PatternRecognitionService {
	
	private static Set<PointDAO> space = new HashSet<>();
	
	private static HashMap<Double,Set<PointDAO>> verticalLines = new HashMap<>(); //same x ---> x : {point, point,...} ATT: size set 1 no line
	private static HashMap<Double,Set<PointDAO>> horizontalLines = new HashMap<>(); //same y ---> y : {point, point,...}  ATT: size set 1 no line
	private static HashMap<StraightSlopedLineDAO,Set<PointDAO>> straightSlopedLines = new HashMap<>(); // (slope,yIntercept) ---> {point, point,...}
	
	@Cacheable
	public SpaceDTO getSpace() {
		return new SpaceDTO(space);
	}
	
	@Cacheable
	public LinesDTO getLines(Long n) {
		Set<LineDTO> resp = new HashSet<>();
		if(n <= 0) return new LinesDTO(resp);
		
		if(n==1) n=2l; //a line segment is defined if have at least two points of the space, n=1 or n=2 -> all lines
		
		final long check = n;
		verticalLines.entrySet().stream().forEach(e -> {
			if(e.getValue().size() >= check) resp.add(new LineDTO(e.getValue()));
		});
		horizontalLines.entrySet().stream().forEach(e -> {
			if(e.getValue().size() >= check) resp.add(new LineDTO(e.getValue()));
		});
		straightSlopedLines.entrySet().stream().forEach(e -> {
			if(e.getValue().size() >= check) resp.add(new LineDTO(e.getValue()));
		});
		
		return new LinesDTO(resp);
	}
	
	@Caching(evict = { @CacheEvict(cacheNames="patternrecognition", allEntries=true) })
	public void addPoint(PointDAO newPoint) {
		
		if(verticalLines.containsKey(newPoint.getX())) {
			verticalLines.get(newPoint.getX()).add(newPoint);
		}else {
			Set<PointDAO> newSet = new HashSet<>();
			newSet.add(newPoint);
			verticalLines.put(newPoint.getX(), newSet);
		}
		
		if(horizontalLines.containsKey(newPoint.getY())) {
			horizontalLines.get(newPoint.getY()).add(newPoint);
		}else {
			Set<PointDAO> newSet = new HashSet<>();
			newSet.add(newPoint);
			horizontalLines.put(newPoint.getY(), newSet);
		}
		
		space.stream().forEach(p->{
			if(!p.getX().equals(newPoint.getX()) && !p.getY().equals(newPoint.getY())) {
				StraightSlopedLineDAO line = new StraightSlopedLineDAO(newPoint, p);
				if(straightSlopedLines.containsKey(line)) {
					straightSlopedLines.get(line).add(newPoint);
				}else {
					Set<PointDAO> newSet = new HashSet<>();
					newSet.add(newPoint);
					newSet.add(p);
					straightSlopedLines.put(line, newSet);
				}
			}
		});
		space.add(newPoint);
			
	}
	
	@Caching(evict = { @CacheEvict(cacheNames="patternrecognition", allEntries=true) })
	public void deleteSpace() {
		space = new HashSet<>();
		verticalLines = new HashMap<>();
		horizontalLines = new HashMap<>();
		straightSlopedLines = new HashMap<>();
	}
}
