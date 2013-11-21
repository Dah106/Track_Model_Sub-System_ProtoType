/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Track_Model_ProtoType;


public class TrackCircuit 
{
    private int failureMode; //0<--Normal Operation, 1<--Broken Rail, 2<--Power Failure, 3<--Track Circuit Failure
    private boolean trackOccupancy; //true if train is present, false if train is not on the track

    public TrackCircuit()
    {
    	failureMode = 0; //set to Normal Operation
        trackOccupancy = false;
    }

    
    public void setFailureMode(int FailureMode)
    {
    	failureMode = FailureMode;

    }

    public int getFailureMode()
    {
    	return failureMode;
    }

    public boolean setOccupancy(boolean occupancy)
    {   
        trackOccupancy = occupancy;
        return trackOccupancy;
    }

}
