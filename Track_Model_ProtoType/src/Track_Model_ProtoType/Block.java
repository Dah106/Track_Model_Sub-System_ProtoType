/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Track_Model_ProtoType;

/**
 *
 * @author dah106
 */
public class Block 
{   
    /*
        nextBlock: reference to next block
        previousBlock: reference to previous block
        nextOptionalBlock: reference to (optional) next block. Used in certain blocks wherever a switch machine is found.
    */
    private Block nextBlock;
    private Block previousBlock;
    private Block nextOptionalBlock;
    
    /*
        Information for each block
        See the raw data file in courseweb
    */
    
    private String trainLine;
    private int blockID;
    private String sectionName;
    private double blockLength;
    private double grade;
    private double speedLimit;
    private double elevation;
    private double cumulativeElevation;
    private String stationName;
    private String switchMachine;
    private String trackType;
    private String railwayCrossing; 
    
    /*
        Three data types
        Signal denotes the switch machine, railway crossing bar and railway light signal
        TrackCircuit denotes the track circuit which mainly deals with train location update and failure mode
        Transponder denotes the device that stores the station name. The position of each transponder will be setup later
    */
    public   Signal controlSignal;
    public TrackCircuit trackCircuit;
    public Transponder transponder;
    private Info info;
    /*
        branchDirection denotes the position of switch machine
        trafficLight denotes the status of railway sigal
        crossingBars denotes the position of railway crossing bars
    
    */
    public boolean branchDirection; // false<--default branch to 'NextBlock', true<--branch to 'OptionalNextBlock'
    public int trafficLight; // 0<--Stop, 1<--Decelerate, 2<--Proceed, 3<--Accelerate
    public int crossingBars; // 0<--Closed, 1<--Open

    /*
        trackStatus denotes track status for maintenance requirement
    */
    private int trackStatus; // 100<-- Normal, -100<-- Closed

    /*
        trackOccupancy denotes track occupancy 
    */
    private boolean trackOccupancy; //true if train is present, false if train is not on the track

   /*
        Assuming that every block has railway signal (traffic light)
        Some blocks have swithch machine and railway crossing bars
        
    */
    private int isFailure; //0<--Normal Operation, 1<--Broken Rail, 2<--Power Failure, 3<--Track Circuit Failure
    private boolean isSwitchMachine; //true if the block has a switch machine, false if the block does not have a switch machine
    private boolean isRailWayCrossingBars; //true if the block has a railway crossing bar, false if the block does have a railway crossing bar

    public Block()
    {   
        /*
            set to default values
        */
        branchDirection = false; //0<--default branch to 'nextBlock', 1<--branch to 'nextOptionalBlock'
        trafficLight = 2; // 0<--Stop, 1<--Decelerate, 2<--Proceed, 3<--Accelerate
        crossingBars = 1; //set to default 'open' position
        isFailure = 0; //set to Normal Operation

        controlSignal = new Signal();
        trackCircuit = new TrackCircuit();
        transponder = new Transponder();
    }

    public Block(String TrainLine, String NameOfSection,int IdOfBlock, double LengthOfBlock, double Grade, double SpeedLimit, double Elevation, double CumulativeElevation, String StationName, String SwitchMachine, String TrackType, String RailWay)
    {   
        /*
            set to default values
        */
        branchDirection = false; //0<--default branch to 'nextBlock', 1<--branch to 'nextOptionalBlock'
        trafficLight = 2; // 0<--Stop, 1<--Decelerate, 2<--Proceed, 3<--Accelerate
        crossingBars = 1; // 0<--Closed, 1<--Open
        isFailure = 0; //set to Normal Operation
        trackStatus = 100;

        controlSignal = new Signal();
        trackCircuit = new TrackCircuit();
        transponder = new Transponder();
        /*
            Initilization
        */
        trainLine = TrainLine;
        blockID = IdOfBlock;
        sectionName = NameOfSection;
        blockLength = LengthOfBlock;
        grade = Grade;
        speedLimit = SpeedLimit;
        elevation = Elevation;
        cumulativeElevation = CumulativeElevation;
        stationName = StationName;
        switchMachine = SwitchMachine;
        trackType = TrackType;
        railwayCrossing = RailWay;
    }
    
    /*
        set previous block 
    */
    public void setPreviousBlock(Block PreviousBlock)
    {
        this.previousBlock = PreviousBlock;
    }
    
    /*
        set next block 
        This is the default position of the track for all blocks
    */
    public void setNextBlock(Block NextBlock)
    {
        this.nextBlock = NextBlock;
    }
    /*
        set optional next block
        Optional next block denotes the secondary block where there is a switch machine within a certain block
        For example, In red line, block 12 has a switch machine. The defualt position links block 13 to block 12.
        When the switch machine is toggled first, then block 13 is connected with block 1 only. 
        Therefore, train could only goes from 13 to 1 instead of 13 to 12. 
    */
    public void setOptionalNextBlock(Block NextBlock)
    {
        this.nextOptionalBlock = NextBlock;
    }
    
    /*
        get previoud block
    */
    public Block getPreviousBlock()
    {
        return this.previousBlock;
    }
    
