wget http://mirrors.aliyun.com/linux-kernel/v4.x/linux-4.8.tar.xz
wget http://mirrors.aliyun.com/linux-kernel/v4.x/patch-4.8.xz
tar -xvf linux-4.8.tar.xz
xz -d patch-4.8.xz | patch –p1
apt-get install libncurses5-dev libssl-dev
apt-get install bc
cp /boot/config-`uname -r` .config 



