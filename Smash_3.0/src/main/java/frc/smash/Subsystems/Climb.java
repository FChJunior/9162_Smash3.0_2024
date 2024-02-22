package frc.smash.Subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.Solenoid;

public class Climb {
    
    private PneumaticHub ph;
    public Compressor compressor;
    private Solenoid climbLeftUp, climbLeftDown;
    private Solenoid climbRightUp, climbRightDown;

    public Climb()
    {
        ph = new PneumaticHub(11);
        compressor     = ph.makeCompressor();
        climbLeftUp    = ph.makeSolenoid(8);
        climbLeftDown  = ph.makeSolenoid(9);
        climbRightUp   = ph.makeSolenoid(10);
        climbRightDown = ph.makeSolenoid(11);
           
    }

    public void ClimbingUP(boolean cl, boolean cr)
    {
        climbLeftUp.set(cl);
        climbRightUp.set(cr);
    }
    public void ClimbingDown(boolean cl, boolean cr)
    {
        climbLeftDown.set(cl);
        climbRightDown.set(cr);
    }
}
