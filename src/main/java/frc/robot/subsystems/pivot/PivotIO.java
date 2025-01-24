package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.AutoLog;

public interface PivotIO {
    @AutoLog
    public class PivotIOInputs {
        public boolean connected = true;
        public double positionRotations = 0;
        public double velocityRotPerSec = 0;
        public double appliedVolts = 0;
        public double supplyCurrentAmps = 0;
        public boolean zeroing = false;
    }

    public void runPosition(double position);

    public void runZeroing();

    public void stop();

    public void updateInputs(PivotIOInputs pivotIOInputs);



}
