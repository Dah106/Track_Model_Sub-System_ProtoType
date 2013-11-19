package Track_Model_ProtoType;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public interface TrainModelInterface 
{     
    /*
        TrainLine = 1<--'Red', 2<--'Green'
        Train Model calls the setTrainLine(int TrainLine) to indentify which train line first
    */

    
    public Block getBlockInformation(int BlockNumber);
    
    public void updateTrainLocation(int BlockNumber);
}
