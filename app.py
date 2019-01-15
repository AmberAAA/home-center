#!/usr/bin/env python3
import RPi.GPIO as GPIO
import modules.dht11 as dht11
import time
import datetime
import threading
import modules.util as util
import json

# initialize GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.cleanup()

# read data using pin 14
instance = dht11.DHT11(pin=4)

# while True:
#     result = instance.read()
#     # if result.is_valid():
#     # print("Last valid input: " + str(datetime.datetime.now()))
#     # print("Temperature: %d C" % result.temperature)
#     # print("Humidity: %d %%" % result.humidity)
#     time.sleep(1)


def fun1():
    result = instance.read()
    if result.is_valid():
        util.post('https://anborong.top/api/sensor', {'sid': '1', 'data': json.dumps({'c': result.temperature, 'h': result.humidity}), 'token':'p5l&ZV&Rqt#jCvco', 'type': 'dht11'})
    global timer
    timer = threading.Timer(3, fun1)
    timer.start()


timer = threading.Timer(3, fun1)
timer.start()

