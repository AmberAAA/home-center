#!/usr/bin/env python

from gpiozero import LED
import time

led = LED(17)

def pc():
	led.on()
	time.sleep(1)
	led.off()
