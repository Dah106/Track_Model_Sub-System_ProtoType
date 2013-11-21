/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Track_Model_ProtoType;

public class Transponder 
{	
	public String nextStation;

	public Transponder()
	{
            nextStation = "DEFAULT_STATION";
        }

	public void setNextStation(String StationName)
	{
            nextStation = StationName;
	}
    
        public String getNextStation()
        {
            return this.nextStation;
        }

}
