# Hardware
The hardware on the device is fairly basic. The main things of interest are the battery, and the main/daughter boards. The biggest point of interest of the hardware lies in the serial pinouts. This will drop you into root shell when connected via a TTL module. It does not require a 5v or 3.3v connection, as it only needs Tx, Rx, and GND.

# Serial
As seen in the pinoutTTL image, the board uses 3 of the 4 pinouts to communicate over serial. The original image was modified because the Tx and Rx were inverted. Once this connection is made, you can use an application like PuTTY (Win) to interface with the MagicTV.

# Details
(more soon, most of this is deep in my IRC log)
