package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Pivot extends SubsystemBase {
  public enum PivotTarget{
    
  }

  private PivotIO io;
  //private PivotIOInputsAutologged inputs;
    
  public Pivot(PivotIO io) {
    this.io = io;
  }

  @Override
  public void periodic(){
    //updateInputs();
  }

}
