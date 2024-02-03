package frc.smash;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

import frc.smash.Subsystems.DriveTrainREV;
import frc.smash.Subsystems.Intake;
import frc.smash.Subsystems.Shooter;
import frc.smash.Assistent.*;

public class Robot extends TimedRobot {
  private Joystick player1;
  private XboxController player2;

  private DriveTrainREV driveTrainREV;
  private Intake intake;
  private Shooter shooter;

  private PIDController distancePID;
  private PIDController anglePID;

  private Limelight limelight;

  private double distanceCommand, angleCommand;

  @Override
  public void robotInit() {

    player1 = new Joystick(0);
    player2 = new XboxController(1);

    driveTrainREV = new DriveTrainREV(1, 2, 3, 4, 0);
    driveTrainREV.setMotorOutMax(0.7);
    driveTrainREV.setMotorOutMin(0.4);

    intake = new Intake(5,6,5);
    shooter = new Shooter();

    limelight = new Limelight("limelight", 50.0, 20.0, 10.0);

    distancePID = new PIDController(0.30, 0.001, 0.02, 0.4, -0.4, 30);
    distancePID.setSetpoint(60);
    anglePID = new PIDController(0.05, 0.001, 0.01, 0.4, -0.4, 30);
    anglePID.setSetpoint(0.1);

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
  public void teleopPeriodic()
  {
    RobotController();
    IntakeController();
    ShooterController();

  }

  void RobotController() {
    distanceCommand = distancePID.calculate(limelight.getDistanceToTarget());
    SmartDashboard.putNumber("distanceCommand", distanceCommand);
    angleCommand = anglePID.calculate(limelight.getAngleToTarget());
    SmartDashboard.putNumber("angleCommand", angleCommand);

    if (player1.getRawButton(1)) {
      if (limelight.ValidateTarget()) {

        driveTrainREV.driveTrainController(angleCommand, -distanceCommand);

      } else {
        driveTrainREV.driveTrainController(0.6, 0.3);
      }

    } else {
      driveTrainREV.driveTrainController(player1.getZ(), -player1.getY(), player1.getRawAxis(3));
    }
  }

  void IntakeController()
  {

  }

  void ShooterController()
  {

  }
}