package model.entity.ellipse.builder;

import model.entity.band.Band;
import model.entity.ellipse.Ellipse;

public class EllipseBuilderDirector {
	
	public static Ellipse create(Double majorAxis, Double minorAxis, Double angleOfRotation, Band band)
	{
		Ellipse obj = new Ellipse();
		
		obj.setMajorAxis(majorAxis);
		obj.setMinorAxis(minorAxis);
		obj.setAngleOfRotation(angleOfRotation);
		obj.setBand(band);
		
		return obj;
	}
}
