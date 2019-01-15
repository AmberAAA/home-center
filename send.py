import urllib.parse
import urllib.request
import json

def post(url, data, method="POST"):
    post_data = urllib.parse.urlencode(data).encode('utf-8')
    request = urllib.request.Request(url, data=post_data, method=method)
    response = urllib.request.urlopen(request)
    return response.read().decode('utf-8')

