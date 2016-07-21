/**********************************************************************************
 * Course: CSC 240 Java Programming                                               *
 * Instructor: Carmella Garcia                                                    *
 * Term Project: Right Polygon Area Calculator                                    *
 * Due 08/08/15                                                                   *
 * Author: Marj Frederick                                                         *
 *                                                                                *
 *  This class is part of the Right Polygon Area Calculator                       *
 *  This class constructs a polygon and calculates various parameters and         *
 *  values (area) that are displayed by the Right Polygon Area Calculator GUI     *
 *                                                                                *
 *  Definitions:                                                                  *
 *  The apothem is a line from the polygon center perpendicular to the center     *
 *      of a side. In this class its length is calculated                         * 
 *   The circum-radius is a line from the polygon center to a vertex. In this     *
 *      class its length is calculated.                                           *
 *                                                                                *
 *  Formulas:  s: side length,  n = number of sides,                              *
 *             a = apothem, r = circum-radius    using  trigonometry              *
 *             angle = 360 / (2 * n)  = 180 / n    (using 1/2 side length)        *
 *                                                                                *
 *  Given: n & s:  r = (0.5 * s) / sin(angle)   &   a = (0.5 * s) / tan(angle)    *
 *                 area = (s * s * n) / 4 tan(angle)                              *
 *                                                                                *
 *  Given: n & r:  s = 2 * r * sin(angle)   &   a = r * cos(angle)                *
 *                 area = 0.5 * r * r * n * sin(2*angle)                          *                                                            *
 *                                                                                *
 *  Given: n & a:  r = a / cos(angle)   &   s = 2 * a * tan(angle)                *
 *                 area = r * r * n * tan(angle)                                  *
 *                                                                                *
 *********************************************************************************/
package PolygonAreaCalculator;

public class MyPolygon
{
	private int numSides = 6;  // default number of sides
	private String parameterType = "S";  // default
	private double parameterValue = 6.0;  // default side length value
	
	// Default constructor
	MyPolygon ()
	{		
	}
	
	// Constructor
	MyPolygon(int sides, String parameterType, double parameterValue)
	{
		setNumSides (sides);
		setParameterType (parameterType);
		setParameterValue (parameterValue);
	}
//--------------------------------------------------------------------------
	// get method to get the width of the matrix
	public int getNumSides()
	{
		return numSides;
	}
//--------------------------------------------------------------------------
	// get the parameter type the user selected
	public String getParameterType()
	{
		return parameterType;
	}
//--------------------------------------------------------------------------
	// get the parameter type the user selected
	public double getParameterValue()
	{
		return parameterValue;
	}
//--------------------------------------------------------------------------
	// set method to set the width of the matrix
	public void setNumSides(int sides)
	{
		this.numSides = sides;
	}
// ----------------------------------------------------------------------------
	// set method to set the parameter type
	public void setParameterType (String parameterType)
	{
		this.parameterType = parameterType;
	}
// ----------------------------------------------------------------------------
	// set method to set the parameter value
	public void setParameterValue (double parameter)
	{
		this.parameterValue = parameter;
	}
//------------------------------------------------------------------------------
	// calculate the angle (central angle / 2) given the number of sides
	public double calculateAngle()
	{
		return (Math.PI / getNumSides());
	}
//------------------------------------------------------------------------------
	// method to calculate the apothem (center to center of a side) of the polygon
	public double calculateApothem()
	{
	    String pType = getParameterType();
	    double given = getParameterValue();
	    
		double apothem = given; // default value - initialized
		
		// ParameterType: user gave side length
		if ("S".equals(pType))
		{
	        apothem = (0.5 * given) / Math.tan(calculateAngle());  
	    }
		// ParameterType: user gave the circum-radius (polygon center to a vertex)
		else if ("R".equals(pType))
		{
		   apothem = given * Math.cos(calculateAngle());
		}
		// ParameterType: user gave the apothem (polygon center to center of side)
		else if ("A".equals(pType))
		{
			apothem = given;   
		}
        return apothem;
	}
//------------------------------------------------------------------------------
	// calculate the circum-radius
	public double calculateRadius()
	{
	    String pType = getParameterType();
	    double given = getParameterValue();
		double radius = given;  // initialized value
		
		// ParameterType: user gave side length
		if ("S".equals(pType))
		{
	        radius = (0.5 * given) / Math.sin(calculateAngle());  
	    }
		// ParameterType: user gave the circum-radius (polygon center to a vertex)
		else if ("R".equals(pType))
		{
		   radius = given;
		}
		// ParameterType: user gave the apothem (polygon center to center of side)
		else if ("A".equals(pType))
		{
			radius = given / Math.cos(calculateAngle());   
		}
		return radius;		
	}
//------------------------------------------------------------------------------
    // calculate the side length of the polygon
	public double calculateSideLength()
	{
	    String pType = getParameterType();
	    double given = getParameterValue();
		double sideLength = given;  // default value - initialized
		
		// ParameterType: user gave side length
		if ("S".equals(pType))
		{
	        sideLength = given;  
	    }
		// ParameterType: user gave the circum-radius (polygon center to a vertex)
		else if ("R".equals(pType))
		{
		   sideLength = 2.0 * given * Math.sin(calculateAngle());
		}
		// ParameterType: user gave the apothem (polygon center to center of side)
		else if ("A".equals(pType))
		{
			sideLength = 2.0 * given * Math.tan(calculateAngle());   
		}				
		return sideLength;
	}
//------------------------------------------------------------------------------
	// calculate the area of the polygon
	public double calculateArea ()
	{		
	    double area = 0;  // default area if invalid calculation
	    int n = getNumSides();
	    double given = getParameterValue();
	    String pType = getParameterType();
	    
	   // ParameterType: user gave side length
	   if ("S".equals(pType))
	   {
          area = (given * given * n) / (4 * Math.tan(Math.PI / n));
	   }
	   // ParameterType: user gave the circum-radius (polygon center to a vertex)
	   else if ("R".equals(pType))
	   {
		  area = 0.5 * (given * given * n) * Math.sin(2 * Math.PI / n);
	   }
	   // ParameterType: user gave the apothem (polygon center to center of side)
	   else if ("A".equals(pType))
	   {
		   area = (given * given * n) * Math.tan(Math.PI / n);
	   }
		return area;
	}	
}  // end MyPolygon class
