package frc.smash.Subsystems;

import com.ctre.phoenix6.hardware.*;

public class Shooter {

    private TalonFX motorShooterLeft;
    private TalonFX motorShooterRight;

    public Shooter(int idMotorShooterLeft, int idMotorShooterRight)
    {
        motorShooterLeft = new TalonFX(idMotorShooterLeft, "rio");
        motorShooterRight = new TalonFX(idMotorShooterRight, "rio");
    }

    public void ShooterSpeed(double speed)
    {
        motorShooterLeft.set(speed);
        motorShooterRight.set(speed);   
    }
}
