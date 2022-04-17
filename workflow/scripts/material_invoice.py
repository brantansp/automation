import requests
import time
import json

#login and extracting token
login     = requests.put('http://automation.pandostaging.in/api/login',
                     json={"data":{"email":"pandoautomation@gmail.com","password":"test@1234"}})
token     = login.headers['x-auth-token']
n         = 1

for x in range(n):
    
    timeStamp   = int(time.time())
    consignee1  = "pandoconsignee+"+str(timeStamp)+"@gmail.com"
    consignee2  = "pandoconsignee+1"+str(timeStamp)+"@gmail.com"
    transporter = "pandotransporter+"+str(timeStamp)+"@gmail.com"
     
     #Adding Consignee
    consignee_add = requests.post('http://automation.pandostaging.in/api/consignee/',
                         json={
                                  "data": {
                                    "name": "c"+str(timeStamp),
                                    "reference_id": "cr"+str(timeStamp),
                                    "address": "Egmore",
                                    "city": "Chennai",
                                    "state": "Tamil Nadu",
                                    "country": "India",
                                    "pincode": "520122",
                                    "customer_type": 1,
                                    "status": 1,
                                    "region": "South",
                                    "combine": "true",
                                    "category": "",
                                    "exclude_vehicles": [],
                                    "manager": "Manager Automation",
                                    "email_id": consignee1,
                                    "phone": "12"
                                  }
                                },
                        headers={'Authorization': "bearer "+token})

    result       = consignee_add.json()
    consignee_id =  result["data"]["id"] #Consingee id
    
    consignee_add = requests.post('http://automation.pandostaging.in/api/consignee/',
                         json={
                                  "data": {
                                    "name": "c1"+str(timeStamp),
                                    "reference_id": "cr1"+str(timeStamp),
                                    "address": "Egmore",
                                    "city": "Chennai",
                                    "state": "Tamil Nadu",
                                    "country": "India",
                                    "pincode": "520122",
                                    "customer_type": 1,
                                    "status": 1,
                                    "region": "South",
                                    "combine": "true",
                                    "category": "",
                                    "exclude_vehicles": [],
                                    "manager": "Manager Automation",
                                    "email_id": consignee2,
                                    "phone": "1234567890"
                                  }
                                },
                        headers={'Authorization': "bearer "+token})

    result        = consignee_add.json()
    consignee_id1 =  result["data"]["id"] #Consingee id
    
    
     #Adding transporter
    transporter_add = requests.post('http://automation.pandostaging.in/api/transporter/',
                      json={
                          "data": {
                            "name": "t"+str(timeStamp),
                            "gst_type": 1,
                            "gst_percentage": "10",
                            "reference_id": "tr"+str(timeStamp),
                            "discount": "0",
                            "status": 1,
                            "country": "India",
                            "users": [
                              {
                                "name": "Manager $unique",
                                "phone": "1234567890",
                                "email": transporter
                              }
                            ],
                            "address": "Ashok Nager",
                            "city": "Chennai",
                            "pincode": "600083",
                            "state": "Tamil Nadu",
                            "pan_no": "AUYPD33324L"
                          }
                        },
                        headers={'Authorization': "bearer "+token})

    result         = transporter_add.json()
    transporter_id =  result["data"]["id"] #transporter id
    
    #Adding Indent
    indent_add = requests.post('http://automation.pandostaging.in/api/order',
                      json={
                      "data": {
                        "order_type": 1,
                        "freight_type": 0,
                        "depot_id": "5c74edf0c8d634115eaf23bd",
                        "vendor_id": transporter_id,
                        "vehicle_type_id": "5c750bce6e2ae92e2d5bc5f8",
                        "delivery_type": "FTL",
                        "gates": [
                          {
                            "id": "5c74edf083f399115e3eaa55",
                            "loading_date": "2019-06-24T07:33:05.917Z",
                            "loader_avl": 0,
                            "paid_by": 0
                          }
                        ],
                        "consignees": [
                          {
                            "gate_id": "5c74edf083f399115e3eaa55",
                            "ship_id": consignee_id,
                            "ship_refid": "cr"+str(timeStamp),
                            "sold_id": consignee_id,
                            "unloader_avl": 0,
                            "paid_by": 0
                          },
                          {
                            "gate_id": "5c74edf083f399115e3eaa55",
                            "ship_id": consignee_id1,
                            "ship_refid": "cr1"+str(timeStamp),
                            "sold_id": consignee_id1,
                            "unloader_avl": 0,
                            "paid_by": 0
                          }
                        ]
                      }
                    },
                        headers={'Authorization': "bearer "+token})

    result         = indent_add.json()
    order_id       =  result["data"]["order_number"] #order id
    
    
    
    #Truck Assign
    truck_assign = requests.put("http://automation.pandostaging.in/api/order/"+order_id+"/assign_truck",
                      json={
                          "data": {
                            "vehicle": {
                              "number": "tn 09 21 1234"
                            },
                            "driver": {
                              "name": "driver",
                              "phone_number": "1234567890"
                            }
                          }
                        },
                        headers={'Authorization': "bearer "+token})

    result         = truck_assign.json()
    truckid        = result["data"]["id"]
    
    
    #New Material Invoice
    material_invoice = requests.post('http://automation.pandostaging.in/api/erp/material_invoice',
                      json={
                              "data": [
                                {
                                  "indent_no": order_id,
                                  "delivery_number": "D1"+str(timeStamp),
                                  "mrp_price": "100",
                                  "material_code": "m1",
                                  "depot_ref_id": "CHN1",
                                  "gate_ref_id": "CHN01",
                                  "division": "BK",
                                  "quantity": "100",
                                  "quantity_unit": "S/0",
                                  "weight": "400",
                                  "weight_unit": "KG",
                                  "volume": "1000",
                                  "volume_unit": "CFT",
                                  "ship_to": "cr"+str(timeStamp),
                                  "sold_to": "cr"+str(timeStamp),
                                  "type": "PRIMARY",
                                  "invoice_number": "INV1-"+str(timeStamp),
                                  "invoice_amount": "12000",
                                  "invoice_date": "2019/04/12",
                                  "category": "",
                                  "truck_out": "2019/04/12 04:28:44"
                                },
                                {
                                  "indent_no": order_id,
                                  "delivery_number": "D2"+str(timeStamp),
                                  "mrp_price": "100",
                                  "material_code": "m1",
                                  "depot_ref_id": "CHN1",
                                  "gate_ref_id": "CHN01",
                                  "division": "BK",
                                  "quantity": "100",
                                  "quantity_unit": "S/0",
                                  "weight": "400",
                                  "weight_unit": "KG",
                                  "volume": "1000",
                                  "volume_unit": "CFT",
                                  "ship_to": "cr1"+str(timeStamp),
                                  "sold_to": "cr1"+str(timeStamp),
                                  "type": "PRIMARY",
                                  "invoice_number": "INV2-"+str(timeStamp),
                                  "invoice_amount": "12000",
                                  "invoice_date": "2019/04/12",
                                  "category": "",
                                  "truck_out": "2019/04/12 04:28:44"
                                }
                              ]
                            },
                        headers={'Authorization': "bearer "+token})

    result         = material_invoice.content
    
    print order_id
    print str(timeStamp)
    print "*************"
    
    time.sleep(1)
