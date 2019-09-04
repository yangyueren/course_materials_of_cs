#include <iostream>
#include <string>
#include <fstream>
using namespace std;

void testByLine(string file, string lable, int num)
{
    char buffer[1000];
    string temp;
    fstream outFile;
    outFile.open(file, ios::in);
    int flag = 0;
    string str1 = lable;
    cout << "output log"
         << "--- all file is as follows:---" << endl;
    
    while (!outFile.eof())
    {
        outFile.getline(buffer, 1000, '\n'); 
        temp = buffer;
        size_t found = temp.find(str1);
        if (found != string::npos)
            flag++;
        if (flag == num)
            cout << buffer << endl;
    }
    outFile.close();
}

int findPosition(string file, string lable)
{
    char buffer[1000];
    string temp;
    fstream outFile;
    outFile.open(file, ios::in);
    int flag = 0;
    string str1 = lable;
    
    while (!outFile.eof())
    {
        outFile.getline(buffer, 1000, '\n'); 
        temp = buffer;
        // cout << buffer <<endl;
        size_t found = temp.find(str1);
        if (found != string::npos)
            flag++;
    }
    outFile.close();
    return flag;
}
int main()
{
    string file = "/var/log/kern.log";
    string label = "mysyscall:";

    int num = findPosition(file, label);
    cout << num << endl;
    testByLine(file, label, num);
    return 0;
}