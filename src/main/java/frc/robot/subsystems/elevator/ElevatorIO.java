package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
    @AutoLog
    public class ElevatorIOInputs {
        public double PositionRads = 0;
        public double VelocityRMP = 0;
        public double AppliedVolts = 0;
        public double SupplyCurrent = 0;
        public double TempCelcius = 0;
    }

    default void updateInputs(ElevatorIOInputs inputs) {}

    default void stop() {}

    default void runVelocity(int motorRPM) {}

    default void setSlot0(double kP, double kI, double kD, double kS, double kV, double kA) {}

}
