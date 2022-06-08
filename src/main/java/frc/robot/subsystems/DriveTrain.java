package frc.robot.subsystems;

//Vendor imports
import com.studica.frc.TitanQuad;
import com.studica.frc.Servo;

//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * DriveTrain class 
 * <p>
 * This class creates the instance of the Titan and enables and sets the speed of the defined motor. 
 */
public class DriveTrain extends SubsystemBase
{
    /**
     * Motors
     */
    private TitanQuad motorR;
    private TitanQuad motorL;
    private Servo servoC;

    /**
     * Constructor
     */
    public DriveTrain()
    {
        //Motors
        motorR = new TitanQuad(Constants.TITAN_ID, Constants.MOTORR);
        motorL = new TitanQuad(Constants.TITAN_ID, Constants.MOTORL);
        servoC = new Servo(Constants.SERVO_C);
    }

    /**
     * Sets the speed of the motor
     * <p>
     * @param speed range -1 to 1 (0 stop)
     */
    public void setMotorSpeed(double speedR, double speedL)
    {
        motorR.set(speedR);
        motorL.set(speedL);
    } 
    /**
    * Sets the speed of the continuous servo motor
    * <p>
    * @param speed range -1 to 1 (0 stop)
    */
   public void setServoSpeed(double speed)
   {
       servoC.setAngle(speed);
   }


}