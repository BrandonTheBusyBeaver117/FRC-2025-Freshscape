package frc.robot.subsystems.elevator;
import frc.robot.Constants;

public class ElevatorConstants {
  public static final int MOTOR_PORT = 0;
  public static final PIDGains GAINS = 
    switch (Constants.getRobotType()) {
      case ALPHA -> new PIDGains(0, 0, 0, 0, 0, 0);
      case PROG -> new PIDGains(0, 0, 0, 0, 0, 0);
      case SIM -> new PIDGains(0, 0, 0, 0, 0, 0);
    };

  public record PIDGains(double kP, double kI, double kD, double kS, double kV, double kA) {}
}
