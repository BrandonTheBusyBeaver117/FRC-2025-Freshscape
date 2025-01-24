package frc.robot.subsystems.pivot;

import java.io.ObjectInputFilter.Status;
import java.util.Optional;

import javax.swing.text.Position;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.subsystems.pivot.Pivot.PivotTarget;

public class PivotIOTalonFX implements PivotIO {



    private final TalonFX pivotMotor;
    private final StatusSignal<Angle> positionRotations;
    private final StatusSignal<AngularVelocity> velocityRotPerSec;
    private final StatusSignal<Voltage> appliedVolts;
    private final StatusSignal<Current> supplyCurrentAmp;
    private final StatusSignal<Temperature> temp;
    
    Optional<Integer> canCoderID;

    public PivotIOTalonFX(int motorID,
    boolean inverted,
    double supplyCurrentLimit,
    Optional<Integer> canCoderID,
    double reduction,
    double upperLimit,
    double upperVoltLimit,
    double lowerVoltLimit) {
        //status targets, velocity, position, etc.
        this.pivotMotor = new TalonFX(motorID);
        velocityRotPerSec = pivotMotor.getVelocity();
        positionRotations = pivotMotor.getPosition();
        appliedVolts = pivotMotor.getMotorVoltage();
        supplyCurrentAmp = pivotMotor.getSupplyCurrent();
        temp = pivotMotor.getDeviceTemp();
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

      canCoder.getConfigurator().setPosition(0);
      config.Feedback.withRemoteCANcoder(canCoder);
      config.Feedback.withSensorToMechanismRatio(reduction);
    }
    pivotMotor.getConfigurator().apply(config);
    pivotMotor.setPosition(0);
    pivotMotor.setNeutralMode(NeutralModeValue.Brake);

    pivotMotor.optimizeBusUtilization();
  }

    @Override
    public void updateInputs(PivotIOInputs inputs) {
    inputs.connected =
        BaseStatusSignal.refreshAll(
                positionRotations, velocityRotPerSec, appliedVolts, supplyCurrentAmp, temp)
            .isOK();
    inputs.positionRotations = positionRotations.getValueAsDouble();
    inputs.velocityRotPerSec = velocityRotPerSec.getValueAsDouble();
    inputs.appliedVolts = appliedVolts.getValueAsDouble();
    inputs.supplyCurrentAmps = supplyCurrentAmp.getValueAsDouble();
    inputs.tempCelsius = temp.getValueAsDouble();

    }

    @Override
    public void runPosition(double position) {
       
    }

    @Override
    public void stop() {
        
    }

}