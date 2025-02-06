package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.pivot.PivotIO.PivotIOInputs;


public class Pivot extends SubsystemBase {
  private PivotTarget targetState;
  private double pivotPosition;
  private PivotIO pivotIO;
  
  public enum PivotControlMode {
    POSITION,
    ZERO,
    STOP,
  }

  private PivotControlMode pivotControlMode = PivotControlMode.STOP; //this just sets it to stop by default
  private Pivot pivot;

  public enum PivotTarget {
    //FIXME: Add placeholder values for testing (or just for teaching because this probably won't get tested)
    L4(0),
    L3(0),
    L2(0),
    L1(0),
    INTAKE(0),
    STOW(0),
    ZEROING(0);
    private double position = 0;

    private PivotTarget(double position) {
      this.position = position;
    }
  }
  //getters and setters
  public double getPosition() {
    return pivotPosition;
  }

  public PivotControlMode getPivotControlMode(PivotControlMode pivotControlMode) {
    return pivotControlMode;
  }

  public void setControlMode(PivotControlMode pivotControlMode) {
    this.pivotControlMode = pivotControlMode;
  }


  public void setPivotTarget(PivotTarget pivotTarget) {
      setControlMode(PivotControlMode.POSITION); 
  }

  public Pivot(PivotIO io) {}

  @Override  
  public void periodic(){
    //if the Pivot is at a certain mode, it will go to a certain angle
    switch(targetState)  { 
        case L1 -> {
          setPivotTarget(PivotTarget.L1);
        } 
        case L2 -> {
          setPivotTarget(PivotTarget.L2);
        } 
        case L3 -> {
          setPivotTarget(PivotTarget.L3);
        } 
        case L4 -> {
          setPivotTarget(PivotTarget.L4);
        } 
        case INTAKE -> {
          setPivotTarget(PivotTarget.INTAKE);
        }
        case STOW -> {
          setPivotTarget(PivotTarget.STOW);
        } 
        case ZEROING -> {
          setPivotTarget(PivotTarget.ZEROING);
        }   
    }
    switch(pivotControlMode) {
      case POSITION -> { 
        pivotIO.runPosition(pivot.getPosition());
      }
      case STOP-> { 
        pivotIO.stop();
      }
      case ZERO-> { 
        pivotIO.runCharacterization();
      }
    }
  }

}
