package frc.robot.subsystems.pivot;

import java.io.ObjectInputFilter.Status;

import javax.swing.text.Position;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
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


    

    public PivotIOTalonFX(int motorID, boolean inverted) {
        this.pivotMotor = new TalonFX(motorID);
        velocityRotPerSec = pivotMotor.getVelocity();
        positionRotations = pivotMotor.getPosition();
        appliedVolts = pivotMotor.getMotorVoltage();
        supplyCurrentAmp = pivotMotor.getSupplyCurrent();
        TalonFXConfiguration config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        config.MotorOutput.Inverted = 
            inverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;


        BaseStatusSignal.setUpdateFrequencyForAll(50, positionRotations, velocityRotPerSec, appliedVolts, supplyCurrentAmp);

        pivotMotor.optimizeBusUtilization();

    }

    @Override
    public void runPosition(double position) {
       
    }

    @Override
    public void stop() {
       
    }

    @Override
    public void updateInputs(PivotIOInputs pivotIOInputs) {
       
    }
}