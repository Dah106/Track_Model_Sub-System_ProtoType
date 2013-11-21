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

public class LinkedBlock
{   
    public final String PathOfFile = "Track_Layout.csv";
    public int trainLine; //1-<-'Red', 2-<-'Green'
    public HashMap<Integer, Block> RED;
    public HashMap<Integer, Block> GREEN;

    public Block YARD;
    public Block CurrentBlock;


    public boolean TRACK_HEATER = false; //false -<- OFF, true -<- ON

    public LinkedBlock()
    {  
       RED = new HashMap();
       GREEN = new HashMap();
       loadMap();
       setTrackLayoutGreenLineConfiguration();
       setTrackLayoutRedLineConfiguration();
       setRedLineTransponder();
       setGreenLineTransponder();
    }

   

    public LinkedBlock(HashMap<Integer,Block> TrainLine, int LineID)
    {   
        trainLine = LineID;
        if(LineID == 1)
        {
            RED = TrainLine;
            setTrackLayoutRedLineConfiguration();
            setRedLineTransponder(); 
        }
        else
        {
            GREEN = TrainLine;
            setTrackLayoutGreenLineConfiguration();
            setGreenLineTransponder();
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
                  RED.put(Integer.parseInt(data[2]), new Block(data[0],data[1],Integer.parseInt(data[2]),Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]),Double.parseDouble(data[7]), data[8], data[9],data[10],data[11]));
                }
                else  
                {
                  GREEN.put(Integer.parseInt(data[2]), new Block(data[0],data[1],Integer.parseInt(data[2]),Double.parseDouble(data[3]), Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]),Double.parseDouble(data[7]), data[8], data[9],data[10],data[11]));
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
    

    public void setTrainLine(int TrainLine)
    {
        trainLine = TrainLine;
    }

    public int getTrainLine()
    {
        return trainLine;
    } 

    
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
    
   
    //SignalLightStatus = 0-<-Stop, 1-<-Decelerate, 2-<-Proceed, 3-<-Accelerate
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
    
    
   

    
    public HashMap<Integer,Block> getRedLineInformation()
    {   
        return RED;
    }
    

    public HashMap<Integer,Block> getGreenLineInformation()
    {      
        return GREEN;
    }

    
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
        -< means setPrevious
    */

    public void setTrackLayoutGreenLineConfiguration()
    {
        /*
            Sector "Albatross" at 12 to 1
        
            12 (SWITCH) -> 11 -> ... -> 1
            
            (switch_control) 1 -< 2 -< 3 -< 4
            
            1 -> 13 (MERGE)
        
            13 ->> 1
            
            13 -> 12 (SWITCH)
        
            12 (SWITCH) -< 13
            
        */
        for(int blockID = 12;blockID>=2;blockID--)
        {   
           GREEN.get(blockID).setNextBlock(GREEN.get(blockID-1));
        }
        
        for(int blockID = 1;blockID<=3;blockID++)
        {
            GREEN.get(blockID).setPreviousBlock(GREEN.get(blockID+1));
        }
        
        GREEN.get(1).setNextBlock(GREEN.get(13));
        
        GREEN.get(13).setOptionalNextBlock(GREEN.get(1));
        
        GREEN.get(13).setNextBlock(GREEN.get(12));
        
        GREEN.get(12).setPreviousBlock(GREEN.get(13));
        
        /*
            Sector "Boss" at 13 to 29
        
            13 -< 14 -< 15 -< ... -< 28
        
            28 -> 29 (SWITCH)
        
            29 (SWITCH) -< 28 
        
            28 -> 27 -> ... -> 13
        
            28 ->> 150 
        
            150 -> 28 (MERGE)

        */
        for(int blockID = 13;blockID<=27;blockID++)
        {
           GREEN.get(blockID).setPreviousBlock(GREEN.get(blockID+1));
        }
        
        GREEN.get(28).setNextBlock(GREEN.get(29));
        
        GREEN.get(29).setPreviousBlock(GREEN.get(28));
        
        for(int blockID = 28;blockID>=14;blockID--)
        {
           GREEN.get(blockID).setNextBlock(GREEN.get(blockID-1));
        }
        
        
        GREEN.get(28).setOptionalNextBlock(GREEN.get(150));
        
        GREEN.get(150).setNextBlock(GREEN.get(28));
        
        /*  
            Sector " Charlie" at 29 to 62
        
            29 -> 30 -> 31 -> ... -> 57
            57 -> 58 (SWITCH)
            58 (SWITCH) -< 57 -< 56 -< 55
            58 -> 59 
            59 -< 58
            59 -> 60
            60 -< 59
            60 -> 61
            61 -< 60
            61 -> 62 (SWITCH) (MERGE)
            62 (SWITCH) -< 61
        */
        for(int blockID = 29;blockID<=56;blockID++)
        {
           GREEN.get(blockID).setNextBlock(GREEN.get(blockID+1));
        }
        
        GREEN.get(57).setNextBlock(GREEN.get(58));
        
        GREEN.get(58).setPreviousBlock(GREEN.get(57));
        
        GREEN.get(57).setPreviousBlock(GREEN.get(56));
        
        GREEN.get(56).setPreviousBlock(GREEN.get(55));
        
        GREEN.get(58).setNextBlock(GREEN.get(59));
            
        GREEN.get(59).setPreviousBlock(GREEN.get(58));
        
        GREEN.get(59).setNextBlock(GREEN.get(60));
        
        GREEN.get(60).setPreviousBlock(GREEN.get(59));
        
        GREEN.get(60).setNextBlock(GREEN.get(61));
        
        GREEN.get(61).setPreviousBlock(GREEN.get(60));
        
        GREEN.get(61).setNextBlock(GREEN.get(62));
       
        GREEN.get(62).setPreviousBlock(GREEN.get(61));
        
        /*
            Sector "Dortmund" at 62 to 76
        
            62 (SWITCH) -> 63 -> 64 -> ... -> 76 (SWITCH)
        
            76 (SWITCH) -< 75 -< 74 -< 73 
        */
        for(int blockID = 62;blockID<=75;blockID++)
        {
           GREEN.get(blockID).setNextBlock(GREEN.get(blockID+1));
        }
        
        GREEN.get(76).setPreviousBlock(GREEN.get(75));
        
        GREEN.get(75).setPreviousBlock(GREEN.get(74));
        
        GREEN.get(74).setPreviousBlock(GREEN.get(73));
        
        /*
           Sector "Egypt" at 76 to 101
        
           76 (SWITCH) -> 77 -> ... -> 99 -> 100 
        
           86 (SWITCH) -< 85
        
           (switch_control) 85 -< 84 -< 83 -< 82 
        
           82 -< 81 -< 80 -< 79 -< 78 -< 77
        
           85 ->> 100
        
           (switch_control) 100 -< 99 -< 98 -< 97
        
           100 ->> 85 (MERGE)
        
           77 ->> 101
        */
        for(int blockID = 76;blockID<=99;blockID++)
        {
           GREEN.get(blockID).setNextBlock(GREEN.get(blockID+1));
        }
        
        GREEN.get(86).setPreviousBlock(GREEN.get(85));
        
        GREEN.get(85).setPreviousBlock(GREEN.get(84));
        
        GREEN.get(84).setPreviousBlock(GREEN.get(83));
        
        GREEN.get(83).setPreviousBlock(GREEN.get(82));
        
        for(int blockID = 82;blockID>=78;blockID--)
        {
           GREEN.get(blockID).setPreviousBlock(GREEN.get(blockID-1));
        }
        
        GREEN.get(85).setOptionalNextBlock(GREEN.get(100));
        
        GREEN.get(100).setPreviousBlock(GREEN.get(99));
        
        GREEN.get(99).setPreviousBlock(GREEN.get(98));
        
        GREEN.get(98).setPreviousBlock(GREEN.get(97));
        
        GREEN.get(100).setNextBlock(GREEN.get(85));
        
        GREEN.get(77).setOptionalNextBlock(GREEN.get(101));
 
        
        /*
            Sector "Ford" at 101 to 150
            101 -> 102 -> ... -> 149 -> 150
        */
        for(int blockID = 101;blockID<=149;blockID++)
        {
           GREEN.get(blockID).setNextBlock(GREEN.get(blockID+1));
        }
        
       
    }
    
    /*
        Setup Track Layout of Red Line
        Link all the block together!
        -> means setNext, ->> means setOptionalNext
        -< means setPrevious
    */
    
    public void setTrackLayoutRedLineConfiguration()
    {   
        /*  
            Start point
        
 
            10 -> 9 (SWITCH) (MERGE)
            
            9 (SWITCH) -< 10
        */
 
 
        RED.get(10).setNextBlock(RED.get(9));
        

        
        RED.get(9).setPreviousBlock(RED.get(10));
        
        /*  
            Sector "Angela" at 8 to 15 or (15 to 8)
        
            9 -> 8 -> 7 ... -> 1
            
            1 -> 16 
            
            16 -< 1 
        
            1 -< 2 -< ... -< 8 -< 9
        
            16 ->> 15 (SWITCH)
        
            15 (SWITCH) -< 16 (MERGE)
        
            10 -< 11 -< 12 ... -< 15 (SWITCH)
        
            15 -> 14 -> ... -> 10
   
        */
        for(int blockID = 9;blockID>=2;blockID--)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID-1));
        }
        
        
        RED.get(1).setNextBlock(RED.get(16));
        
        RED.get(16).setPreviousBlock(RED.get(1));
        
        for(int blockID = 1;blockID<=8;blockID++)
        {
            RED.get(blockID).setPreviousBlock(RED.get(blockID+1));
        }
        
        RED.get(16).setOptionalNextBlock(RED.get(15));
        
        RED.get(15).setPreviousBlock(RED.get(16));
        
        for(int blockID = 10;blockID<=14;blockID++)
        {
            RED.get(blockID).setPreviousBlock(RED.get(blockID+1));
        }
        
        for(int blockID = 15;blockID>=11;blockID--)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID-1));
        }
        
        
        
        /*
            Sector "Boardwalk" at 16 to 32 or (32 to 16)
        
            16 -> 17 -> ... -> 31 -> 32 (SWITCH)
        
            32 (SWITCH) -< 31 -< ... -< 17 -< 16
        */
        for(int blockID = 16;blockID<=31;blockID++)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID+1));
        }
        
        for(int blockID = 32;blockID>=17;blockID--)
        {
            RED.get(blockID).setPreviousBlock(RED.get(blockID-1));
        }
        
        
        /*
            "Right Tit" starts at 76 to 72 or (72 to 76)
        
            27 (SWITCH) ->> 76 -> 75 -> 74 -> 73 -> 72 
            
            72 -> 32 (SWITCH) (MERGE)
            
            32 (SWITCH) ->> 72 -< 73 -< 74 -< 75 -< 76 
        
            76 -< 27 (SWITCH) (MERGE)
            
        */
        RED.get(27).setOptionalNextBlock(RED.get(76));
        RED.get(76).setNextBlock(RED.get(75));
        RED.get(75).setNextBlock(RED.get(74));
        RED.get(74).setNextBlock(RED.get(73));
        RED.get(73).setNextBlock(RED.get(72));
        RED.get(72).setNextBlock(RED.get(32));
        
        RED.get(32).setOptionalNextBlock(RED.get(72));
        RED.get(72).setPreviousBlock(RED.get(73));
        RED.get(73).setPreviousBlock(RED.get(74));
        RED.get(74).setPreviousBlock(RED.get(75));
        RED.get(75).setPreviousBlock(RED.get(76));
        RED.get(76).setPreviousBlock(RED.get(27));
        
        /*
            Sector "chalky" at 32 to 38 or (38 to 32)
        
            32 (SWITCH) -> 33 -> ... -> 37 -> 38 (SWITCH)
        
            38 (SWITCH) -< 37 -< ... -< 33 -< 32 (SWITCH)
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
        
            38 (SWITCH) ->> 71 -> 70 -> 69 -> 68 -> 67 
        
            67 -> 43 (SWITCH)
        
            43 (SWITCH) ->> 67 -< 68 -< 69 -< 70 -< 71 
        
            71 -< 38 (SWITCH)
        */
        RED.get(38).setOptionalNextBlock(RED.get(71));
        RED.get(71).setNextBlock(RED.get(70));
        RED.get(70).setNextBlock(RED.get(69));
        RED.get(69).setNextBlock(RED.get(68));
        RED.get(68).setNextBlock(RED.get(67));
        RED.get(67).setNextBlock(RED.get(43));
          
        RED.get(43).setOptionalNextBlock(RED.get(67));
        RED.get(67).setPreviousBlock(RED.get(68));
        RED.get(68).setPreviousBlock(RED.get(69));
        RED.get(69).setPreviousBlock(RED.get(70));
        RED.get(70).setPreviousBlock(RED.get(71));
        RED.get(71).setPreviousBlock(RED.get(38));
          
        
        /*
            Sector "Darmody" at 38 to 52 or (52 to 43)
        
            38 (SWITCH) -> 39 -> ... -> 51 -> 52 (SWITCH)
        
            52 (SWITCH) -< 51 -< ... -< 39 -< 38 (SWITCH)
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
        
            52 (SWITCH) -> 53 -> ... -> 66
        
            66 -> 52 (SWITCH) (MERGE)
        
            52 (SWITCH) ->> 66 
            
            66 -< 65 -< ... -< 53 -< 52 (SWITCH)
        */
        for(int blockID = 52;blockID<=65;blockID++)
        {
            RED.get(blockID).setNextBlock(RED.get(blockID+1));
        }   
        
        RED.get(66).setNextBlock(RED.get(52));

        
        RED.get(52).setOptionalNextBlock(RED.get(66));
        
        for(int blockID = 66;blockID>=53;blockID--)
        {
            RED.get(blockID).setPreviousBlock(RED.get(blockID-1));
        }

        //end!!
    }
    
    /*
        Set up transponders for certain blocks in Green Line
    */
    public void setGreenLineTransponder()
    {
        /*
            2 (EDGEBROOK)
            9 (PIONEER)
            16 (STATION)
            22 (WHITED)
            31 (SOUTH BANK)
            39 (CENTRAL)
            48 (INGLEWOOD)
            57 (OVERBROOK)
            65 (GLENBURY)
            73 (DORMONT)
            77 (MT LEBANON)
            88 (POPLAR)
            96 (CASTLE SHANNON)
            105 (DORMONT)
            114 (GLENBURY)
            123 (OVERBROOK)
            132 (INGLEWOOD)
            141 (CENTRAL)
        */
        
        GREEN.get(2).installTransponder(GREEN.get(2).getStationName());
        GREEN.get(9).installTransponder(GREEN.get(9).getStationName());
        GREEN.get(16).installTransponder(GREEN.get(16).getStationName());
        GREEN.get(22).installTransponder(GREEN.get(22).getStationName());
        GREEN.get(31).installTransponder(GREEN.get(31).getStationName());
        GREEN.get(39).installTransponder(GREEN.get(39).getStationName());
        GREEN.get(48).installTransponder(GREEN.get(48).getStationName());
        GREEN.get(57).installTransponder(GREEN.get(57).getStationName());
        GREEN.get(65).installTransponder(GREEN.get(65).getStationName());
        GREEN.get(73).installTransponder(GREEN.get(73).getStationName());
        GREEN.get(77).installTransponder(GREEN.get(77).getStationName());
        GREEN.get(88).installTransponder(GREEN.get(88).getStationName());
        GREEN.get(96).installTransponder(GREEN.get(96).getStationName());
        GREEN.get(105).installTransponder(GREEN.get(105).getStationName());
        GREEN.get(114).installTransponder(GREEN.get(114).getStationName());
        GREEN.get(123).installTransponder(GREEN.get(123).getStationName());
        GREEN.get(132).installTransponder(GREEN.get(132).getStationName());
        GREEN.get(141).installTransponder(GREEN.get(141).getStationName());
        
    }
    
    /*
        Set up transponders for certain blocks in Red Line
    */
    public void setRedLineTransponder()
    {
        /*
            7 (SHADYSIDE)
            16 (HERRON AVE)
            21 (SWISSVILLE)
            25 (PENN STATION)
            35 (STEEL PLAZA)
            45 (FIRST AVE)
            48 (STATION SQUARE)
            60 (SOUTH HILLS JUNCTION)
        */
        RED.get(7).installTransponder(RED.get(7).getStationName());
        RED.get(16).installTransponder(RED.get(16).getStationName());
        RED.get(21).installTransponder(RED.get(21).getStationName());
        RED.get(25).installTransponder(RED.get(25).getStationName());
        RED.get(35).installTransponder(RED.get(35).getStationName());
        RED.get(45).installTransponder(RED.get(45).getStationName());
        RED.get(48).installTransponder(RED.get(48).getStationName());
        RED.get(60).installTransponder(RED.get(60).getStationName());
        
    }
    
    public static void main(String args[]) {
    
        LinkedBlock LinkedTestOfRed = new LinkedBlock();
        LinkedBlock LinkedTestOfGreen = new LinkedBlock();
//        System.out.println("the optional block ID is " + LinkedTestOfGreen.getGreenLineInformation().get(85).getOptionalNextBlock().getBlockID());
          System.out.println("the previous block ID is " + LinkedTestOfRed.getRedLineInformation().get(51).getPreviousBlock().getBlockID());
//        System.out.println("the next block ID is " + LinkedTestOfGreen.getGreenLineInformation().get(150).getNextBlock().getBlockID());
        
        
    }
 
}
