package frc.smash.Subsystems;

import com.ctre.phoenix6.hardware.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {

    private TalonFX motorIntakeLeft;
    private TalonFX motorIntakeRight;

    private CANSparkMax motorIntakeArm;
    private RelativeEncoder encoderMotorIntakeArm;

    private SparkPIDController intakePIDController;
    private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    private double p, i, d, iz, ff, max, min;

    public Intake(int idMotorIntakeLeft, int idMotorIntakeRight, int idMotorIntakeArm)
    {
        motorIntakeLeft = new TalonFX(idMotorIntakeLeft, "rio");
        motorIntakeRight = new TalonFX(idMotorIntakeRight, "rio");

        motorIntakeArm = new CANSparkMax(idMotorIntakeArm, MotorType.kBrushless);

        encoderMotorIntakeArm = motorIntakeArm.getEncoder();
        SetupPID();
    }

    private void SetupPID()
    {
                // PID coefficients
                kP = 0.1;
                kI = 1e-4;
                kD = 0;
                kIz = 0;
                kFF = 0;
                kMaxOutput = 0.5;
                kMinOutput = -0.5;
        
                // set PID coefficients
                intakePIDController.setP(kP);
                intakePIDController.setI(kI);
                intakePIDController.setD(kD);
                intakePIDController.setIZone(kIz);
                intakePIDController.setFF(kFF);
                intakePIDController.setOutputRange(kMinOutput, kMaxOutput);
    }

    public void MovePosition(double position)
    {
        PIDWrite();
        intakePIDController.setReference(position, CANSparkMax.ControlType.kPosition);
        SmartDashboard.putNumber("ProcessVariable", encoderMotorIntakeArm.getPosition());
    }
    
    private void PIDWrite()
    {
        if ((p != kP)) {
            intakePIDController.setP(p);
            kP = p;
        }
        if ((i != kI)) {
            intakePIDController.setI(i);
            kI = i;
        }
        if ((d != kD)) {
            intakePIDController.setD(d);
            kD = d;
        }
        if ((iz != kIz)) {
            intakePIDController.setIZone(iz);
            kIz = iz;
        }
        if ((ff != kFF)) {
            intakePIDController.setFF(ff);
            kFF = ff;
        }
        if ((max != kMaxOutput) || (min != kMinOutput)) {
            intakePIDController.setOutputRange(min, max);
            kMinOutput = min;
            kMaxOutput = max;
        }

    }

    public void IntakeSpeed(double speed, int direction)
    {
        motorIntakeLeft.set(speed * direction);
        motorIntakeRight.set(-speed * direction);
    }
}
