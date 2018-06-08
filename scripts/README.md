# Scripts

Any user created scripts to manage/interact with the Magic TV will go here.

## tv.py/tv-py3.py

Credit: parkerlreed

Script that accepts an RF channel number along with subchannel and plays it in a media player (currently set to mpv)

You can get your local channel mappings from https://www.tablotv.com/tools/

py3 version for Python 3. In Python 2 strings sent over a socket were already byte streams. Python 3 requires the data to be explicitly formatted as a byte stream.

## tv-scanner.py

Credit: parkerlreed

Script to scan for the base frequencies you can receive. Prints as it's scanning and then once more at the end.

TODO: Implement mpeg2 library to verify valid signal and grab program/subchannel data.

Python 3 required. Python 2 needs modification to the message strings to work.
