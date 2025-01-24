package frc.robot.subsystems.pivot;

import java.util.Optional;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.ControlModeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public class PivotIOTalonFX implements PivotIO {



    private final TalonFX pivotMotor;
    private final StatusSignal<Angle> positionRotations;
    private final StatusSignal<AngularVelocity> velocityRotPerSec;
    private final StatusSignal<Voltage> appliedVolts;
    private final StatusSignal<Current> supplyCurrentAmp;


    
    private final double zeroingVolts;
    private final double zeroingOffset;

    private final VoltageOut voltageOutput = new VoltageOut(0).withUpdateFreqHz(0);
    private final NeutralOut neutralOutput = new NeutralOut();
    private final PositionVoltage positionControl = new PositionVoltage(0).withUpdateFreqHz(0);
   
    Optional<Integer> canCoderID;

    public PivotIOTalonFX(int motorID,
    boolean inverted,
    double supplyCurrentLimit,
    Optional<Integer> canCoderID,
    double reduction,
    double upperLimit,
    double lowerLimit, 
    double upperVoltLimit,
    double lowerVoltLimit,
    double zeroingVolts, 
    double zeroingOffset

    ) {
        //status targets, velocity, position, etc.
        this.pivotMotor = new TalonFX(motorID);
        velocityRotPerSec = pivotMotor.getVelocity();
        positionRotations = pivotMotor.getPosition();
        appliedVolts = pivotMotor.getMotorVoltage();
        supplyCurrentAmp = pivotMotor.getSupplyCurrent();
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotorOutput.Inverted = 
            inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        BaseStatusSignal.setUpdateFrequencyForAll(50, positionRotations, velocityRotPerSec, appliedVolts, supplyCurrentAmp, temp);
        config.CurrentLimits.SupplyCurrentLimit = supplyCurrentLimit;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.SoftwareLimitSwitch.withForwardSoftLimitEnable(true);
        config.SoftwareLimitSwitch.withForwardSoftLimitThreshold(upperLimit);
        config.Voltage.withPeakForwardVoltage(upperVoltLimit);
        config.Voltage.withPeakReverseVoltage(lowerVoltLimit);
        config.Feedback.withSensorToMechanismRatio(reduction);

        this.zeroingVolts = zeroingVolts;
        this.zeroingOffset = zeroingOffset;

    // CANCODER CONFIG
    if (canCoderID.isPresent()) {
      CANcoder canCoder = new CANcoder(canCoderID.get());
      canCoder
          .getConfigurator()
          .apply(
              new CANcoderConfiguration()
                  .withMagnetSensor(
                      new MagnetSensorConfigs()
                          .withSensorDirection(SensorDirectionValue.Clockwise_Positive)
                          .withMagnetOffset(0)));

        BaseStatusSignal.setUpdateFrequencyForAll(50, positionRotations, velocityRotPerSec, appliedVolts, supplyCurrentAmp);

        pivotMotor.optimizeBusUtilization();

    }

    @Override
    public void runPosition(double rotations) {
       this.pivotMotor.setControl(positionControl.withPosition(rotations));
    }

    @Override
    public void runZeroing() {
        this.pivotMotor.setControl(voltageOutput.withOutput(zeroingVolts));
    }

    @Override
    public void stop() {
        this.pivotMotor.setControl(neutralOutput);
        
    }

    @Override
    public void updateInputs(PivotIOInputs pivotIOInputs) {
       
    }
}