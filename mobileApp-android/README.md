# Initial Findings
After looking into the code (while obfuscated) I noticed that most of the app's work was sourced out to common libraries, except two. One was "eardatek.special.player". This seems to be the library that both handles the streams output and interaction with the second library. The second library is called "blazevideo.libdtv". This one is extremely difficult to understand, but it is called a LOT by the eardatek library.

# Details
One important note is a whole section of eardatek's library consists of the scanchannelactivity. This also includes an array with the frequencies used, as well as references to a "savedChannelList". I have not yet determined if this channel list is determined locally or by the MagicTV device.

# Todo
There is a section (Eardatek, i, l, t()) where the app requests the MagicTV "deviceInfo" variable. Maybe this communication system has bigger possibilities.
