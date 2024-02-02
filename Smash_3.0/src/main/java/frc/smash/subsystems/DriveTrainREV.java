package frc.smash.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrainREV {

    private CANSparkMax leftFront;
    private CANSparkMax leftBack;
    private CANSparkMax rightFront;
    private CANSparkMax rightBack;

    private DifferentialDrive driveTrain;
    private double outMax, outMin;

    public DriveTrainREV(int idLF, int idLB, int idRF, int idRB, int mototType) {
        switch (mototType) {
            case 0:
                leftFront = new CANSparkMax(idLF, MotorType.kBrushed);
                leftBack = new CANSparkMax(idLB, MotorType.kBrushed);
                rightFront = new CANSparkMax(idRF, MotorType.kBrushed);
                rightBack = new CANSparkMax(idRB, MotorType.kBrushed);
                break;

            case 1:
                leftFront = new CANSparkMax(idLF, MotorType.kBrushless);
                leftBack = new CANSparkMax(idLB, MotorType.kBrushless);
                rightFront = new CANSparkMax(idRF, MotorType.kBrushless);
                rightBack = new CANSparkMax(idRB, MotorType.kBrushless);
                break;
        }

        driveTrain = new DifferentialDrive(leftFront, rightFront);
        leftBack.follow(leftFront);
        rightBack.follow(rightFront);
    }


    public void setOutMax(double outMax) {
        this.outMax = outMax;
    }

    public void setOutMin(double outMin) {
        this.outMin = outMin;
    }

    public void driveTrainController(double forward, double rotation, double inS) {

        double speed = (inS - 1) * (outMax - outMin)/ -2 + outMin;
        
        driveTrain.arcadeDrive(speed * forward, speed * rotation);

        SmartDashboard.putNumber("speed", speed);
        SmartDashboard.putNumber("inS", inS);
        SmartDashboard.putNumber("forward", forward);
        SmartDashboard.putNumber("rotation", rotation);
        
    }

}
