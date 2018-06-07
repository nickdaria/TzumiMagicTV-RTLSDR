# TzumiMagicTV RTLSDR
  This repo is a hub for modifications and details about the Tzumi MagicTV, a 13 dollar OpenWRT unit with RTL-SDR and a battery. At /r/rtlsdr, we have been working to get access to it. This is an unorganized hub where I am putting my findings and some others on the ##rtlsdr IRC. If you would like to help add to this, send me a message.

# Goals
  Right now, the ultimate goal is to get wireless SSH access. Currently, the only way is by dropping into root shell via serial ports, though we want to be able to gain root access without opening the device. I have been looking for ways to exploit the app and device communication, or take advantage of security flaws in the outdated software. Some over at /r/rtlsdr are trying to brute force the password, but as far as I know, it's no dice there.
  As far as I can tell, the next goal is to get the rtlsdr program running on the MagicTV. This would give us a 13 dollar, wifi accessible, battery powered SDR.

# Organization
  This repo is split into a simple hierarchy mainly divided by hardware and software. There is also a software-findings folder that details observed behaviors with the stock firmware. Finally, there is a mobileApp-android folder with a copy of the mobile app and a teardown.

# Credits
  * parkerlreed (IRC: ParkerR)
  * IRC: iKitsune
  * nickfromstatefarm (IRC: nickfromstatefar, nick-d)

# License
  I specifically chose The Unlicense for this project so anyone can use this work. It is a culmination of my work, and other on the /r/rtlsdr subreddit (including IRC.) Feel free to use what you need, just let us know if you find anything that could help.
