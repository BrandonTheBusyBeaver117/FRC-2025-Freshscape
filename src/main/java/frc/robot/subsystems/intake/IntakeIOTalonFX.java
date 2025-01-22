package frc.robot.subsystems.intake;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public class IntakeIOTalonFX implements IntakeIO {
    private final TalonFX talon;
    
    private final StatusSignal<Angle> position;
    private final StatusSignal<AngularVelocity> velocity;
    private final StatusSignal<Voltage> appliedVolts;
    private final StatusSignal<Current> supplyCurrent;

    private final VoltageOut voltageOutput = new VoltageOut(0).withUpdateFreqHz(0);
    private final NeutralOut neutralOutput = new NeutralOut();

    private final double mechanismReduction;

    public IntakeIOTalonFX(
        int id, int currentLimitAmps, boolean inverted, boolean brake, double reduction) {
            talon = new TalonFX(id);

            mechanismReduction = reduction;

            TalonFXConfiguration config = new TalonFXConfiguration();
            config.MotorOutput.Inverted = 
                inverted ? InvertedValue.Clockwise_Poitive : InvertedValue.Counterclockwise_Positive;
            config.MotorOutput.NeutralMode = brake ? NeutralModeValue.Brake : NeutralModeValue.Coast;
            config.CurrentLimits.SupplyCurrentLimit = currentLimitAmps;
            config.CurrentLimits.SupplyCurrentLimit = currentLimitAmps;
            config.CurrentLimits.SupplyCurrentLimitEnable = true;
            talon.getConfigurator().apply(config);

            position = talon.getPosition();
            velocity = talon.getVelocity();
            appliedVolts = talon.getMotorVoltage();
            supplyCurrent = talon.getSupplyCurrent();
            BaseStatusSignal.setUpdateFrequencyForAll(null, null)
        }
    )
}
