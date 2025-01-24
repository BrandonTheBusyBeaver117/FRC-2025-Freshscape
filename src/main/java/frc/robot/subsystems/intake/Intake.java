package frc.robot.subsystems.intake;


import java.lang.Thread.State;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.swerve.controllers.HeadingController;

public class Intake extends SubsystemBase {
  public enum Target {
    HOLD(2),
    IDLE(0),
    EJECT(-12),
    INTAKE(12);
    
    private double volts;

    private Target(double volts) {
      this.volts = volts;
    }

    public double getVolts() {
      return volts;
    }
  }


  private IntakeIO io;
  private IntakeIOInputsAutoLogged inputs  = new IntakeIOInputsAutoLogged();


  private Target target = Target.IDLE;

  public Intake(IntakeIO io) {
    //hold, idle, eject, intake
    this.io = io;
    

  }

  @Override
  public void periodic() {
    io.runVolts(target.getVolts());

    boolean skibidi = false;
    switch (target) {
      case HOLD:
        
        break;
      case IDLE:

        break;
      case EJECT:

        break;
      case INTAKE:
          if(skibidi){
            target = Target.HOLD;
          }
        break;
    }

    io.updateInputs(inputs);
    Logger.processInputs("Intake", inputs);
  }

  public Target getTarget() {
    return target;
  }
  
  public void setTarget(Target target) { 
    this.target = target;
  }
}
