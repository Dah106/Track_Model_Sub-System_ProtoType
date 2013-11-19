/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Track_Model_ProtoType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LinkedBlock implements TrackControllerInterface, TrainModelInterface
{   
    public final String PathOfFile = "Track_Layout.csv";
    public int trainLine; //1<--'Red', 2<--'Green'
    public HashMap<Integer, Block> RED;
    public HashMap<Integer, Block> GREEN;

    public Block YARD;
    public Block CurrentBlock;


    public boolean TRACK_HEATER = false; //false <-- OFF, true <-- ON

    public LinkedBlock()
    {
       loadMap();
       setTrackLayoutGreenLineConfiguration();
       setTrackLayoutRedLineConfiguration();
    }

    public LinkedBlock(int TrainLine)
    {
       setTrainLine(TrainLine);
       loadMap();
       setTrackLayoutGreenLineConfiguration();
       setTrackLayoutRedLineConfiguration();
    }

    public LinkedBlock(HashMap<Integer,Block> TrainLine, int LineID)
    {   
        trainLine = LineID;
        if(LineID == 1)
        {
            RED = TrainLine;
            //setTrackLayoutConfiguration();  
        }
        else
        {
            GREEN = TrainLine;
            //setTrackLayoutConfiguration();  
        }
    }

    private void loadMap()
    {
        BufferedReader br = null;
        String line = "";
        String splitBy = ",";
        String isRed = "Red";
        try
        {
            br = new BufferedReader(new FileReader(PathOfFile)); 
            while ((line = br.readLine()) != null)
            {  
                String [] data = line.split(splitBy);
                if(data[0].equals(isRed))
                {
                  RED.put(Integer.parseInt(data[2]), new Block(data[0],data[1],Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]),Double.parseDouble(data[7]), data[8], data[9],data[10],data[11]));
                }
                else  
                {
                  GREEN.put(Integer.parseInt(data[2]), new Block(data[0],data[1],Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]),Double.parseDouble(data[7]), data[8], data[9],data[10],data[11]));
                }
            }
        }catch (FileNotFoundException e) {  
        e.printStackTrace();  
        }catch (IOException e) {  
        e.printStackTrace();  
        }finally {  
        if (br != null) {  
        try {  
            br.close();  
        } catch (IOException e) {  
        e.printStackTrace();  
        }  
        }  
    }
    }
    
    @Override
    public void setTrainLine(int TrainLine)
    {
        trainLine = TrainLine;
    }

    public int getTrainLine()
    {
        return trainLine;
    } 

    @Override
    public void addTrackModelBlock(int StartBlockNumber, int EndBlockNumber)
    {   

       switch (trainLine)
        {
            case 1: for(int cursor=StartBlockNumber;cursor<=EndBlockNumber;cursor++)
                    {   
                        if(RED.get(cursor).isClose())
                        {
                            RED.get(cursor).setTrackStatus(100);
                        }
                    }
                    break;
            
            case 2: for(int cursor=StartBlockNumber;cursor<=EndBlockNumber;cursor++)
                    {   
                        if(GREEN.get(cursor).isClose())
                        {
                            GREEN.get(cursor).setTrackStatus(100);
                        }
                    }
                    break;
            
            default: 
                    break;        
        }
    }
    
    @Override
    public void removeTrackModelBlock(int StartBlockNumber, int EndBlockNumber)
    {
        switch (trainLine)
        {
            case 1: for(int cursor=StartBlockNumber;cursor<=EndBlockNumber;cursor++)
                    {   
                        if(!RED.get(cursor).isClose())
                        {
                            RED.get(cursor).setTrackStatus(-100);
                        }
                    }
                    break;
            
            case 2: for(int cursor=StartBlockNumber;cursor<=EndBlockNumber;cursor++)
                    {   
                        if(!GREEN.get(cursor).isClose())
                        {
                            GREEN.get(cursor).setTrackStatus(-100);
                        }
                    }
                    break;
            
            default: 
                    break;        
        } 
    } 

    @Override
    public void toggleTrackModelSwitch(int BlockNumber)
    {
        switch (trainLine)
        {
            case 1: CurrentBlock = RED.get(BlockNumber);
                    CurrentBlock.controlSwitchMachine();
                    break;
            
            case 2: CurrentBlock = GREEN.get(BlockNumber);
                    CurrentBlock.controlSwitchMachine();
                    break;
            
            default: 
                    break;        
        }
    }
    
    @Override
    //SignalLightStatus = 0<--Stop, 1<--Decelerate, 2<--Proceed, 3<--Accelerate
    public void controlRailWayLightSignal(int BlockNumber, int SignalLightStatus)
    {
        switch (trainLine)
        {
            case 1: CurrentBlock = RED.get(BlockNumber);
                    CurrentBlock.controlRailWayLight(SignalLightStatus);
                    break;
            
            case 2: CurrentBlock = GREEN.get(BlockNumber);
                    CurrentBlock.controlRailWayLight(SignalLightStatus);
                    break;
            
            default: 
                    break;        
        }
    }
    
    @Override
    public void toggleRailWayCrossingBarSignal(int BlockNumber)
    {   
        
        switch (trainLine)
        {
            case 1: CurrentBlock = RED.get(BlockNumber);
                    CurrentBlock.controlRailWayCrossingBars();
                    break;
            
            case 2: CurrentBlock = GREEN.get(BlockNumber);
                    CurrentBlock.controlRailWayCrossingBars();
                    break;
            
            default: 
                    break;        
        }

    }
    
    @Override
    public boolean operateTrackModelTrackHeater()
    {
        if(TRACK_HEATER == false)
        {
            TRACK_HEATER = true;
        }

        else
        {
            TRACK_HEATER = false;
        }

        return TRACK_HEATER;
    }

    @Override
    public Block getBlockInformation(int BlockNumber)
    {   
       
        switch (trainLine)
        {
            case 1: CurrentBlock = RED.get(BlockNumber);
                    break;
            
            case 2: CurrentBlock = GREEN.get(BlockNumber);
                    break;
            
            default: 
                    break;        
        }
        return CurrentBlock;
    }
    
    @Override
    public void updateTrainLocation(int BlockNumber)
    {
        
        switch (trainLine)
        {
            case 1: CurrentBlock = RED.get(BlockNumber);
                    if(CurrentBlock.getPreviousBlock() != null)
                    {   
                        CurrentBlock.getPreviousBlock().changeBackOccupancy();
                    }
                    CurrentBlock.changeOccupancy();
                    break;
            
            case 2: CurrentBlock = GREEN.get(BlockNumber);
                    if(CurrentBlock.getPreviousBlock() != null)
                    {   
                        CurrentBlock.getPreviousBlock().changeBackOccupancy();
                    }
                    CurrentBlock.changeOccupancy();
                    break;
            
            default: 
                    break;        
        }

    }

    @Override
    public HashMap<Integer,Block> getRedlLineInformation()
    {   
        return RED;
    }
    
    @Override
    public HashMap<Integer,Block> getGreenLineInformation()
    {      
        return GREEN;
    }

    @Override
    public void forceFailure(int BlockNumber, int FailMode)
    {
        switch (trainLine)
        {
            case 1: CurrentBlock = RED.get(BlockNumber);
                    CurrentBlock.setFailure(FailMode);
                    break;
            
            case 2: CurrentBlock = GREEN.get(BlockNumber);
                    CurrentBlock.setFailure(FailMode);
                    break;
            
            default: 
                    break;        
        }
        
    }
    
    /*
        Setup Track Layout of Green Line
        Link all the block together!
        -> means setNext, ->> means setOptionalNext
        <- means setPrevious
    */

    public void setTrackLayoutGreenLineConfiguration()
    {
        /*
            12 (SWITCH) -> 11 -> ... -> 1
        */
        for(int blockID = 12;blockID>=2;blockID--)
        {   
           GREEN.get(blockID).setNextBlock(GREEN.get(blockID-1));
        }
        
        /*
            1 ->> 13
        */
        GREEN.get(1).setOptionalNextBlock(GREEN.get(13));
        
        /*
            13 -> 14 -> 15 -> ... -> 57
        */
        for(int blockID = 13;blockID<=56;blockID++)
        {
           GREEN.get(blockID).setNextBlock(GREEN.get(blockID+1));
        }
        
        /*
            57 ->> 58 (YARD)
            57 -> 59
            59 -> 60
            60 -> 61
            61 ->> 63
            62 (YARD) -> 63
        */
        GREEN.get(57).setOptionalNextBlock(GREEN.get(58));
        GREEN.get(57).setNextBlock(GREEN.get(59));
        GREEN.get(59).setNextBlock(GREEN.get(60));
        GREEN.get(60).setNextBlock(GREEN.get(61));
        GREEN.get(61).setOptionalNextBlock(GREEN.get(63));
        GREEN.get(62).setNextBlock(GREEN.get(63));
        
        /*
           63 -> 64 -> 65 -> ... -> 100
        */
        for(int blockID = 63;blockID<=99;blockID++)
        {
           GREEN.get(blockID).setNextBlock(GREEN.get(blockID+1));
        }
        
        /*
            100 ->> 85 
        */
        GREEN.get(100).setOptionalNextBlock(GREEN.get(85));
        
        /*
            85 <- 84 <- 83 <- ... 78 <- 77
        */
        for(int blockID = 85;blockID>=78;blockID--)
        {
           GREEN.get(blockID).setPreviousBlock(GREEN.get(blockID-1));
        }
        
        /*
            77 ->> 101
        */
        GREEN.get(77).setOptionalNextBlock(GREEN.get(101));
        
        /*
            101 -> 102 -> ... -> 150
        */
        for(int blockID = 101;blockID<=149;blockID++)
        {
           GREEN.get(blockID).setPreviousBlock(GREEN.get(blockID+1));
        }
       
        /*
            150 ->> 28
        */
        GREEN.get(150).setOptionalNextBlock(GREEN.get(28));
       
        /*
            28 <- 27 <- ... <- 14 <- 13 
        */
        for(int blockID = 28;blockID>=14;blockID--)
        {
           GREEN.get(blockID).setPreviousBlock(GREEN.get(blockID-1));
        }
        
        /*
            13 -> 12
        */
        GREEN.get(13).setNextBlock(GREEN.get(12));
    }
    
    /*
        Setup Track Layout of Red Line
        Link all the block together!
        -> means setNext, ->> means setOptionalNext
        <- means setPrevious
    */
    
    public void setTrackLayoutRedLineConfiguration()
    {   
        /*  
            Start point
        
            9 (YARD) -> 8 
            
            10 ->> 8
        */
        RED.get(9).setNextBlock(RED.get(8));
        
        RED.get(10).setOptionalNextBlock(RED.get(8));
        
        /*  
            Sector "Angela" at 8 to 15 or (15 to 8)
        
            8 -> 7 -> 6 ... -> 1 -> 16 
            
            1 -> 16 
            
            16 <- 1 
        
            16 ->> 15 (SWITCH) 
        
            15 -> 14 -> ... -> 10 
        */
        for(int blockID = 8;blockID>=2;blockID--)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID-1));
        }
        
        RED.get(1).setNextBlock(RED.get(16));
        
        RED.get(16).setPreviousBlock(RED.get(1));
        
        RED.get(16).setOptionalNextBlock(RED.get(15));
        
        for(int blockID = 15;blockID>=11;blockID--)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID-1));
        }
        
        /*
            Sector "Boardwalk" at 16 to 32 or (32 to 16)
        
            16 -> 17 -> ... -> 31 -> 32 (SWITCH)
        
            32 (SWITCH) <- 31 <- ... <- 17 <- 16
        */
        for(int blockID = 16;blockID<=31;blockID++)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID+1));
        }
        
        for(int blockID = 32;blockID>=17;blockID--)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID-1));
        }
        
        
        /*
            "Right Tit" starts at 76 to 72 or (72 to 76)
        
            27 (SWITCH) ->> 76 -> 75 -> 74 -> 73 -> 72 ->> 32 (SWITCH)
            
            32 (SWITCH) ->> 72 <- 73 <- 74 <- 75 <- 76 ->> 27 (SWITCH)
            
        */
        RED.get(27).setOptionalNextBlock(RED.get(76));
        RED.get(76).setNextBlock(RED.get(75));
        RED.get(75).setNextBlock(RED.get(74));
        RED.get(74).setNextBlock(RED.get(73));
        RED.get(73).setNextBlock(RED.get(72));
        RED.get(72).setOptionalNextBlock(RED.get(32));
        
        RED.get(32).setOptionalNextBlock(RED.get(72));
        RED.get(72).setPreviousBlock(RED.get(73));
        RED.get(73).setPreviousBlock(RED.get(74));
        RED.get(74).setPreviousBlock(RED.get(75));
        RED.get(75).setPreviousBlock(RED.get(76));
        RED.get(76).setOptionalNextBlock(RED.get(27));
        
        /*
            Sector "chalky" at 32 to 38 or (38 to 32)
        
            32 (SWITCH) -> 33 -> ... -> 37 -> 38 (SWITCH)
        
            38 (SWITCH) <- 37 <- ... <- 33 <- 32 (SWITCH)
        */
        for(int blockID = 32;blockID<=37;blockID++)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID+1));
        }
        
        for(int blockID = 38;blockID>= 33;blockID--)
        {
            RED.get(blockID).setPreviousBlock(RED.get(blockID-1));
        }
        
        /*
            "Left Tit" starts at 71 to 67 or (67 to 71)
        
            38 (SWITCH) ->> 71 -> 70 -> 69 -> 68 -> 67 ->> 43 (SWITCH)
        
            43 (SWITCH) ->> 67 <- 68 <- 69 <- 70 <- 71 ->> 38 (SWITCH)
        */
        RED.get(38).setOptionalNextBlock(RED.get(71));
        RED.get(71).setNextBlock(RED.get(70));
        RED.get(70).setNextBlock(RED.get(69));
        RED.get(69).setNextBlock(RED.get(68));
        RED.get(68).setNextBlock(RED.get(67));
        RED.get(67).setOptionalNextBlock(RED.get(43));
          
        RED.get(43).setOptionalNextBlock(RED.get(67));
        RED.get(67).setPreviousBlock(RED.get(68));
        RED.get(68).setPreviousBlock(RED.get(69));
        RED.get(69).setPreviousBlock(RED.get(70));
        RED.get(70).setPreviousBlock(RED.get(71));
        RED.get(71).setOptionalNextBlock(RED.get(38));
        
        
        
        /*
            Sector "Darmody" at 38 to 52 or (52 to 43)
        
            38 (SWITCH) -> 39 -> ... -> 51 -> 52 (SWITCH)
        
            52 (SWITCH) <- 51 <- ... <- 39 <- 38 (SWITCH)
        */
        for(int blockID = 38;blockID<=51;blockID++)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID+1));
        }
    
        for(int blockID = 52;blockID>= 39;blockID--)
        {
            RED.get(blockID).setPreviousBlock(RED.get(blockID-1));
        }
        
        /*
            Sector "Eddie" at 52 to 66 or (66 to 52)
        
            52 (SWITCH) -> 53 -> ... -> 66 ->> 52 (SWITCH)
        
            52 (SWITCH) ->> 66 <- 65 <- ... <- 53 <- 52 (SWITCH)
        */
        for(int blockID = 52;blockID<=65;blockID++)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID+1));
        }   
        RED.get(66).setOptionalNextBlock(RED.get(52));

        
        RED.get(52).setOptionalNextBlock(RED.get(66));
        for(int blockID = 66;blockID>=53;blockID--)
        {
            RED.get(blockID).setPreviousBlock(RED.get(blockID-1));
        }
        
        
        //end!!

    }

    
}
