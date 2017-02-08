package edgarmarcopolo.com.myfirstappandroidthings;

/**
 * Created by Edgar on 1/20/2017.
 */

import android.os.Build;

import com.google.android.things.pio.PeripheralManagerService;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class BoardDefaults {
    private static final String DEVICE_EDISON_ARDUINO = "edison_arduino";
    private static final String DEVICE_EDISON = "edison";
    private static final String DEVICE_RPI3 = "rpi3";
    private static final String DEVICE_NXP = "imx6ul";
    private static String sBoardVariant = "";

    /**
     * Return the GPIO pin that the LED is connected on.
     * For example, on Intel Edison Arduino breakout, pin "IO13" is connected to an onboard LED
     * that turns on when the GPIO pin is HIGH, and off when low.
     */
    public static String getGPIOForLED() {
        switch (getBoardVariant()) {
            case DEVICE_EDISON_ARDUINO:
                return "IO13";
            case DEVICE_EDISON:
                return "GP45";
            case DEVICE_RPI3:
                return "BCM6";
            case DEVICE_NXP:
                return "GPIO4_IO20";
            default:
                throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
        }
    }

    public static String getLedGpioPin() {
        switch (getBoardVariant()) {
            case DEVICE_EDISON_ARDUINO:
                return "IO13";
            case DEVICE_EDISON:
                return "GP45";
            case DEVICE_RPI3:
                return "BCM6";
            case DEVICE_NXP:
                return "GPIO4_IO21";
            default:
                throw new IllegalArgumentException("Unknown device: " + Build.DEVICE);
        }
    }

    public static String getButtonGpioPin() {
        switch (getBoardVariant()) {
            case DEVICE_EDISON_ARDUINO:
                return "IO12";
            case DEVICE_EDISON:
                return "GP44";
            case DEVICE_RPI3:
                return "BCM21";
            case DEVICE_NXP:
                return "GPIO4_IO20";
            default:
                throw new IllegalArgumentException("Unknown device: " + Build.DEVICE);
        }
    }

    /**
     * Return the preferred PWM port for each board.
     */
    public static String getPWMPort() {
        switch (getBoardVariant()) {
            case DEVICE_EDISON_ARDUINO:
                return "IO6";
            case DEVICE_EDISON:
                return "GP12";
            case DEVICE_RPI3:
                return "PWM0";
            case DEVICE_NXP:
                return "PWM7";
            default:
                throw new IllegalStateException("Unknown Build.DEVICE " + Build.DEVICE);
        }
    }

    public static String getSpeakerPwmPin() {
        switch (getBoardVariant()) {
            case DEVICE_EDISON_ARDUINO:
                return "IO3";
            case DEVICE_EDISON:
                return "GP13";
            case DEVICE_RPI3:
                return "PWM1";
            case DEVICE_NXP:
                return "PWM7";
            default:
                throw new IllegalArgumentException("Unknown device: " + Build.DEVICE);
        }
    }

    public static String getI2cBus() {
        switch (getBoardVariant()) {
            case DEVICE_EDISON_ARDUINO:
                return "I2C6";
            case DEVICE_EDISON:
                return "I2C1";
            case DEVICE_RPI3:
                return "I2C1";
            case DEVICE_NXP:
                return "I2C2";
            default:
                throw new IllegalArgumentException("Unknown device: " + Build.DEVICE);
        }
    }

    public static String getSpiBus() {
        switch (getBoardVariant()) {
            case DEVICE_EDISON_ARDUINO:
                return "SPI1";
            case DEVICE_EDISON:
                return "SPI2";
            case DEVICE_RPI3:
                return "SPI0.0";
            case DEVICE_NXP:
                return "SPI3_0";
            default:
                throw new IllegalArgumentException("Unknown device: " + Build.DEVICE);
        }
    }

    private static String getBoardVariant() {
        if (!sBoardVariant.isEmpty()) {
            return sBoardVariant;
        }
        sBoardVariant = Build.DEVICE;
        // For the edison check the pin prefix
        // to always return Edison Breakout pin name when applicable.
        if (sBoardVariant.equals(DEVICE_EDISON)) {
            PeripheralManagerService pioService = new PeripheralManagerService();
            List<String> gpioList = pioService.getGpioList();
            if (gpioList.size() != 0) {
                String pin = gpioList.get(0);
                if (pin.startsWith("IO")) {
                    sBoardVariant = DEVICE_EDISON_ARDUINO;
                }
            }
        }
        return sBoardVariant;
    }
}
