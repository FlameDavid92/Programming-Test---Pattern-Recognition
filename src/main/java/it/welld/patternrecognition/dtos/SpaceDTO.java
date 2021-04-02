package it.welld.patternrecognition.dtos;

import java.util.Set;

import it.welld.patternrecognition.daos.PointDAO;

public class SpaceDTO {
	private Set<PointDAO> space;

	public SpaceDTO(Set<PointDAO> space) {
		super();
		this.space = space;
	}

	public Set<PointDAO> getSpace() {
		return space;
	}

	public void setSpace(Set<PointDAO> space) {
		this.space = space;
	}
	
}
