package org.firstinspires.ftc.teamcode.OpMode.hardware;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.OpMode.hardware.RobotGlobalSettings.initialize;


import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmSystem {

    RobotHardwareMap robotHardwareMap;
    LinearOpMode opMode;
    public ArmSystem(RobotHardwareMap robotHardwareMap, LinearOpMode opMode){
        this.opMode = opMode;
        this.robotHardwareMap = robotHardwareMap;
        initialize();
    }
    DcMotor lift_system;
    DcMotor hang_system_1;
    DcMotor hang_system_2;
    public Servo servo_lift;

    DigitalChannel liftLimitSwitch;
    DigitalChannel touchSensor_1;
    DigitalChannel touchSensor_2;
    public void init () {
        lift_system = hardwareMap.dcMotor.get("lift_system");
        hang_system_1 = hardwareMap.dcMotor.get("hang_system_1");
        hang_system_2 = hardwareMap.dcMotor.get("hang_system_2");
        servo_lift = hardwareMap.get(Servo.class, "servo_lift");

        liftLimitSwitch = hardwareMap.digitalChannel.get("liftLimitSwitch");
        liftLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_1 = hardwareMap.digitalChannel.get("touchSensor_1");
        touchSensor_1.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_2 = hardwareMap.digitalChannel.get("touchSensor_2");
        touchSensor_2.setMode(DigitalChannel.Mode.INPUT);
    }

    public void startArm() {
        lift_system.setPower(0.2);
        hang_system_1.setPower(1);
        hang_system_2.setPower(1);
        servo_lift.setPosition(0.2);
    }

    public void checkSensors() {
        // Проверяем состояние датчиков и выводим их состояния
        boolean isMagneticFieldDetected = liftLimitSwitch.getState();
        boolean isTouching = touchSensor_1.getState() && touchSensor_2.getState();

        if (!isMagneticFieldDetected) {
            telemetry.addData("Magnetic Sensor State", "yes");
        } else {
            telemetry.addData("Magnetic Sensor State", "no");
        }

        if (!isTouching) {
            telemetry.addData("Touch Sensor State", "yes");
        } else {
            telemetry.addData("Touch Sensor State", "no");
        }

        telemetry.update();
    }

    public void stopArm() {
        // Останавливаем движение систем
        lift_system.setPower(0);
        hang_system_1.setPower(0);
        hang_system_2.setPower(0);
        servo_lift.setPosition(0.75);

        // Возвращаем системы на нулевую позицию
        while (!liftLimitSwitch.getState()) {
            lift_system.setPower(0.5);
        }
        lift_system.setPower(0);

        while (!touchSensor_1.getState()) {
            hang_system_1.setPower(0.5);
        }
        hang_system_1.setPower(0);

        while (!touchSensor_2.getState()) {
            hang_system_2.setPower(0.5);
        }
        hang_system_2.setPower(0);

        // Показываем состояния датчиков
        checkSensors();
    }


}
