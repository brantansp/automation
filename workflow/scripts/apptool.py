from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import os
import time
from applitools.eyes import Eyes



#variable intialization
chromepath     = "/usr/local/bin/chromedriver"
url            = "http://automation.pandostaging.in"
username       = "pandoautomation@gmail.com"
password       = "test@1234"
inputFilePath  = "/Users/dinesh/Documents/scripts/optima/optima_input_files/"
outputFilePath = "/Users/dinesh/Documents/scripts/optima/optima_output_files/"

#Setting up Eye 
eyes = Eyes()
eyes.api_key = '97CqnPZZaCl8OsE4v98Obm5qwB1tYmQAE2d9FMD8ZPjew110'
eyes.baseline_name = "baseline_detail_page_2"


#setting up driver
chrome_options = webdriver.ChromeOptions()
#chrome_options.add_argument("--headless")
prefs = {'download.default_directory' : '/Users/dinesh/Documents/scripts/optima/optima_output_files/'}
chrome_options.add_experimental_option('prefs', prefs)
driver = webdriver.Chrome(chrome_options=chrome_options)
driver.set_window_size(1424, 1068)

#AppTool
eyes.open(driver, "UI Validation", "PandoCorp")


#Getting URL
driver.get(url)

#eyes to login window
eyes.check_window('Login Window');

time.sleep(5)

#login
driver.find_element_by_xpath("//label[contains(text(),'User name:')]//preceding-sibling::input").send_keys(username)
driver.find_element_by_xpath("//label[contains(text(),'Password:')]//preceding-sibling::input").send_keys(password)
driver.find_element_by_xpath("//button[@type='button']").click()

time.sleep(5)
eyes.check_window('Indent Page');
eyes.close();

driver.quit();


