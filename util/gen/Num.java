package util.gen;

/**
 * Math and statistical static methods.
 */
public class Num {

	/**Gets the average of the integers bracketed and including the start and stop.
	 * (ie 3,6  returns the average of 3+4+5+6/4= 4.5)*/
	public static float getAverageInts (int start, int end){
		int endOne = end+1;
		int len = endOne-start;
		int sum =0;
		for (int i= start; i<endOne; i++) sum+=i;
		return (float)sum/(float)len;
	}
}