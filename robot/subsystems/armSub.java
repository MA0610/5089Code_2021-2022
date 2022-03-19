// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.lang.invoke.ConstantBootstraps;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class armSub extends SubsystemBase {
  /** Creates a new armSub. */
  VictorSPX leftArm,rightArm;
 // CANSparkMax intake;
  public armSub() {
    leftArm = new VictorSPX(Constants.leftArm);
    rightArm = new VictorSPX(Constants.rightArm);
    //intake = new CANSparkMax(Constants.intakeMotor,MotorType.kBrushless);
  }
  public void armOut(){
    leftArm.set(ControlMode.PercentOutput, 0.6);
    rightArm.set(ControlMode.PercentOutput, -0.6);

  }
  public void armIn(){
    leftArm.set(ControlMode.PercentOutput, -0.5);
    rightArm.set(ControlMode.PercentOutput, 0.5);
  }
  public void stopArm(){
    leftArm.set(ControlMode.PercentOutput, 0.0);
    rightArm.set(ControlMode.PercentOutput, 0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
