package org.firstinspires.ftc.teamcode.OpMode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Disabled
@TeleOp(name = "sensorsTest")
public class sensorsTest extends OpMode {

    DcMotor lift_system;
    DcMotor hang_system_1;
    DcMotor hang_system_2;

    DigitalChannel liftLimitSwitch;
    DigitalChannel touchSensor_1;
    DigitalChannel touchSensor_2;

    private final String LIFT_SYSTEM_CONFIG_NAME = "lift_system";
    private final String HANG_SYSTEM_1_CONFIG_NAME = "hang_system_1";
    private final String HANG_SYSTEM_2_CONFIG_NAME = "hang_system_2";

    boolean xButtonPressed = false;
    boolean xButtonAlreadyPressed = false;

    @Override
    public void init() {
        lift_system = hardwareMap.dcMotor.get(LIFT_SYSTEM_CONFIG_NAME);
        hang_system_1 = hardwareMap.dcMotor.get(HANG_SYSTEM_1_CONFIG_NAME);
        hang_system_2 = hardwareMap.dcMotor.get(HANG_SYSTEM_2_CONFIG_NAME);

        liftLimitSwitch = hardwareMap.digitalChannel.get("liftLimitSwitch");
        liftLimitSwitch.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_1 = hardwareMap.digitalChannel.get("touchSensor_1");
        touchSensor_1.setMode(DigitalChannel.Mode.INPUT);

        touchSensor_2 = hardwareMap.digitalChannel.get("touchSensor_2");
        touchSensor_2.setMode(DigitalChannel.Mode.INPUT);
    }

    @Override
    public void loop() {
        processXButton();
    }

    private void processXButton() {
        if (gamepad1.x && !xButtonAlreadyPressed) {
            xButtonPressed = true;
            xButtonAlreadyPressed = true;
        } else if (!gamepad1.x) {
            xButtonAlreadyPressed = false;
        }

        if (xButtonPressed || (liftLimitSwitch.getState() && touchSensor_1.getState() && touchSensor_2.getState())) {
            if (!liftLimitSwitch.getState()) {
                lift_system.setPower(-1);
            } else {
                lift_system.setPower(0);
            }

            if (!touchSensor_1.getState()) {
                hang_system_1.setPower(-1);
            } else {
                hang_system_1.setPower(0);
            }

            if (!touchSensor_2.getState()) {
                hang_system_2.setPower(-1);
            } else {
                hang_system_2.setPower(0);
            }
        } else {
            lift_system.setPower(0);
            hang_system_1.setPower(0);
            hang_system_2.setPower(0);
        }
    }
}
