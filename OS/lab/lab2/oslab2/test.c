#include <unistd.h>
#include <sys/syscall.h>
#define __NR_ mysyscall	333
int main()
{
    syscall(__NR_mysyscall);    
}


# include <linux/unistd.h>
# include <sys/syscall.h>

int main() {
        syscall(333);
        return 0;
}
