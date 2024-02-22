package frc.smash.Assistent;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {

    private NetworkTable table;
    private double targetHeight;
    private double cameraHeight;
    private double mountAngle;
    private double targetValidate;

    public Limelight(String tableName, double cameraHeight, double mountAngle) {
        table = NetworkTableInstance.getDefault().getTable(tableName);
        this.cameraHeight = cameraHeight;
        this.mountAngle = mountAngle;
    }

    public void setTargetHeight(double targetHeight)
    {
        this.targetHeight = targetHeight;
    }

    public double getDistanceToTarget() {
        double horizontalOffset = table.getEntry("tx").getDouble(0.0);
        double verticalOffset = table.getEntry("ty").getDouble(0.0);

        double distance = calculateDistance(horizontalOffset, verticalOffset, targetHeight, cameraHeight, mountAngle);
        SmartDashboard.putNumber("Distance", distance);
        return distance;
    }

    public double getAngleToTarget() {
        double horizontalOffset = table.getEntry("tx").getDouble(0.0);

        double angle = calculateAngle(horizontalOffset);
        return angle;
    }

    private double calculateDistance(double horizontalOffset, double verticalOffset, double targetHeight, double cameraHeight, double mountAngle) {
        double distance = (targetHeight - cameraHeight) / Math.tan(Math.toRadians(mountAngle + verticalOffset));
        return distance;
    }

    private double calculateAngle(double horizontalOffset) {
        double angle = Math.toDegrees(Math.atan(horizontalOffset / getDistanceToTarget()));
        return angle;
    }

    public boolean ValidateTarget()
    {  
        targetValidate = table.getEntry("tv").getDouble(0.0);
        return targetValidate == 1;
    }

    public int getAprilTagId() {
        int id = (int) table.getEntry("tid").getDouble(0.0);
        return id;
    }
}