package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  public enum Target {
    HOLD,
    IDLE,
    EJECT,
    INTAKE
  }

  private Target target = Target.IDLE;

  public Intake(IntakeIO io) {
    //hold, idle, eject, intake

  }

  @Override
  public void periodic() {
    switch (target) {
      case HOLD:
        break;
      case IDLE:
        break;
      case EJECT:
        break;
      case INTAKE:
        break;
    }
  }
}
