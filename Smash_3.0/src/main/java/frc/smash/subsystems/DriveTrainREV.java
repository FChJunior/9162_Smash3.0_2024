package frc.smash.Subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrainREV {

    // Declaração dos motores para cada roda
    private CANSparkMax leftFront;
    private CANSparkMax leftBack;
    private CANSparkMax rightFront;
    private CANSparkMax rightBack;

    // Declaração do objeto DifferentialDrive para controlar o sistema de tração
    private DifferentialDrive driveTrain;

    // Variáveis para configurar os limites de saída do controlador
    private double outMax, outMin;

    // Construtor da classe DriveTrainREV
    public DriveTrainREV(int idLF, int idLB, int idRF, int idRB, int motorType) {
        // Seleciona o tipo de motor com base no parâmetro motorType
        switch (motorType) {
            case 0:
                leftFront = new CANSparkMax(idLF, MotorType.kBrushed);
                leftBack = new CANSparkMax(idLB, MotorType.kBrushed);
                rightFront = new CANSparkMax(idRF, MotorType.kBrushed);
                rightBack = new CANSparkMax(idRB, MotorType.kBrushed);
                break;

            case 1:
                leftFront = new CANSparkMax(idLF, MotorType.kBrushless);
                leftBack = new CANSparkMax(idLB, MotorType.kBrushless);
                rightFront = new CANSparkMax(idRF, MotorType.kBrushless);
                rightBack = new CANSparkMax(idRB, MotorType.kBrushless);
                break;
        }

        // Inicializa o objeto DifferentialDrive com os motores da frente
        driveTrain = new DifferentialDrive(leftFront, rightFront);

        // Define os motores traseiros para seguir os motores da frente
        leftBack.follow(leftFront);
        rightBack.follow(rightFront);
    }

    // Método para definir o valor máximo de saída do controlador
    public void setOutMax(double outMax) {
        this.outMax = outMax;
    }

    // Método para definir o valor mínimo de saída do controlador
    public void setOutMin(double outMin) {
        this.outMin = outMin;
    }

    // Método para controlar o sistema de tração (driveTrain) com limites de saída configuráveis
    public void driveTrainController(double forward, double rotation, double inS) {

        // Calcula a velocidade com base nos limites de saída configurados
        double speed = (inS - 1) * (outMax - outMin) / -2 + outMin;
        
        // Aciona o driveTrain com a velocidade ajustada e os parâmetros de controle
        driveTrain.arcadeDrive(speed * forward, speed * rotation);
    }

       public void driveTrainController(double forward, double rotation) {
        
        // Aciona o driveTrain com a velocidade ajustada e os parâmetros de controle
        driveTrain.arcadeDrive(forward, rotation);
    }

}
