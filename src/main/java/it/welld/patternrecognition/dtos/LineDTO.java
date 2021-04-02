package it.welld.patternrecognition.dtos;

import java.util.Set;

import it.welld.patternrecognition.daos.PointDAO;


public class LineDTO {
	private Set<PointDAO> line;

	public LineDTO(Set<PointDAO> line) {
		super();
		this.line = line;
	}

	public Set<PointDAO> getLine() {
		return line;
	}

	public void setLine(Set<PointDAO> line) {
		this.line = line;
	}
	
}
