// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

  /*
   *  0: collor wheel
      1: winch
      2: intake
      3: winch
      4: shooter
      5: right1
      6: right2
      7: left1
      8: left2
      9: indexer
   */
  private TalonSRX shooter1 = new TalonSRX(6);
  private TalonSRX shooter2 = new TalonSRX(5);
  private TalonSRX intake1 = new TalonSRX(2);
  private TalonSRX intake2 = new TalonSRX(1);
  private TalonSRX intakeTop = new TalonSRX(9);

  private TalonSRX left1 = new TalonSRX(8);
  private TalonSRX left2 = new TalonSRX(7);
  private TalonSRX right1 = new TalonSRX(0);
  private TalonSRX right2 = new TalonSRX(3);

  private int outtaking = 1, invertShooter = 1;
  
  //private final PWMSparkMax m_leftDrive = new PWMSparkMax(0);
  //private final PWMSparkMax m_rightDrive = new PWMSparkMax(1);
  //private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftDrive, m_rightDrive);
  private final XboxController m_controller = new XboxController(0);
  private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    //m_rightDrive.setInverted(true);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    /*
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
     */
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    //m_robotDrive.arcadeDrive(-m_controller.getLeftY(), -m_controller.getRightX());
    double intakePower = m_controller.getLeftTriggerAxis() * .7 * outtaking;
    intake1.set(ControlMode.PercentOutput, intakePower);
    intake2.set(ControlMode.PercentOutput, -intakePower);

    intakeTop.set(ControlMode.PercentOutput, intakePower);

    double shooterPower = m_controller.getRightTriggerAxis() * .7 * invertShooter;

    shooter1.set(ControlMode.PercentOutput, shooterPower);
    shooter2.set(ControlMode.PercentOutput, shooterPower);
    
    
    if (m_controller.getLeftBumperPressed())
      outtaking = -1;

    if (m_controller.getLeftBumperReleased())
      outtaking = 1;

    if (m_controller.getRightBumperPressed())
      invertShooter = -1;
    
    if (m_controller.getRightBumperReleased())
      invertShooter = 1;


    double leftIn = m_controller.getLeftY(), rightIn = m_controller.getRightY();

    leftIn = (Math.abs(leftIn) < 0.1 ? 0 : -leftIn);
    rightIn = (Math.abs(rightIn) < 0.1 ? 0 : rightIn);

    left1.set(ControlMode.PercentOutput, leftIn);
    left2.set(ControlMode.PercentOutput, leftIn);
    right1.set(ControlMode.PercentOutput, rightIn);
    right2.set(ControlMode.PercentOutput, rightIn);


    //left2.set(ControlMode.PercentOutput, m_controller.getLeftY());

    //right1.set(ControlMode.PercentOutput, m_controller.getRightY());
    //right2.set(ControlMode.PercentOutput, m_controller.getRightY());

    //print the controller output to the roborio console
    //System.out.println(m_controller.getLeftY());
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
