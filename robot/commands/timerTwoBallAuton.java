// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class timerTwoBallAuton extends CommandBase {
  /** Creates a new timerTwoBallAuton. */
  Timer time;
  int frontCount;
  int turnCount;
  
  public timerTwoBallAuton() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.maArcadeSub);
    addRequirements(RobotContainer.mTransportSub);
    addRequirements(RobotContainer.mArmSub);
    addRequirements(RobotContainer.mIntakeSub);
    addRequirements(RobotContainer.mShooterSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time = new Timer();
    
    turnCount = 0;

    //RobotContainer.maArcadeSub.calibrateNavX();
    //RobotContainer.maArcadeSub.resetNavX();
    RobotContainer.maArcadeSub.resetTicks();
  
  time.reset();
    time.start();
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    

 RobotContainer.mShooterSub.setShooterPower();


 if(time.get()>=2&&time.get()<=4.5){
   RobotContainer.mShooterSub.stopShooter();
   RobotContainer.mTransportSub.toShooter();
 }
 else if(time.get()>=4.6 && time.get()<=4.8){
   RobotContainer.mTransportSub.stopTransport();
   // (right, left)
   RobotContainer.maArcadeSub.setRaw(1.0, -1.0);   //goes backward
 }
 //stops the robot and starts making a 180 degree right turn while bringing the arm out
 else if(time.get()>=5.2 && time.get()<=5.3){
 RobotContainer.maArcadeSub.setRaw(0.0, 0.0);
 RobotContainer.maArcadeSub.setRaw(-1.0,  1.0);
 RobotContainer.mArmSub.armOut();

}
//stops both arm and drive once the robot makes a 180 degree right turn and arm is fully out
else if(RobotContainer.maArcadeSub.getNavXAngle()>=179 && turnCount==0&& time.get()>=5.3 && time.get()<=6.5){
  RobotContainer.maArcadeSub.setRaw(0.0, 0.0);
  RobotContainer.mArmSub.stopArm();
  turnCount++;
}
else if(time.get()>=6.5 && time.get()<=7.75){
  RobotContainer.mIntakeSub.intakeIn();
  RobotContainer.maArcadeSub.setRaw(-1.0, 1.0);
}



    


    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.maArcadeSub.setRaw(0.0, 0.0);
    RobotContainer.mTransportSub.stopTransport();
    RobotContainer.mShooterSub.stopShooter();
    time.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
