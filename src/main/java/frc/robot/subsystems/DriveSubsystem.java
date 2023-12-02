// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

  private TalonSRX talon;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {

    talon = new TalonSRX(1);

    // talon.configFactoryDefault(); 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setMotor(double speed){
    talon.set(ControlMode.PercentOutput, speed);
  }

  public void stop(){
    talon.set(ControlMode.PercentOutput, 0);
  }

}
