package frc.smash.Assistent;

import edu.wpi.first.wpilibj.Timer;

public class PIDController {
    private double kP;
    private double kI;
    private double kD;
    private double maxOutput;
    private double minOutput;

    private double setpoint;
    private double integral;
    private double derivative;
    private double previousError;
    private double maxIntegral;

    public PIDController(double kP, double kI, double kD, double maxOutput, double minOutput, double maxIntegral) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.maxOutput = maxOutput;
        this.minOutput = minOutput;
        this.maxIntegral = maxIntegral;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
        integral = 0;
        previousError = 0;
    }

    public double calculate(double input) {
        double error = setpoint - input;
    
        // Limite para evitar wind-up integral excessivo
        if (Math.abs(integral) < maxIntegral) {
            integral += (error * Timer.getFPGATimestamp());
        }
    
        derivative = (error - previousError) / Timer.getFPGATimestamp();
    
        double output = kP * error + kI * integral + kD * derivative;
    
        // Zerar o termo integral em situações específicas (ajuste conforme necessário)
        if (shouldResetIntegral(error)) {
            integral = 0.0;
        }
        
        // Limite de saída
        if (output > maxOutput) {
            output = maxOutput;
        } else if (output < minOutput) {
            output = minOutput;
        }
    
        previousError = error;
        return output;
    }
    
    // Método para verificar se o termo integral deve ser resetado
    private boolean shouldResetIntegral(double error) {
            return Math.abs(error) > maxIntegral;
    }
}
