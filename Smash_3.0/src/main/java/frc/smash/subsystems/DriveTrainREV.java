package frc.smash.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrainREV {

    // Declaração dos motores para cada roda
    private CANSparkMax motorFrontLeft;
    private CANSparkMax motorBackLeft;
    private CANSparkMax motorFrontRight;
    private CANSparkMax motorBackRight;

    // Declaração do objeto DifferentialDrive para controlar o sistema de tração
    private DifferentialDrive driveTrain;

    // Variáveis para configurar os limites de saída do controlador
    private double motorOutMax, motorOutMin;

    // Construtor da classe DriveTrainREV
    public DriveTrainREV(int idFrontLeft, int idBackLeft, int idFrontRight, int idBackRight) {
    
        motorFrontLeft  = new CANSparkMax(idFrontLeft, MotorType.kBrushed);
        motorBackLeft   = new CANSparkMax(idBackLeft, MotorType.kBrushed);
        motorFrontRight = new CANSparkMax(idFrontRight, MotorType.kBrushed);
        motorBackRight  = new CANSparkMax(idBackRight, MotorType.kBrushed);
    

        // Inicializa o objeto DifferentialDrive com os motores da frente
        driveTrain = new DifferentialDrive(motorFrontLeft, motorFrontRight);

        // Define os motores traseiros para seguir os motores da frente
        motorBackLeft.follow(motorFrontLeft);
        motorBackRight.follow(motorFrontRight);

    }

    // Método para definir o valor máximo de saída do controlador
    public void setMotorOutMax(double outMax) {
        this.motorOutMax = outMax;
    }

    // Método para definir o valor mínimo de saída do controlador
    public void setMotorOutMin(double outMin) {
        this.motorOutMin = outMin;
    }

    // Método para controlar o sistema de tração (driveTrain) com limites de saída
    // configuráveis
    public void driveTrainController(double forward, double rotation, double inS) {

        // Calcula a velocidade com base nos limites de saída configurados
        double speed = (inS - 1) * (motorOutMax - motorOutMin) / -2 + motorOutMin;

        // Aciona o driveTrain com a velocidade ajustada e os parâmetros de controle
        driveTrain.arcadeDrive(speed * forward, speed * rotation);
    }

    public void driveTrainController(double forward, double rotation) {

        // Aciona o driveTrain com a velocidade ajustada e os parâmetros de controle
        driveTrain.arcadeDrive(forward, rotation);
    }

}
