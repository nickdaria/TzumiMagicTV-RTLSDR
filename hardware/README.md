# Hardware
  The hardware on the device is fairly basic. The main things of interest are the battery, and the main/daughter boards. The biggest point of interest of the hardware lies in the serial pinouts. This will drop you into root shell when connected via a TTL module. It does not require a 5v or 3.3v connection, as it only needs Tx, Rx, and GND.

# Serial
  As seen in the pinoutTTL image, the board uses 3 of the 4 pinouts to communicate over serial. The original image was modified because the Tx and Rx were inverted. Once this connection is made, you can use an application like PuTTY (Win) to interface with the MagicTV.

# USB
  There are two USB ports, one full port for powering devices, and one micro USB port for charging and powering the MagicTV. Unfortunately, the microUSB data leads are commoned, meaning they do not even connect to the device, just it's power supply. As far as I can tell, it is the same with the full size USB port. This is most likely the case, as the factory uses the serial pins to program the device, and don't need to interface via microusb.

# LEDs
  The power led is software controlled, and can be manipulated with the command "echo 0 > /sys/class/leds/tp-link\:blue\:system/brightness". 0 turns off the light, and any nonzero number turns it on.
  The battery indicators are most likely wired to the battery controller, as these work without the magictv powered on, making gpio control unlikely.

# Details
  Daughter Board: http://earda.manufacturer.globalsources.com/si/6008848642438/pdtl/Wireless-broadband/1156156175/WLAN-AP-Repeater-Module.htm
  (more soon, most of this is deep in my IRC log)
