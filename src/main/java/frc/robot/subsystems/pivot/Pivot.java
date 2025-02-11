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
    //TODO: THESE ARE PLACEHOLDER VALUES FOR TESTING!!!1111!111
    L4(45),
    L3(35),
    L2(30),
    L1(25),
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
