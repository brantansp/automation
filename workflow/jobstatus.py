import xml.dom.minidom
import re
status = 0

#parsing xml
doc     = xml.dom.minidom.parse("fitnesse-data-rerun.xml");
results  =  doc.getElementsByTagName("testsuite")

#extracting errors or failures
for result in results:
  error   =  result.getAttribute("errors")
  failure =  result.getAttribute("failures")
  
#checking runStatus
if (error >= "1" or failure >= "1") :
    status = 1
    

print status    