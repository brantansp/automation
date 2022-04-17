import requests
import time

#login and extracting token
login = requests.put('http://automation.pandostaging.in/api/login',
                     json={"data":{"email":"pandoautomation@gmail.com","password":"test@1234"}})
token = login.headers['x-auth-token']
n = 19

for x in range(n):

    #Rejecting consignee by refrence id
    consignee_add = requests.post('http://automation.pandostaging.in/api/erp/consignee/',
                         json={"data":[{"name":"cexeception"+str(x),
                                        "reference_number":"",
                                        "address":"Egmore",
                                        "city":"Chennai",
                                        "state":"Tamil Nadu",
                                        "region":"South",
                                        "mobile_number":"1234567890",
                                        "pincode":"520122",
                                        "email":"pandoconsignee+"+str(x)+"test"+"@gmail.com",
                                        "category":"B",
                                        "customer_type":"Shipper Site"}
                                       ]},
                        headers={'Authorization': "bearer "+token})

    print x
    time.sleep(3)
