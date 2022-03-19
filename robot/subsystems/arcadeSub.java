// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.lang.invoke.ConstantBootstraps;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class arcadeSub extends SubsystemBase {
  /** Creates a new tankSub. */

  AHRS navX;
  public static SPI.Port navXPort = SPI.Port.kMXP;

  TalonFX FLDrive, BLDrive, FRDrive, BRDrive;
  public arcadeSub() {

  FLDrive = new TalonFX(Constants.FLMotor);
  BLDrive = new TalonFX(Constants.BLMotor);
  FRDrive = new TalonFX(Constants.FRMotor);
  BRDrive = new TalonFX(Constants.BRMotor);


  FRDrive.setNeutralMode(NeutralMode.Brake);
  BRDrive.setNeutralMode(NeutralMode.Brake);
  FLDrive.setNeutralMode(NeutralMode.Brake);
  BLDrive.setNeutralMode(NeutralMode.Brake);


  FRDrive.config_kF(0, 0.01);
  BRDrive.config_kF(0, 0.01);
  FLDrive.config_kF(0, 0.01);
  BLDrive.config_kF(0, 0.01);
  
  
  FRDrive.config_kP(0, 0.1);
  BRDrive.config_kP(0, 0.1);
  FLDrive.config_kP(0, 0.1);
  BLDrive.config_kP(0, 0.1);
  
  
  //this is the max acceleration the talons can go when going by encoder
  //ex 2048*3 is like 3 rotations per second

  //original was 2048*2
  FRDrive.configMotionAcceleration(2048*4);
  BRDrive.configMotionAcceleration(2048*4);
  FLDrive.configMotionAcceleration(2048*4);
  BLDrive.configMotionAcceleration(2048*4);
  

  //4 ft/sec transfer to meters
  //this sets the max velocity you can use with the talons when going by encoder
  //use same idea for speed as acceleration

  //original was 2048*3
  FRDrive.configMotionCruiseVelocity(2048*6);
  BRDrive.configMotionCruiseVelocity(2048*6);
  FLDrive.configMotionCruiseVelocity(2048*6);
  BLDrive.configMotionCruiseVelocity(2048*6);
  //3.658 m/sec


  //inverts the motors
  FLDrive.setInverted(false);
  BLDrive.setInverted(false);
  
  FRDrive.setInverted(false);
  BRDrive.setInverted(false);
  
//makes it so that your back two motors are on the same page as your front two motors, they won't fight each other at some point  
  BLDrive.set(ControlMode.Follower, FLDrive.getDeviceID());
  BRDrive.set(ControlMode.Follower, FRDrive.getDeviceID());
  


  FLDrive.configPeakOutputForward(1);
  FLDrive.configPeakOutputReverse(-1);
  
  FRDrive.configPeakOutputForward(1);
  FRDrive.configPeakOutputReverse(-1);
  


  }

//gyroscope

public void calibrateNavX(){
  navX.calibrate();
}

public void resetNavX(){
  navX.reset();
}

public double getNavXAngle(){
  return navX.getAngle();
}
/*
public double setGyroAngle(){
return navX.getAngle();
}
*/
//changes coast/brake setting for drive
public void setBrake(){
  FRDrive.setNeutralMode(NeutralMode.Brake);
  BRDrive.setNeutralMode(NeutralMode.Brake);
  FLDrive.setNeutralMode(NeutralMode.Brake);
  BLDrive.setNeutralMode(NeutralMode.Brake);
}

public void setCoast(){
  FRDrive.setNeutralMode(NeutralMode.Coast);
  BRDrive.setNeutralMode(NeutralMode.Coast);
  FLDrive.setNeutralMode(NeutralMode.Coast);
  BLDrive.setNeutralMode(NeutralMode.Coast);
}


//method that sets the power of the drive using arcade style commands
  public void setPower(double throttle, double turn){

    double leftOutput = -throttle +turn;
    double rightOutput = -throttle -turn;
    
    FLDrive.set(ControlMode.PercentOutput, leftOutput);
    BLDrive.set(ControlMode.PercentOutput, leftOutput);
    FRDrive.set(ControlMode.PercentOutput, -rightOutput);
    BRDrive.set(ControlMode.PercentOutput, -rightOutput);
    
    
    
    }



  //method that sets the speed of the motors using tank drive commands
  public void setRaw(double Right, double Left){
    FLDrive.set(ControlMode.PercentOutput, Left);
    BLDrive.set(ControlMode.PercentOutput, Left);
    FRDrive.set(ControlMode.PercentOutput, Right);
    BRDrive.set(ControlMode.PercentOutput, Right);



  }
//gets the number of ticks the left encoder is on
  public double getLeftEncoderCount(){
    return FLDrive.getSelectedSensorPosition();
    
    }

    //gets the number of ticks the right encoder is on
    public double getRightEncoderCount(){
      return FRDrive.getSelectedSensorPosition();
      
      }


      //method that sets the target tick value for the encoders
      public void setEncoderTarget(double leftEncoderTarget, double rightEncoderTarget){

        FRDrive.set(ControlMode.MotionMagic,rightEncoderTarget);
        FLDrive.set(ControlMode.MotionMagic,leftEncoderTarget);
    
      }

      //method that resets the tick count for the encoders
    public void resetTicks(){
    
    FLDrive.getSensorCollection().setIntegratedSensorPosition(0, 30);
    BLDrive.getSensorCollection().setIntegratedSensorPosition(0, 30);
    FRDrive.getSensorCollection().setIntegratedSensorPosition(0, 30);
    BRDrive.getSensorCollection().setIntegratedSensorPosition(0, 30);
    
    }

    //method that returns whether or not your encoder reached its target
    public boolean onTarget(){


      return Math.abs(FLDrive.getClosedLoopError())<100 && Math.abs(FRDrive.getClosedLoopError())<100; 
     }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
