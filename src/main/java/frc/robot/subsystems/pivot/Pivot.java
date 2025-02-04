package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Pivot extends SubsystemBase {
  private PivotTarget targetState;
  public enum PivotTarget{
    SCORE_L4,
    SCORE_L3,
    SCORE_L2,
    SCORE_L1,
    INTAKE,
    STOW,
    ZEROING;
  }

  
  public Pivot(PivotIO io) {
    //if the Pivot is at a certain mode, it will go to a certain angle
      switch(targetState)  {
        case SCORE_L1: 
        case SCORE_L2: 
        case SCORE_L3:
        case SCORE_L4: 
    }
  }


  @Override
  public void periodic(){
  
  }

}
