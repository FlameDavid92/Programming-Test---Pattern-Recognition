package it.welld.patternrecognition.dtos;

import java.util.Set;

public class LinesDTO {
	private Set<LineDTO> lines;

	public LinesDTO(Set<LineDTO> lines) {
		super();
		this.lines = lines;
	}

	public Set<LineDTO> getLines() {
		return lines;
	}

	public void setLines(Set<LineDTO> lines) {
		this.lines = lines;
	}
	
}
