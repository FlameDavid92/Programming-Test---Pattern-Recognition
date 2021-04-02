package it.welld.patternrecognition.daos;

public class StraightSlopedLineDAO {
	
	private Double slope;
	private Double yIntercept;
	
	public StraightSlopedLineDAO(PointDAO pt0, PointDAO pt1){
	    this.slope = (pt0.getY() - pt1.getY()) / (pt0.getX() - pt1.getX());
	    this.yIntercept = pt0.getY() - this.slope * pt0.getX();
	}
	
	public Boolean isInLine(PointDAO pt) {
		return pt.getY().equals( (pt.getX()*slope)+yIntercept );
	}

	public Double getSlope() {
		return slope;
	}

	public void setSlope(Double slope) {
		this.slope = slope;
	}

	public Double getyIntercept() {
		return yIntercept;
	}

	public void setyIntercept(Double yIntercept) {
		this.yIntercept = yIntercept;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((slope == null) ? 0 : slope.hashCode());
		result = prime * result + ((yIntercept == null) ? 0 : yIntercept.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StraightSlopedLineDAO other = (StraightSlopedLineDAO) obj;
		if (slope == null) {
			if (other.slope != null)
				return false;
		} else if (!slope.equals(other.slope))
			return false;
		if (yIntercept == null) {
			if (other.yIntercept != null)
				return false;
		} else if (!yIntercept.equals(other.yIntercept))
			return false;
		return true;
	}
	
	
}