    /*
        get next block (default)
    */
    public Block getNextBlock()
    {

        return this.nextBlock;
    }
    /*
        get next block(optional)
    */  
    public Block getOptionalNextBlock()
    {
        return this.nextOptionalBlock;
    }
//////////////////////////////////////////////////////
    /*
        pretty self-explanatory
    */
    public String getTrainLine()
    {
        return this.trainLine;
    }
    
    public int getBlockID()
    {
        return this.blockID;
    }
    public String getSectionName()
    {
        return sectionName;
    }

    public double getBlockLength()
    {
        return blockLength;
    }

    public double getGrade()
    {
        return grade;
    }

    public double getSpeedLimit()
    {
        return speedLimit;
    }

    public double getElevation()
    {
        return elevation;
    }

    public double getCumulativeElevation()
    {
        return cumulativeElevation;
    }

    public String getStationName()
    {
        return stationName;
    }

    public String getSwitchMachine()
    {
        return switchMachine;
    }

    public String getTrackTyoe()
    {
        return trackType;
    }

    public String getRailWayCrossing()
    {
        return railwayCrossing;
    }

    public int getTrackStatus()
    {
        return trackStatus;
    }
   //////////////////////////////////////////////////////
    /*
        return an object that is stored in the block object
    */


    public Signal getSignal()
    {
        return controlSignal;
    }
    public TrackCircuit getTrackCircuit()
    {
        return trackCircuit;
    }
    public Transponder getTransponder()
    {
        return transponder;
    }
    
    /*
        install transponder in certain blocks. 
        Assume four blocks prior to the block that has a station
    */
    
    public void installTransponder(String stationName)
    {
        transponder.setNextStation(stationName);
    }
    
    /*
        check if a certain block has transponder. 
        If the block has a transponder, method returns yes
        If the block dost not have a transponder, method returns no
        required for train
    */
    public boolean hasTransponder()
    {
        boolean result = false;
        
        if(!(transponder.getNextStation().equals("DEFAULT_STATION")))
        {
            result = true;
        }

        return result;
    }
   //////////////////////////////////////////////////////
    
    /*
        use case 1 for track controller
    */
    public void controlSwitchMachine()
    {

        controlSignal.changeSwitchMachineSignal();
        branchDirection = controlSignal.getSwitchMachineStatus();
    
    }
    
    /*
        use case 2 for track controller
    */
    //SignalLightsStatus = 0<--Stop, 1<--Decelerate, 2<--Proceed, 3<--Accelerate
    public void controlRailWayLight(int SignalLightStatus)
    {   
        controlSignal.changeRailWayLightSignal(SignalLightStatus);
        trafficLight = controlSignal.getRailWayLightSignal(); 
    }
    
    /*
        use case 3 for track controller
    */
    public void controlRailWayCrossingBars()
    {
        controlSignal.changeRailWayCrossingBarSignal();
        crossingBars = controlSignal.getRailWayCrossingBars();
    }

    /////////////////////////////////
    /*
        get branch direction of the track
        required for train model
    */
    public boolean getBranchDirection()
    {
        return branchDirection;
    }
    
    /*
        get traffic light information of the block 
        required for train model
    */
    public int getTrafficLight()
    {
        return trafficLight;
    }
    
    public Info getInfo()
    {
        return info;
    }
    public void setInfo(Info info)
    {
        this.info=info;
    }
    
    /*
        get railway crossing bars of the block
        required for train model
    */
    public int getRailWayCrossingBars()
    {
        return crossingBars;
    }
    ///////////////////////////////
    
    /*
        set track occupancy
        required for train model
    */
    public void changeOccupancy(boolean occupancy)
    {  
       trackOccupancy = trackCircuit.setOccupancy(occupancy);
    }
    
    /*
        get track occupancy
        required for other modules
    */
    public boolean getOccupancy()
    {
        return trackOccupancy;
    }
    ///////////////////////////////
    public void setTrackStatus(int TRACK_STATUS)
    {
        trackStatus = TRACK_STATUS;
    }
      public void setMaintainence()
    {
        if(isFailure==0)
        {   
            //if track is not broken 
            trackStatus=trackStatus*-1;
        }
        else if( trackStatus==100)
        {    
            //if track is broken
            trackStatus=-100;       
        }
    }

    ///////////////////////////////

    public boolean isClose()
    {   
        boolean result = false;
        if(trackStatus == 100)
        {
            result = false;
        }

        else
        {
            result = true;
        }

        return result;
    }
    public boolean isSwitchMachineStatus()
    {   
       
        if(!switchMachine.equals("null"))
        {
            isSwitchMachine = true;
        }

        else
        {
            isSwitchMachine = false;
        }

        return isSwitchMachine;
    }

    public boolean isRailWayCrossingBarStatus()
    {
        if(!railwayCrossing.equals("null"))
        {
            isRailWayCrossingBars = true;
        }

        else
        {
            isRailWayCrossingBars = false;
        }
        
        return isRailWayCrossingBars;

    }

    //1<--Broken Rail, 2<--Power Failure, 3<--Track Circuit Failure, 0<--Normal Operation
    public void setFailure(int FailMode)
    {
        trackCircuit.setFailureMode(FailMode);
        isFailure = trackCircuit.getFailureMode();
    }
 
}

