package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public class ElevatorIOTalonFX implements ElevatorIO {
    private final TalonFX motor;
    private final VelocityVoltage voltageRequest;
    private final VelocityVoltage neutralOutput = new VelocityVoltage(0);

    private final Slot0Configs gainConfigs;

    private final StatusSignal<Angle> position;
    private final StatusSignal<AngularVelocity> velocity;
    private final StatusSignal<Voltage> voltage;
    private final StatusSignal<Current> current;
    private final StatusSignal<Temperature> temp;
    
    public ElevatorIOTalonFX() {
        this.motor = new TalonFX(ElevatorConstants.MOTOR_PORT);
        this.voltageRequest = new VelocityVoltage(0);

        this.gainConfigs = new Slot0Configs();
        this.gainConfigs.kP = ElevatorConstants.GAINS.kP();
        this.gainConfigs.kI = ElevatorConstants.GAINS.kI();
        this.gainConfigs.kD = ElevatorConstants.GAINS.kD();
        this.gainConfigs.kS = ElevatorConstants.GAINS.kS();
        this.gainConfigs.kV = ElevatorConstants.GAINS.kV();
        this.gainConfigs.kA = ElevatorConstants.GAINS.kA();

        this.motor.getConfigurator().apply(this.gainConfigs);

        this.position = this.motor.getPosition();
        this.velocity = this.motor.getVelocity();
        this.voltage = this.motor.getMotorVoltage();
        this.current = this.motor.getSupplyCurrent();
        this.temp = this.motor.getDeviceTemp();
    }

    public void updateInputs(ElevatorIOInputs inputs) {
        inputs.PositionRads = Units.rotationsToRadians(position.getValueAsDouble());
        inputs.VelocityRMP = velocity.getAppliedUpdateFrequency() * 60.0;
        inputs.AppliedVolts = voltage.getValueAsDouble();
        inputs.SupplyCurrent = current.getValueAsDouble();
        inputs.TempCelcius = temp.getValueAsDouble();
    }

    public void stop() {
        motor.setControl(neutralOutput);
    }

    public void runVelocity(int motorRPM) {
        motor.setControl(voltageRequest.withVelocity(motorRPM));
    }

    public void setSlot0(double kP, double kI, double kD, double kS, double kV, double kA) {
        gainConfigs.kP = kP;
        gainConfigs.kI = kI;
        gainConfigs.kD = kD;
        gainConfigs.kS = kS;
        gainConfigs.kV = kV;
        gainConfigs.kA = kA;

        motor.getConfigurator().apply(gainConfigs);
    }
}
