package org.firstinspires.ftc.teamcode.OpMode.hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


public class RobotHardwareMap {

    //0 - FL
    //1 - FR
    //2 - RL
    //3 - RR
    private LinearOpMode opMode = null;
    public HardwareMap baseHMap;

    public VoltageSensor controlHubBatteryVoltage;
    public VoltageSensor expansionHubBatteryVoltage;

    public LynxModule controlHub;
    public LynxModule expansionHub;

    public DcMotor rearLeftMotor;
    public DcMotor rearRightMotor;
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor intake;
    public DcMotor hang_motor_1;
    public DcMotor hang_motor_2;
    public DcMotor liftMotor;
    DigitalChannel liftLimitSwitch;
    DigitalChannel touchSensor_1;
    DigitalChannel touchSensor_2;

    public Servo intake1;
    public Servo intake2;
    public Servo intakeArm;
    public Servo servo_lift;
    public Servo servo_plane;


    public IMU chImu;

    private final int baseResolution_x = 320;
    private final int baseResolution_y = 240;
    WebcamName backCamera;

    boolean controlHubBatteryVoltageEnabled = true;
    boolean expansionHubBatteryVoltageEnabled = true;

    public RobotHardwareMap(HardwareMap baseHMap, LinearOpMode opmode) {

        this.opMode = opmode;
        this.baseHMap = baseHMap;
    }

    public void initialize(){

        opMode.telemetry.addData("Status", "detecting...");

        controlHubBatteryVoltage = baseHMap.get(VoltageSensor.class, "Control Hub");
        expansionHubBatteryVoltage = baseHMap.get(VoltageSensor.class, "Expansion Hub");
        controlHub = baseHMap.get(LynxModule.class, "Control Hub");
        expansionHub = baseHMap.get(LynxModule.class, "Expansion Hub");

        frontRightMotor = baseHMap.get(DcMotor.class, "frontRightMotor");
        rearLeftMotor = baseHMap.get(DcMotor.class, "rearLeftMotor");
        frontLeftMotor = baseHMap.get(DcMotor.class, "frontLeftMotor");
        rearRightMotor = baseHMap.get(DcMotor.class, "rearRightMotor");
        liftMotor = baseHMap.get(DcMotor.class, "lift_system");

        //Camera
        try {
//            frontCamera = baseHMap.get(WebcamName.class, "Front Camera");
            backCamera = baseHMap.get(WebcamName.class, "Webcam 1");
            opMode.telemetry.addData("cameras", "success ");
        } catch (IllegalArgumentException iae){
            opMode.telemetry.addData("cameras", iae.getMessage());
        }

        //Initializes the IMU
        chImu = baseHMap.get(IMU.class, "imu");

        //Bucky Parameters
        /*
//        IMU.Parameters myIMUParamaters = new IMU.Parameters(
//                    new RevHubOrientationOnRobot(
//                                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
//                                RevHubOrientationOnRobot.UsbFacingDirection.UP
//                    )
        );*/
        IMU.Parameters myIMUParamaters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        chImu.initialize(myIMUParamaters);

        opMode.telemetry.addData("Status", "done");
        opMode.telemetry.update();
    }

}
