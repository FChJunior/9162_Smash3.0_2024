package frc.smash;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import frc.smash.subsystems.DriveTrainREV;

public class Robot extends TimedRobot 
{
  private Joystick       player1;
  private XboxController player2;

  private DriveTrainREV driveTrainREV;

  @Override
  public void robotInit() {

    player1 = new Joystick(0);
    player2 = new XboxController(1);

    
    driveTrainREV = new DriveTrainREV(1, 2, 3, 4, 0);
    driveTrainREV.setOutMax(0.7);
    driveTrainREV.setOutMin(0.4);

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {

    //
  }

  @Override
  public void teleopPeriodic() {

    driveTrainREV.driveTrainController(player1.getZ(), player1.getY(), player1.getRawAxis(3));
  }

}