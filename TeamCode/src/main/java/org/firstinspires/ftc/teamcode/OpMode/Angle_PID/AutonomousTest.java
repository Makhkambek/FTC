package org.firstinspires.ftc.teamcode.OpMode.Angle_PID;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
//@Disabled
@Autonomous(name = "TestForAutonomous")
public class AutonomousTest extends LinearOpMode {

    private static final double Kp = 1.2;
    private static final double Ki = 0.0;
    private static final double Kd = 0.031;

    private static final double ENCODER_TICKS_PER_INCH = 42.81;

    private Drivetrain drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {
        // Инициализация робота
        drivetrain = new Drivetrain();
        drivetrain.init(hardwareMap);

        waitForStart();

        moveWithPID(0.1, 10);

        sleep(2000);

        moveWithPID(0.1, -10);
    }

    private void moveWithPID(double speed, double distance) {
        ElapsedTime timer = new ElapsedTime();
        double integralSum = 0;
        double lastError = 0;

        double targetEncoderTicks = distance * ENCODER_TICKS_PER_INCH;

        while (opModeIsActive()) {
            double error = targetEncoderTicks - drivetrain.getLeftEncoderTicks();
            integralSum += error * timer.seconds();
            double derivative = (error - lastError) / timer.seconds();
            lastError = error;
            timer.reset();
            double output = (error * Kp) + (integralSum * Ki) + (derivative * Kd);

            drivetrain.setPower(speed + output, speed - output);

            if (Math.abs(error) < 10) {
                break;
            }

            telemetry.addData("Current Encoder Ticks", drivetrain.getLeftEncoderTicks());
            telemetry.addData("Target Encoder Ticks", targetEncoderTicks);
            telemetry.addData("Error", error);
            telemetry.update();
        }

        drivetrain.stop();
    }
}
