/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Track_Model_ProtoType;

import java.util.*;

/**
 *
 * @author dah106
 */
public interface TrackControllerInterface 
{   
    /*
        TrainLine = 1<--'Red', 2<--'Green'
        Track Controller calls the setTrainLine(int TrainLine) to indentify which train line first
    */
    public void setTrainLine(int TrainLine);
    
    public void addTrackModelBlock(int StartBlockNumber, int EndBlockNumber);
    
    public void removeTrackModelBlock(int StartBlockNumber, int EndBlockNumber);
 
    public void toggleTrackModelSwitch(int BlockNumber);
    
    public void toggleRailWayCrossingBarSignal(int BlockNumber);
    
    //SignalLightStatus = 0<--Stop, 1<--Decelerate, 2<--Proceed, 3<--Accelerate
    public void controlRailWayLightSignal(int BlockNumber, int SignalLightStatus);
    
    public boolean operateTrackModelTrackHeater();
    
    public HashMap<Integer,Block> getRedlLineInformation();
 
    public HashMap<Integer,Block> getGreenLineInformation();
    
    public void forceFailure(int BlockNumber, int FailMode);
    
    
}
