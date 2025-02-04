package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.pivot.PivotIO.PivotIOInputs;


public class Pivot extends SubsystemBase {
  private PivotTarget targetState;
  private double pivotPosition;
  private PivotIO pivotIO;


  public enum PivotTarget{
    L4,
    L3,
    L2,
    L1,
    INTAKE,
    STOW,
    ZEROING;
  }

  //getter
  public double getPosition() {
    return pivotPosition;
  }

  public Pivot(PivotIO io) {
    //if the Pivot is at a certain mode, it will go to a certain angle
      switch(targetState)  { //FIXME: ADD GETTING THE POSITION TARGETS
        case L1: 
        case L2: 
        case L3:
        case L4: 
        case INTAKE:
        case STOW:
        case ZEROING:
    }
  }


  @Override
  public void periodic(){}

}
