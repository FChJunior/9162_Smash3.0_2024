package frc.smash;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;

import frc.smash.Subsystems.*;
import frc.smash.Assistent.*;

public class Robot extends TimedRobot {
  private Joystick player1;
  private XboxController player2;

  private DriveTrainREV driveTrainREV;
  private Intake intake;
  private Shooter shooter;
  private Climb climb;

  private PIDController distancePID;
  private PIDController anglePID;

  private Limelight limelight;

  private Timer timer;

  private double distanceCommand, angleCommand;

  @Override
  public void robotInit() {
    player1 = new Joystick(1);
    player2 = new XboxController(0);

    driveTrainREV = new DriveTrainREV(1, 2, 3, 4);
    driveTrainREV.setMotorOutMax(1);
    driveTrainREV.setMotorOutMin(0.5);

    intake = new Intake(5, 1, 2);
    shooter = new Shooter(5, 6);
    climb = new Climb();

    limelight = new Limelight("limelight", 57.0, 1);

    distancePID = new PIDController(0.1, 0.0001, 0, 0.4, -0.4, 30);
    anglePID = new PIDController(0.01, 0.0001, 0, 0.4, -0.4, 30);
    anglePID.setSetpoint(0.2);

    CameraServer.startAutomaticCapture();
  }

  @Override
  public void autonomousInit() {
    timer.start();
    intake.setPosition(0);
  }

  @Override
  public void autonomousPeriodic() {
    shooter.ShooterSpeed(0.4);

    if (timer.get() == 0.5) {
      intake.IntakeGet(-1);
    }

    if (timer.get() == 1.5) {
      intake.MovePosition(28);
      shooter.ShooterSpeed(0);
    }

    if (timer.get() >= 2 && timer.get() < 4) {
      intake.IntakeGet(1);
      distancePID.setSetpoint(100);
      distanceCommand = distancePID.calculate(limelight.getDistanceToTarget());
      angleCommand = anglePID.calculate(limelight.getAngleToTarget());
      driveTrainREV.driveTrainController(0, -0.4);
    }

    if (timer.get() == 4) {
      intake.MovePosition(0);
      intake.IntakeGet(0);
    }

    if (timer.get() == 5) {
      shooter.ShooterSpeed(0.5);
    }

    if (timer.get() == 6) {
      intake.IntakeGet(-1);
    }

    if (timer.get() == 7) {
      shooter.ShooterSpeed(0);
    }
  }

  @Override
  public void teleopInit() {
    timer.stop();
    shooter.ShooterSpeed(0);
    intake.MovePosition(0);
    intake.IntakeGet(0);
  }

  @Override
  public void teleopPeriodic() {
    RobotController();
    IntakeController();
    ShooterController();
    ClimbController();
  }

  void RobotController() {
    distanceCommand = distancePID.calculate(limelight.getDistanceToTarget());
    SmartDashboard.putNumber("distanceCommand", distanceCommand);
    angleCommand = anglePID.calculate(limelight.getAngleToTarget());
    SmartDashboard.putNumber("angleCommand", angleCommand);

    if (player1.getRawButton(1)) {
      if (limelight.ValidateTarget()) {
        if (limelight.getAprilTagId() == 7) {
          limelight.setTargetHeight(132);
          distancePID.setSetpoint(40);
        }

        driveTrainREV.driveTrainController(angleCommand, -distanceCommand);
      }

    } else {
      driveTrainREV.driveTrainController(-player1.getZ(), player1.getY(), player1.getRawAxis(3));
    }
  }

  void IntakeController() {
    if (player2.getRightBumperPressed()) {
      intake.MovePosition(28);
    } else if (player2.getLeftBumperPressed()) {
      intake.MovePosition(0);
    }

    if (player2.getXButton()) // botão de sugar
    {
      intake.IntakeGet(1);
    } else if (player2.getYButton())
      intake.IntakeGet(-1);// botão de cuspir

    else
      intake.IntakeGet(0);
  }

  void ShooterController() {
    if (player2.getAButton())
      shooter.ShooterSpeed(-0.55);
    else if (player2.getBButton())
      shooter.ShooterSpeed(-0.2);
    else
      shooter.ShooterSpeed(0);
  }

  void ClimbController() {
    climb.ClimbingDown(player2.getLeftY() == -1, player2.getRightY() == -1);
    climb.ClimbingUP(player2.getLeftY() == 1, player2.getRightY() == 1);

    if (player2.getStartButtonPressed())
      climb.compressor.enableDigital();
    if (player2.getBackButtonPressed())
      climb.compressor.disable();
  }
}