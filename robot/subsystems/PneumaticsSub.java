// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticsSub extends SubsystemBase {
  /** Creates a new PneumaticsSub. */
  Compressor Compressor;
  DoubleSolenoid IN_OUT, Piston;
  boolean Trigger;
  public PneumaticsSub() {


    IN_OUT = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,5,2);

    Piston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,1,6);


  }

  public void Compressor()
  {
    boolean pressureSwitch = Compressor.getPressureSwitchValue();
    
    if(pressureSwitch)
    {
      Compressor.start();
    }else
    {
      Compressor.stop();
    }
  }

  public void Extend()
  {
  IN_OUT.set(Value.kForward);
    Trigger = true;
  }

  public void Retract()
  {
   IN_OUT.set(Value.kReverse);
    Trigger = true;
  }

  public void Fast()
  {
    Piston.set(Value.kForward);
    Trigger = true;
  }

  public void Slow()
  {
    Piston.set(Value.kReverse);
    Trigger = true;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
