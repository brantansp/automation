import sys
import xml.dom.minidom
import re
rerun = 0
ag1   = str(sys.argv[1])
ag2   = str(sys.argv[2])


def replacenth(string, sub, wanted, n):
    where = [m.start() for m in re.finditer(sub, string)][n-1]
    before = string[:where]
    after = string[where:]
    after = after.replace(sub, wanted, 1)
    newString = before + after
    return newString
    
    
doc     = xml.dom.minidom.parse("fitnesse-data-result.xml");
results =  doc.getElementsByTagName("result")
path    = "FitNesseRoot/"

for result in results:
     status     = result.getElementsByTagName("wrong")
     execep     = result.getElementsByTagName("exceptions")
     testCase   = result.getElementsByTagName("pageHistoryLink")
     error      = status[0].firstChild.nodeValue
     exeception = execep[0].firstChild.nodeValue
     test       = testCase[0].firstChild.nodeValue
     
     if (error >= "1" or exeception >= "1") :
         
         rerun = ag2
         
         #extracting failed testcase
         trimquestion = test.split("?")
         file = trimquestion[0].replace(".","/")
         fullpath = path+file+".wiki"
                  
         #reading file
         file       = open(fullpath)
         contents   = file.read()
         tagContent = replacenth(contents,'---','Suites: '+ ag1 + '\n---',2)         
         f = open(fullpath, "w")
         f.write(tagContent)
         
         #Failed testcase
         #print fullpath


print rerun