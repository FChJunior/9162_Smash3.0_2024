package frc.smash.Subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
    private CANSparkMax motorIntakeArm;

    private WPI_VictorSPX motorIntakeUp;
    private WPI_VictorSPX motorIntakeDown;

    private SparkPIDController intakePIDController;
    private RelativeEncoder encoderMotorIntakeArm;
    private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public Intake(int idMotorIntakeArm, int idMotorIntakeUp, int idmotorIntakeDown) {
        motorIntakeArm = new CANSparkMax(idMotorIntakeArm, MotorType.kBrushless);
        motorIntakeUp = new WPI_VictorSPX(idMotorIntakeUp);
        motorIntakeDown = new WPI_VictorSPX(idmotorIntakeDown);
        
        motorIntakeArm.restoreFactoryDefaults();
        encoderMotorIntakeArm = motorIntakeArm.getEncoder();
        encoderMotorIntakeArm.setPosition(0);
        intakePIDController = motorIntakeArm.getPIDController();
        SetupPID();
    }

    private void SetupPID() {
        kP = 0.05;
        kI = 1e-4;
        kD = 1;
        kIz = 0;
        kFF = 0;
        kMaxOutput = 0.2;
        kMinOutput = -0.2;

        // set PID coefficients
        intakePIDController.setP(kP); 
        intakePIDController.setI(kI);
        intakePIDController.setD(kD);
        intakePIDController.setIZone(kIz);
        intakePIDController.setFF(kFF);
        intakePIDController.setOutputRange(kMinOutput, kMaxOutput);

    }

    public void MovePosition(double position) {
        //PIDWrite();
        intakePIDController.setReference(position, CANSparkMax.ControlType.kPosition);
        SmartDashboard.putNumber("ProcessVariable", encoderMotorIntakeArm.getPosition());
    }

    public void IntakeGet(double speed) {
        motorIntakeUp.set(-speed);
        motorIntakeDown.set(-speed);
    }

    public void setPosition(int position)
    {
        encoderMotorIntakeArm.setPosition(position);
    }
}
