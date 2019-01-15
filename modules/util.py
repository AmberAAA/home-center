import urllib.parse
import urllib.request
import json

max_try_num = 4

def post(url, data, method="POST"):
    post_data = urllib.parse.urlencode(data).encode('utf-8')
    for tries in range(max_try_num):
        try:
            request = urllib.request.Request(url, data=post_data, method=method)
            response = urllib.request.urlopen(request)
            break
        except:
            if tries < (max_try_num - 1) :
                continue
            else:
                print("post failed!")
                break
    return response.read().decode('utf-8')

