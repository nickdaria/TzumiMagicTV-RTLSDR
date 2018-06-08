#!/usr/bin/env python3

import socket

TCP_IP = '192.168.1.1'
TCP_PORT = 6000
BUFFER_SIZE = 1024
CHOICES = [0, 57, 63, 69, 79, 85, 177, 183, 189, 195, 201, 207, 213, 473, 479, 485, 491, 497, 503, 509, 515, 521, 527, 533, 539, 545, 551, 557, 563, 569, 575, 581, 587, 593, 599, 605, 611, 617, 623, 629, 635, 641, 647, 653, 659, 665, 671, 677, 683, 689, 695, 701, 707, 713, 719, 725, 731, 737, 743, 749, 755, 761, 767, 773, 779, 785, 791, 797, 803]
MESSAGE1 = '<msg type="login_req"><params protocol="TCP" port="8000"/></msg>'
CHANLIST = []

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))
s.send(bytes(MESSAGE1, encoding='utf-8'))
data = s.recv(BUFFER_SIZE)
print(data)
s.close()


for i in CHOICES:
    try:
        MESSAGE2 = '<msg type="tune_req"><params tv_type="ATSC" freq="' + str(i) +'000" bandwidth="8000" plp="0" programNumber="0"/></msg>'
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.settimeout(5)
        s.connect((TCP_IP, TCP_PORT))
        s.send(bytes(MESSAGE2, encoding='utf-8'))
        data = s.recv(BUFFER_SIZE)
        print(data)
        if 'ret="0"' in str(data):
            CHANLIST.append(str(i))
            print(CHANLIST)
        s.close()
    except socket.timeout:
        continue

print(CHANLIST)
