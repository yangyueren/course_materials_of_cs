import os

f = open("/var/log/kern.log")               
line = f.readline()
num = 0
label = "mysyscall:"
while line:
    if label in line:
        num = num + 1	                      
    line = f.readline()


y = open("/var/log/kern.log")               
line2 = y.readline()  
temp = 0  
flag = 0
         
while line2:
    if label in line2:
        temp = temp + 1 
        if(num == temp):
            flag = 1                   
    if flag:
        print(line2)
    
    line2 = y.readline()

