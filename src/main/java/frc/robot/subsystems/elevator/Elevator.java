package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase  {
  public enum ElevatorTarget {
    BOTTOM(0),
    TOP(15);

    private final int velocity;
    private ElevatorTarget(int velocity) {
      this.velocity = velocity;
    }
  }

  private final ElevatorIO io;
  private ElevatorTarget target = ElevatorTarget.BOTTOM;
  private ElevatorIOInputsAutoLogged input = new ElevatorIOInputsAutoLogged();

  public Elevator(ElevatorIO io) {
    this.io = io;
  }

  @Override
  public void periodic(){
    io.updateInputs(input);
    io.runVelocity(target.velocity);
  }

  public void setTarget(ElevatorTarget target) {
    this.target = target;
  }
}
